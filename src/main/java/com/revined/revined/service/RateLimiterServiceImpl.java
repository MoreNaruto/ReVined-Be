package com.revined.revined.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import io.github.bucket4j.local.LocalBucketBuilder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimiterServiceImpl implements RateLimiterService {
    Map<String, Bucket> bucketCache = new ConcurrentHashMap<>();
    @Override
    public Bucket resolveBucket(String cookieId) {
        return bucketCache.computeIfAbsent(cookieId, this::newBucket);
    }

    private Bucket newBucket(String cookieId) {
        return new LocalBucketBuilder()
                .addLimit(Bandwidth.classic(10, Refill.intervally(5, Duration.ofSeconds(10))))
                .build();
    }
}
