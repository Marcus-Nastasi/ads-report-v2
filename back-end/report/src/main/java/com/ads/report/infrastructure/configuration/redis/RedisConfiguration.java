package com.ads.report.infrastructure.configuration.redis;

import com.ads.report.infrastructure.entity.OAuth2AuthorizedClientDto;
import com.ads.report.infrastructure.gateway.redis.RedisOAuth2AuthorizedClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

/**
 *
 * <p>The Redis configuration.<p/>
 *
 * <p>This class allows us to configure Redis.<p/>
 *
 * @author Marcus Nastasi
 * @version 1.0.2
 * @since 2025
 * */
@Configuration
public class RedisConfiguration {

    /**
     *
     * <p>The redis template config.<p/>
     *
     * @param factory The redis connection factory base object.
     * @return Return a template to represent the saving of a String as key, and OAuth2AuthorizedClientDto as value.
     */
    @Bean
    public RedisTemplate<String, OAuth2AuthorizedClientDto> redisTemplate(RedisConnectionFactory factory) {
        // Creating a new RedisTemplate object.
        RedisTemplate<String, OAuth2AuthorizedClientDto> template = new RedisTemplate<>();
        template.setConnectionFactory(factory); // Setting the connection factory.
        // Creating and config the ObjectMapper to JSON.
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        // Using jackson 2 serializer to JSON to Redis.
        Jackson2JsonRedisSerializer<OAuth2AuthorizedClientDto> serializer = new Jackson2JsonRedisSerializer<>(
            objectMapper,
            OAuth2AuthorizedClientDto.class
        );
        // Configuring the serializer to the template.
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        return template;
    }

    @Bean
    public RedisOAuth2AuthorizedClient redisOAuth2AuthorizedClient(RedisTemplate<String, OAuth2AuthorizedClientDto> redisTemplate, ClientRegistrationRepository clientRegistrationRepository) {
        return new RedisOAuth2AuthorizedClient(redisTemplate, clientRegistrationRepository);
    }
}
