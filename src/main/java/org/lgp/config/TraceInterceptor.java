package org.lgp.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.lgp.constant.Constants;
import org.lgp.utils.TraceIdUtil;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

public class TraceInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String traceId = request.getHeader(Constants.TRACE_ID);
        if (traceId == null) {
            traceId = TraceIdUtil.getTraceId();
        }
        MDC.put(Constants.TRACE_ID,traceId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.remove(Constants.TRACE_ID);
    }
}
