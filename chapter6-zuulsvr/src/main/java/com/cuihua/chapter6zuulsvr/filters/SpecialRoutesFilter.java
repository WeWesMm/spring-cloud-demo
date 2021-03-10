package com.cuihua.chapter6zuulsvr.filters;

import com.cuihua.chapter6zuulsvr.model.AbTestingRoute;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author Lin Jing
 * @date 2021/1/21 下午3:52
 */
@Slf4j
@Component
public class SpecialRoutesFilter extends ZuulFilter {
    private static final int FILTER_ORDER = 1;
    private static final boolean SHOULD_FILTER = true;

    @Autowired
    private FilterUtils filterUtils;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public String filterType() {
        return FilterUtils.ROUTE_FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    private ProxyRequestHelper helper = new ProxyRequestHelper();

    @Override
    public Object run() throws ZuulException {

        RequestContext ctx = RequestContext.getCurrentContext();

        // 执行对 SpecialRoutes 服务的调用，以确定该服务 ID 是否有路由记录
        AbTestingRoute abTestingRoute = getAbRoutingInfo(filterUtils.getServiceId());

        if (abTestingRoute != null && useSpecialRoute(abTestingRoute)) {
            // 将完整的 URL（包含路径）构建到有 specialroutes 服务指定的服务位置
            String route = buildRouteString(
                    ctx.getRequest().getRequestURI(),
                    abTestingRoute.getEndpoint(),
                    ctx.get("serviceId").toString()
            );
            // 完成转发到其他服务的工作
            forwardToSpecialRoute(route);
        }

        return null;
    }

    /**
     * 确定目标服务的路由记录是否存在
     *
     * @param serviceName 目标服务
     * @return
     */
    private AbTestingRoute getAbRoutingInfo(String serviceName) {
        ResponseEntity<AbTestingRoute> restExchange = null;
        try {
            // 调用 SpecialRouteService 端点
            restExchange = restTemplate.exchange(
                    "http://specialroutesservice/v1/route/abtesting/{serviceName}",
                    HttpMethod.GET, null, AbTestingRoute.class, serviceName
            );
        } catch (HttpClientErrorException ex) {
            // 如果路由服务没有找到记录（它将返回 HTTP 状态码 404），该方法将返回空值
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                return null;
            }
        }
        return restExchange.getBody();
    }

    /**
     * 确定是否应该将目标服务请求路由到替代服务位置
     *
     * @param testRoute 目标路由服务
     * @return
     */
    public boolean useSpecialRoute(AbTestingRoute testRoute) {
        Random random = new Random();

        // 检查路由是否为活跃状态
        if (testRoute.getActive().equals("N")) {
            return false;
        }

        // 确定是否应该使用代替服务路由
        int value = random.nextInt((10 - 1) + 1) + 1;
        System.err.println("Random value: " + value);
        // 若随机生成的数大于返回路由的权重则使用代替路由
        if (testRoute.getWeight() < value) {
            return true;
        }

        return false;
    }

    /**
     * 设置目标服务路由 URI
     *
     * @param oldEndpoint 旧服务端点
     * @param newEndpoint 新服务端点
     * @param serviceName 服务名称
     * @return 目标服务路由 URI
     */
    private String buildRouteString(String oldEndpoint, String newEndpoint, String serviceName) {
        int index = oldEndpoint.indexOf(serviceName);

        String strippedRoute = oldEndpoint.substring(index + serviceName.length());
        System.err.println("Target route: " + String.format("%s/%s", newEndpoint, strippedRoute));
        return String.format("%s/%s", newEndpoint, strippedRoute);
    }

