package bloom_story.global.domain;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import java.io.IOException;

public class PointDeserializer extends JsonDeserializer<Point> {
    
    private static final GeometryFactory geometryFactory = new GeometryFactory();

    @Override
    public Point deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        double[] coordinates = p.readValueAs(double[].class); // JSON 배열을 double 배열로 읽음
        if (coordinates.length != 2) {
            throw new IOException("Invalid Point format. Expected [longitude, latitude]");
        }
        double longitude = coordinates[0];
        double latitude = coordinates[1];
        return geometryFactory.createPoint(new Coordinate(longitude, latitude));
    }
}

