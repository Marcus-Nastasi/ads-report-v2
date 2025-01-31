package com.ads.report.infrastructure.configuration.redis;

import com.ads.report.domain.oauth2.OAuth2AuthorizedClientDto;
import com.ads.report.infrastructure.gateway.oauth2.OAuth2AuthorizedClientDeserializer;
import com.ads.report.infrastructure.gateway.oauth2.OAuth2AuthorizedClientSerializer;
import com.ads.report.infrastructure.gateway.redis.RedisOAuth2AuthorizedClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@Configuration
public class RedisConfiguration {

    @Bean
    public RedisTemplate<String, OAuth2AuthorizedClientDto> redisTemplate(RedisConnectionFactory factory, ClientRegistrationRepository clientRegistrationRepository) {
        RedisTemplate<String, OAuth2AuthorizedClientDto> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        // Criando ObjectMapper configurado para OAuth2AuthorizedClient
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Suporte para Instant, LocalDateTime

        // Criando Serializador/Desserializador customizado
        Jackson2JsonRedisSerializer<OAuth2AuthorizedClientDto> serializer =
            new Jackson2JsonRedisSerializer<>(objectMapper, OAuth2AuthorizedClientDto.class);

        // Configurar Serializadores no RedisTemplate
        template.setKeySerializer(new StringRedisSerializer()); // Serializa chaves como String
        template.setValueSerializer(serializer); // Serializa valores usando Jackson

        return template;
    }

    @Bean
    public RedisOAuth2AuthorizedClient redisOAuth2AuthorizedClient(RedisTemplate<String, OAuth2AuthorizedClientDto> redisTemplate, ClientRegistrationRepository clientRegistrationRepository) {
        return new RedisOAuth2AuthorizedClient(redisTemplate, clientRegistrationRepository);
    }
}
