package com.webest.store.store.application;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.domain.geo.GeoLocation;
import org.springframework.data.redis.domain.geo.Metrics;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
public class GeoOperation {

    private static final String STORE_CACHE_PREFIX = "store:";

    @Resource
    private GeoOperations<String, String> geoOperations;

    public Long add(double x, double y, String member) {
        Point point = new Point(x, y);
        return geoOperations.add(STORE_CACHE_PREFIX, point, member);
    }

    public List<String> findNearByStores(Double x, Double y, Double radius) {
        Circle circle = new Circle(new Point(x, y), new Distance(radius, Metrics.KILOMETERS));
        GeoResults<RedisGeoCommands.GeoLocation<String>> res = geoOperations.radius(STORE_CACHE_PREFIX, circle);
        return res.getContent().stream()
                .map(GeoResult::getContent)
                .map(GeoLocation::getName)
                .collect(Collectors.toList());
    }
}
