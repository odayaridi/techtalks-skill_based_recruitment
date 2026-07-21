package org.example.techtalksskillbasedrecruitment.ratelimit;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

@Component
public class SlidingWindowRateLimiter {

    private final ConcurrentHashMap<String, ConcurrentLinkedDeque<Long>> requestTimestamps =
            new ConcurrentHashMap<>();

    public boolean isAllowed(String key, int maxRequests, int windowSeconds) {

        long currentTime = System.currentTimeMillis();

        long windowStart =
                currentTime - (windowSeconds * 1000L);

        ConcurrentLinkedDeque<Long> timestamps =
                requestTimestamps.computeIfAbsent(
                        key,
                        ignored -> new ConcurrentLinkedDeque<>()
                );

        synchronized (timestamps) {

            while (!timestamps.isEmpty()
                    && timestamps.peekFirst() < windowStart) {

                timestamps.pollFirst();
            }

            if (timestamps.size() >= maxRequests) {
                return false;
            }

            timestamps.addLast(currentTime);

            return true;
        }
    }
}