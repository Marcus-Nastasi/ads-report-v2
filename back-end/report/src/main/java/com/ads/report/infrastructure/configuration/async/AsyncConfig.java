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
@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean
    public Executor contextAwareExecutor() {
        // 1. Creates a configured threads pool.
        ThreadPoolTaskExecutor delegate = new ThreadPoolTaskExecutor();
        delegate.setCorePoolSize(16);
        delegate.setMaxPoolSize(32);
        delegate.setQueueCapacity(200);
        delegate.setThreadNamePrefix("ctx-async-");
        // 2. Associates a decorator that propagate Request + Security Context
        delegate.setTaskDecorator(new ContextCopyingDecorator());
        delegate.initialize();
        // 3. Evolves with an executor that knows how to propagate the SecurityContext.
        return new DelegatingSecurityContextAsyncTaskExecutor(delegate);
    }
}
