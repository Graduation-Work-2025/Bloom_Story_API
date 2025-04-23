package bloom_story.domain.location.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bloom_story.domain.location.service.LocationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/location")
public class LocationController implements LocationApi {

    private final LocationService locationService;

    // @Operation(summary = "모든 스토리 조회")
    // @GetMapping
    // public ResponseEntity<StoriesResponse> getNearbyStories(
    //     @RequestParam double longitude,
    //     @RequestParam double latitude
    // ) {
    //     StoriesResponse response = locationService.getNearbyStories(longitude, latitude);
    //     return ResponseEntity.ok(response);
    // }
}

