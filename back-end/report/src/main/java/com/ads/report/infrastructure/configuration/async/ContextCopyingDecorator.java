package com.ads.report.infrastructure.configuration.async;

import org.springframework.core.task.TaskDecorator;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * This class enables to copy context and scope of requests.
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
public class ContextCopyingDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(@NonNull Runnable runnable) {
        // 1. Capture the current request context (attributes)
        RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
        // 2. Capture the current security context (OAuth2 token)
        SecurityContext secCtx = SecurityContextHolder.getContext();
        /**
         * Returns new {@link Runnable} that, before executing the logic, restore the context on threads.
         * */
        return () -> {
            try {
                // Define the RequestContext and SecurityContext on current thread.
                RequestContextHolder.setRequestAttributes(attrs);
                SecurityContextHolder.setContext(secCtx);
                // Executes original logic.
                runnable.run();
            } finally {
                // Clear the context to avoid context scape through threads.
                RequestContextHolder.resetRequestAttributes();
                SecurityContextHolder.clearContext();
            }
        };
    }
}
