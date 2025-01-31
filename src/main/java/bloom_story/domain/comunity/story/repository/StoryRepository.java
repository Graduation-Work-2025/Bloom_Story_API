package bloom_story.domain.comunity.story.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import bloom_story.domain.comunity.story.model.Story;

public interface StoryRepository extends Repository<Story, Integer> {

    Story save(Story story);

    void delete(Story story);

    Optional<Story> findById(Integer id);

    default Story getById(Integer id) {
        return findById(id)
            .orElseThrow(() -> new RuntimeException("id: " + id));
    }

    default List<Story> findAllByLocation(String location) {
        return null;
    }
}
