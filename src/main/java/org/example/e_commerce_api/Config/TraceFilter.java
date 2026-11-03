package org.example.e_commerce_api.Config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

import static org.example.e_commerce_api.Constant.app.AppConstant.REQUEST_ID;

@Component
public class TraceFilter extends OncePerRequestFilter {
    public static final String TRACE_ID = "traceId";
    public static final String PATH = "path";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestId = request.getHeader("X-Request-Id");
        if (requestId == null || requestId.isBlank()){
            requestId = UUID.randomUUID().toString();
        }

        String traceId = requestId;

        MDC.put(REQUEST_ID, requestId);
        MDC.put(TRACE_ID, traceId);

        request.setAttribute(REQUEST_ID, requestId);
        request.setAttribute(TRACE_ID, traceId);
        request.setAttribute(PATH, request.getRequestURI());

        response.setHeader("X-Request-Id", requestId);
        response.setHeader("X-Trace_Id",traceId);

        try{
            filterChain.doFilter(request, response);
        }finally {
            MDC.clear();
        }
    }
}
