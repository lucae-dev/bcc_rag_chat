package bck_instapic.core.filters;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class CorrelationIdFilter extends OncePerRequestFilter {
    public static final String CORRELATION_ID_HEADER = "X-Correlation-ID";
    public static final String MDC_CORRELATION_ID_KEY = "correlationId";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {

        try {
            String correlationId = request.getHeader(CORRELATION_ID_HEADER);
            if (correlationId == null || correlationId.isEmpty()) {
                correlationId = generateUniqueCorrelationId();
            }
            // Put the correlation id into MDC so that it is automatically added to all log entries
            MDC.put(MDC_CORRELATION_ID_KEY, correlationId);
            // Optionally, include the correlation id in the response header for client reference
            response.addHeader(CORRELATION_ID_HEADER, correlationId);

            filterChain.doFilter(request, response);
        } finally {
            // Clean up the MDC to prevent leakage of data between requests
            MDC.remove(MDC_CORRELATION_ID_KEY);
        }
    }

    private String generateUniqueCorrelationId() {
        return UUID.randomUUID().toString();
    }
}
