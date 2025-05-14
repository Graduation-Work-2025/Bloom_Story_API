package bloom_story.domain.location.service;

import java.util.Arrays;
import java.util.List;

import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocationService {

    public static List<Double> extractFromPoint(Point point) {
        if (point == null) {
            return null;
        }
        return Arrays.asList(point.getX(), point.getY());
    }
}
