package bloom_story.domain.location.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import bloom_story.domain.story.model.Story;

public interface LocationRepository extends Repository<Story, Integer> {

    @Query(value = "SELECT * FROM stories " +
        "WHERE ST_Distance_Sphere(location, ST_GeomFromText(:point)) <= :distance",
        nativeQuery = true)
    List<Story> findStoriesWithinDistance(@Param("point") String point, @Param("distance") double distance);

}
