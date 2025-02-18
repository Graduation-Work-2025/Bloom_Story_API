package bloom_story.domain.bloom.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bloom_story.domain.bloom.model.Bloom;
import bloom_story.domain.bloom.service.BloomService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blooms")
public class BloomController {

    private final BloomService bloomService;

    @Operation(summary = "스토리 조회")
    @GetMapping("/{id}")
    public ResponseEntity<Bloom> getStory(
        @PathVariable Integer id
    ) {
        Bloom response = bloomService.getBloomById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "모든 스토리 조회")
    @GetMapping
    public ResponseEntity<List<Bloom>> getStories() {
        return ResponseEntity.ok(bloomService.getBlooms());
    }
}

