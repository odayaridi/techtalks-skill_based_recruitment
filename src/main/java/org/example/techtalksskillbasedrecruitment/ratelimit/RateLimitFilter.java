//package org.example.techtalksskillbasedrecruitment.ratelimit;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.example.techtalksskillbasedrecruitment.common.exceptions.ErrorResponse;
//import org.springframework.http.HttpStatus;
//import org.springframework.lang.NonNull;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.HandlerExecutionChain;
//import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
//
//import java.io.IOException;
//import java.time.LocalDateTime;
//
//@Component
//public class RateLimitFilter extends OncePerRequestFilter {
//
//    private final SlidingWindowRateLimiter slidingWindowRateLimiter;
//    private final RequestMappingHandlerMapping handlerMapping;
//    private final ObjectMapper objectMapper;
//
//    public RateLimitFilter(SlidingWindowRateLimiter slidingWindowRateLimiter,
//                           RequestMappingHandlerMapping handlerMapping,
//                           ObjectMapper objectMapper) {
//        this.slidingWindowRateLimiter = slidingWindowRateLimiter;
//        this.handlerMapping = handlerMapping;
//        this.objectMapper = objectMapper;
//    }
//
//    @Override
//    protected void doFilterInternal(@NonNull HttpServletRequest request,
//                                    @NonNull HttpServletResponse response,
//                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
//
//        HandlerMethod handlerMethod = resolveHandlerMethod(request);
//
//        if (handlerMethod == null) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        RateLimit rateLimit = handlerMethod.getMethodAnnotation(RateLimit.class);
//
//        if (rateLimit == null) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication == null || !authentication.isAuthenticated()) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        String userIdentifier = authentication.getName();
//        String endpoint = request.getRequestURI();
//        String key = userIdentifier + ":" + endpoint;
//
//        boolean allowed = slidingWindowRateLimiter.isAllowed(
//                key,
//                rateLimit.requests(),
//                rateLimit.windowSeconds()
//        );
//
//        if (!allowed) {
//            writeRateLimitExceededResponse(response, endpoint);
//            return;
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//    private HandlerMethod resolveHandlerMethod(HttpServletRequest request) {
//        try {
//            HandlerExecutionChain chain = handlerMapping.getHandler(request);
//            if (chain != null && chain.getHandler() instanceof HandlerMethod) {
//                return (HandlerMethod) chain.getHandler();
//            }
//        } catch (Exception e) {
//            return null;
//        }
//        return null;
//    }
//
//    private void writeRateLimitExceededResponse(HttpServletResponse response, String path) throws IOException {
//        ErrorResponse errorResponse = new ErrorResponse(
//                LocalDateTime.now(),
//                HttpStatus.TOO_MANY_REQUESTS.value(),
//                HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase(),
//                "Rate limit exceeded. Please try again later.",
//                path
//        );
//        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
//        response.setContentType("application/json");
//        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
//    }
//}


package org.example.techtalksskillbasedrecruitment.ratelimit;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.techtalksskillbasedrecruitment.common.exceptions.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private static final int DEFAULT_MAX_REQUESTS = 100;
    private static final int DEFAULT_WINDOW_SECONDS = 60;

    private final SlidingWindowRateLimiter slidingWindowRateLimiter;
    private final RequestMappingHandlerMapping handlerMapping;
    private final ObjectMapper objectMapper;

    public RateLimitFilter(
            SlidingWindowRateLimiter slidingWindowRateLimiter,
            RequestMappingHandlerMapping handlerMapping,
            ObjectMapper objectMapper) {

        this.slidingWindowRateLimiter = slidingWindowRateLimiter;
        this.handlerMapping = handlerMapping;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        HandlerMethod handlerMethod = resolveHandlerMethod(request);

        if (handlerMethod == null) {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            filterChain.doFilter(request, response);
            return;
        }

        // Default rate limit for all endpoints
        int maxRequests = DEFAULT_MAX_REQUESTS;
        int windowSeconds = DEFAULT_WINDOW_SECONDS;

        // Override defaults if @RateLimit is present
        RateLimit rateLimit = handlerMethod.getMethodAnnotation(RateLimit.class);
        if (rateLimit != null) {
            maxRequests = rateLimit.requests();
            windowSeconds = rateLimit.windowSeconds();
        }

        String userIdentifier = authentication.getName();
        String endpoint = request.getRequestURI();
        String key = userIdentifier + ":" + endpoint;

        boolean allowed = slidingWindowRateLimiter.isAllowed(
                key,
                maxRequests,
                windowSeconds
        );

        if (!allowed) {
            writeRateLimitExceededResponse(response, endpoint);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private HandlerMethod resolveHandlerMethod(HttpServletRequest request) {
        try {
            HandlerExecutionChain chain = handlerMapping.getHandler(request);

            if (chain != null && chain.getHandler() instanceof HandlerMethod handlerMethod) {
                return handlerMethod;
            }
        } catch (Exception e) {
            return null;
        }

        return null;
    }

    private void writeRateLimitExceededResponse(
            HttpServletResponse response,
            String path) throws IOException {

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.TOO_MANY_REQUESTS.value(),
                HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase(),
                "Rate limit exceeded. Please try again later.",
                path
        );

        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}