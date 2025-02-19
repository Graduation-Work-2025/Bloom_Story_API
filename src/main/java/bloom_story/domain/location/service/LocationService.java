package bloom_story.domain.location.service;

import java.util.Arrays;
import java.util.List;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import bloom_story.domain.comunity.story.dto.StoriesResponse;
import bloom_story.domain.comunity.story.model.Story;
import bloom_story.domain.location.repository.LocationRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final GeometryFactory geometryFactory = new GeometryFactory();
    private static final double DISTANCE = 4.0;

    private final LocationRepository locationRepository;

    public Point convertToPoint(double longitude, double latitude) {
        return geometryFactory.createPoint(new Coordinate(longitude, latitude));
    }

    public static List<Double> extractFromPoint(Point point) {
        if (point == null) {
            return null;
        }
        return Arrays.asList(point.getX(), point.getY());
    }

    public StoriesResponse getNearbyStories(double longitude, double latitude) {
        String point = String.format("POINT(%.5f %.5f)", longitude, latitude);
        List<Story> stories = locationRepository.findStoriesWithinDistance(point, DISTANCE);
        return StoriesResponse.from(stories);
    }
}
