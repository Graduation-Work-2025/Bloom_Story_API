package bloom_story.domain.location.service;

import java.util.Arrays;
import java.util.List;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    private static final GeometryFactory geometryFactory = new GeometryFactory();

    public static Point convertToPoint(double longitude, double latitude) {
        return geometryFactory.createPoint(new Coordinate(longitude, latitude));
    }

    public static List<Double> extractFromPoint(Point point) {
        if (point == null) {
            return null;
        }
        return Arrays.asList(point.getX(), point.getY());
    }
}
