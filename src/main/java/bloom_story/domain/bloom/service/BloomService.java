package bloom_story.domain.bloom.service;

import java.util.List;

import org.springframework.stereotype.Service;

import bloom_story.domain.bloom.model.Bloom;
import bloom_story.domain.bloom.repository.BloomRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BloomService {

    private BloomRepository bloomRepository;

    public Bloom getBloomById(Integer id) {
        return bloomRepository.getById(id);
    }

    public List<Bloom> getBlooms() {
        return bloomRepository.findAll();
    }
}
