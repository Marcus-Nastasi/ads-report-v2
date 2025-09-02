package com.ads.report.infrastructure.configuration.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;

import java.util.concurrent.Executor;

/**
 * The {@link org.springframework.scheduling.annotation.Async} personalized configuration.
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
@EnableAsync
@Configuration
public class AsyncConfig {

    private final ThreadPoolTaskExecutor delegate = new ThreadPoolTaskExecutor();

    private final int availableProcessors = Runtime.getRuntime().availableProcessors();

    @Bean
    public Executor contextAwareExecutor() {
        // 1. Creates a configured threads pool.
        delegate.setCorePoolSize(availableProcessors);
        delegate.setMaxPoolSize(availableProcessors * 2);
        delegate.setQueueCapacity(200);
        delegate.setThreadNamePrefix("ctx-async-");
        // 2. Associates a decorator that propagate Request + Security Context
        delegate.setTaskDecorator(new ContextCopyingDecorator());
        delegate.initialize();
        // 3. Evolves with an executor that knows how to propagate the SecurityContext.
        return new DelegatingSecurityContextAsyncTaskExecutor(delegate);
    }
}
