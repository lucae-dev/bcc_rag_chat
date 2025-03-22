package bck_instapic.core.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Component
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Wrap the request and response to allow for multiple reads of the body
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            logRequest(wrappedRequest);
            logResponse(wrappedResponse);
            // Ensure the response body is written out to the client
            wrappedResponse.copyBodyToResponse();
        }
    }
    private void logRequest(ContentCachingRequestWrapper request) {
        String payload = getPayload(request.getContentAsByteArray(), request.getCharacterEncoding());
        Map<String, String> headers = getHeaders(request);
        List<String> cookies = getCookies(request);

        LOGGER.info("REQUEST: method={}, uri={}, query={}, remoteAddr={}, headers={}, cookies={}, payload={}",
                request.getMethod(),
                request.getRequestURI(),
                request.getQueryString(),
                request.getRemoteAddr(),
                headers,
                cookies,
                payload);
    }

    private void logResponse(ContentCachingResponseWrapper response) {
        String payload = getPayload(response.getContentAsByteArray(), response.getCharacterEncoding());
        Map<String, String> headers = getResponseHeaders(response);

        LOGGER.info("RESPONSE: status={}, headers={}, payload={}",
                response.getStatus(),
                headers,
                payload);
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }
        return headers;
    }

    private Map<String, String> getResponseHeaders(HttpServletResponse response) {
        Map<String, String> headers = new HashMap<>();
        Collection<String> headerNames = response.getHeaderNames();
        for(String headerName : headerNames) {
            headers.put(headerName, response.getHeader(headerName));
        }
        return headers;
    }

    private List<String> getCookies(HttpServletRequest request) {
        List<String> cookieList = new ArrayList<>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for(Cookie cookie : cookies) {
                cookieList.add(cookie.getName() + "=" + cookie.getValue());
            }
        }
        return cookieList;
    }

    private String getPayload(byte[] buf, String encoding) {
        if (buf == null || buf.length == 0) {
            return "";
        }
        try {
            return new String(buf, encoding);
        } catch (UnsupportedEncodingException e) {
            return "Unsupported Encoding";
        }
    }
}