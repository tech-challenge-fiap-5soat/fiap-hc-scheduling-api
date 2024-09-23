package com.fiap.hackaton.healthmed.scheduling_api.common.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {
    @Bean
    public RedissonClient redissonClient(@Value("${redis.address}") String address) {
        String addressFull = String.format("redis://%s", address); ;
        Config config = new Config();
        config.useSingleServer().setAddress(addressFull);
        return Redisson.create(config);
    }
}