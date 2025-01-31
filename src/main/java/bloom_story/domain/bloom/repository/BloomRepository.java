package bloom_story.domain.bloom.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import bloom_story.domain.bloom.model.Bloom;

public interface BloomRepository extends Repository<Bloom, Integer> {

    Optional<Bloom> findById(Integer id);

    default Bloom getById(Integer id) {
        return findById(id)
            .orElseThrow(() -> new RuntimeException("id: " + id));
    }
}
