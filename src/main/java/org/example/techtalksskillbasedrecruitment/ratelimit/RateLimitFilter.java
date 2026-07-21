package org.example.techtalksskillbasedrecruitment.ratelimit;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.techtalksskillbasedrecruitment.exceptions.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private static final int DEFAULT_MAX_REQUESTS = 100;
    private static final int DEFAULT_WINDOW_SECONDS = 60;

    private final SlidingWindowRateLimiter rateLimiter;
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;
    private final ObjectMapper objectMapper;

    public RateLimitFilter(
            SlidingWindowRateLimiter rateLimiter,
            RequestMappingHandlerMapping requestMappingHandlerMapping,
            ObjectMapper objectMapper
    ) {
        this.rateLimiter = rateLimiter;
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        HandlerExecutionChain handlerExecutionChain;

        try {
            handlerExecutionChain =
                    requestMappingHandlerMapping.getHandler(request);
        } catch (Exception exception) {
            filterChain.doFilter(request, response);
            return;
        }

        if (handlerExecutionChain == null
                || !(handlerExecutionChain.getHandler() instanceof HandlerMethod handlerMethod)) {

            filterChain.doFilter(request, response);
            return;
        }

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {

            filterChain.doFilter(request, response);
            return;
        }

        int maxRequests = DEFAULT_MAX_REQUESTS;
        int windowSeconds = DEFAULT_WINDOW_SECONDS;

        RateLimit rateLimit =
                handlerMethod.getMethodAnnotation(RateLimit.class);

        if (rateLimit != null) {
            maxRequests = rateLimit.requests();
            windowSeconds = rateLimit.windowSeconds();
        }

        String key =
                authentication.getName() + ":" + request.getRequestURI();

        boolean allowed = rateLimiter.isAllowed(
                key,
                maxRequests,
                windowSeconds
        );

        if (allowed) {
            filterChain.doFilter(request, response);
            return;
        }

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.TOO_MANY_REQUESTS.value(),
                HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase(),
                "Rate limit exceeded. Please try again later.",
                request.getRequestURI()
        );

        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        objectMapper.writeValue(
                response.getWriter(),
                errorResponse
        );
    }
}