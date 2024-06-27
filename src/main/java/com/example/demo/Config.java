package com.example.demo;

import io.micrometer.core.aop.CountedAspect;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.config.MeterFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class Config {

    @Bean
    public CountedAspect countedAspect(MeterRegistry registry) {
        return new CountedAspect(registry);
    }

    @Bean
    public MeterFilter meterFilter() {
        return new MeterFilter() {
            @Override
            public Meter.Id map(Meter.Id id) {
                var tags = Tags.concat(id.getTagsAsIterable(), Tags.of("DYNAMIC_FILTER_TAG", UUID.randomUUID().toString()));
                return id.withTags(tags);
            }
        };
    }
}
