package com.webest.store.store.application;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.domain.geo.GeoLocation;
import org.springframework.data.redis.domain.geo.Metrics;
import org.springframework.stereotype.Component;

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
        GeoResults<RedisGeoCommands.GeoLocation<String>> res = geoOperations.radius(
            STORE_CACHE_PREFIX, circle);
        return res.getContent().stream()
            .map(GeoResult::getContent)
            .map(GeoLocation::getName)
            .collect(Collectors.toList());
    }

}
