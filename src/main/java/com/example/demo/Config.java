package com.example.demo;

import io.micrometer.core.aop.CountedAspect;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.config.MeterFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    private String next = "first";

    @Bean
    public CountedAspect countedAspect(MeterRegistry registry) {
        return new CountedAspect(registry);
    }

    @Bean
    public MeterFilter meterFilter() {
        return new MeterFilter() {
            @Override
            public Meter.Id map(Meter.Id id) {
                var tags = Tags.concat(id.getTagsAsIterable(), Tags.of("DYNAMIC_FILTER_TAG", next));
                prepareNext();
                return id.withTags(tags);
            }
        };
    }

    private void prepareNext() {
        if (next.equals("first")) {
            next = "second";
        } else {
            next = "first";
        }
    }
}