    /**
     * 转发到其他服务工作
     *
     * @param route 替代服务 URL
     */
    private void forwardToSpecialRoute(String route) {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();

        // 创建发送到服务的所有 HTTP 请求头部的副本
        MultiValueMap<String, String> headers = helper.buildZuulRequestHeaders(request);
        // 创建所有 HTTP 请求参数的副本
        MultiValueMap<String, String> params = helper.buildZuulRequestQueryParams(request);

        // 创建发送到服务的方式的副本：POST 等
        String verb = getVerb(request);
        // 创建将被转发到替代服务的 HTTP 主体的副本
        InputStream requestEntity = getRequestBody(request);
        if (request.getContentLength() < 0) {
            context.setChunkedRequestBody();
        }
        this.helper.addIgnoredHeaders();
        CloseableHttpClient httpClient = null;
        HttpResponse response = null;

        try {
            httpClient = HttpClients.createDefault();
            // 使用 forward() 方法调用替代服务
            response = forward(
                    httpClient,
                    verb,
                    route,
                    request,
                    headers,
                    params,
                    requestEntity
            );
            // 将服务调用的结果保存回 Zuul 服务器
            setResponse(response);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException ex) {
            }
        }
    }

    /**
     * 获取 HTTP 请求的请求方式（如 GET、POST 等）
     *
     * @param request HTTP 请求
     * @return 请求方式
     */
    private String getVerb(HttpServletRequest request) {
        String sMethod = request.getMethod();
        return sMethod.toUpperCase();
    }

    /**
     * 获取 HTTP 请求的主体，即 HTTP BODY
     *
     * @param request HTTP 请求
     * @return 请求主体
     */
    private InputStream getRequestBody(HttpServletRequest request) {
        InputStream requestEntity = null;
        try {
            requestEntity = request.getInputStream();
        } catch (IOException ex) {
            // no requestBody is ok.
        }
        return requestEntity;
    }

    /**
     * 调用替代服务
     *
     * @param httpclient    HTTP Client
     * @param verb          请求方式
     * @param uri           请求 URL
     * @param request       HTTP 请求
     * @param headers       请求首部
     * @param params        请求参数
     * @param requestEntity 请求主体
     * @return 响应结果
     * @throws Exception
     */
    private HttpResponse forward(HttpClient httpclient, String verb, String uri,
                                 HttpServletRequest request, MultiValueMap<String, String> headers,
                                 MultiValueMap<String, String> params, InputStream requestEntity)
            throws Exception {
        Map<String, Object> info = this.helper.debug(verb, uri, headers, params,
                requestEntity);
        // 根据 URI 创建 URL 对象
        URL host = new URL(uri);
        HttpHost httpHost = getHttpHost(host);

        HttpRequest httpRequest;
        int contentLength = request.getContentLength();
        // 创建请求体类
        InputStreamEntity entity = new InputStreamEntity(requestEntity, contentLength,
                request.getContentType() != null
                        ? ContentType.create(request.getContentType()) : null);
        // 根据请求类型设置 HTTP 请求类型及请求体
        switch (verb.toUpperCase()) {
            case "POST":
                HttpPost httpPost = new HttpPost(uri);
                httpRequest = httpPost;
                httpPost.setEntity(entity);
                break;
            case "PUT":
                HttpPut httpPut = new HttpPut(uri);
                httpRequest = httpPut;
                httpPut.setEntity(entity);
                break;
            case "PATCH":
                HttpPatch httpPatch = new HttpPatch(uri);
                httpRequest = httpPatch;
                httpPatch.setEntity(entity);
                break;
            default:
                httpRequest = new BasicHttpRequest(verb, uri);

        }
        try {
            // 设置请求首部
            httpRequest.setHeaders(convertHeaders(headers));
            // 转发 HTTP 请求
            HttpResponse zuulResponse = forwardRequest(httpclient, httpHost, httpRequest);

            return zuulResponse;
        } finally {
        }
    }

    /**
     * 设置 HttpHost
     *
     * @param host URL 对象
     * @return HttpHost 对象
     */
    private HttpHost getHttpHost(URL host) {
        HttpHost httpHost = new HttpHost(host.getHost(), host.getPort(),
                host.getProtocol());
        return httpHost;
    }

    /**
     * 设置 HTTP 请求首部
     *
     * @param headers 请求首部副本
     * @return 请求首部
     */
    private Header[] convertHeaders(MultiValueMap<String, String> headers) {
        List<Header> list = new ArrayList<>();
        for (String name : headers.keySet()) {
            for (String value : headers.get(name)) {
                list.add(new BasicHeader(name, value));
            }
        }
        return list.toArray(new BasicHeader[0]);
    }

    /**
     * 转发 HTTP 请求
     *
     * @param httpclient  HTTP Client
     * @param httpHost    HttpHost 对象
     * @param httpRequest HTTP 请求
     * @return
     * @throws IOException
     */
    private HttpResponse forwardRequest(HttpClient httpclient, HttpHost httpHost,
                                        HttpRequest httpRequest) throws IOException {
        return httpclient.execute(httpHost, httpRequest);
    }

    /**
     * 将服务调用的结果保存回 Zuul 服务器
     *
     * @param response 替代服务调用结果
     * @throws IOException
     */
    private void setResponse(HttpResponse response) throws IOException {
        this.helper.setResponse(response.getStatusLine().getStatusCode(),
                response.getEntity() == null ? null : response.getEntity().getContent(),
                revertHeaders(response.getAllHeaders()));
    }

    /**
     * 设置响应首部
     *
     * @param headers 响应首部
     * @return 响应首部
     */
    private MultiValueMap<String, String> revertHeaders(Header[] headers) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        for (Header header : headers) {
            String name = header.getName();
            if (!map.containsKey(name)) {
                map.put(name, new ArrayList<String>());
            }
            map.get(name).add(header.getValue());
        }
        return map;
    }
}
