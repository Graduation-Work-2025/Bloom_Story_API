package bloom_story.domain.comunity.story.model;

import lombok.Getter;

@Getter
public enum SharingType {
    PUBLIC("전체 공개"),
    FRIEND("친구 공개"),
    PRIVATE("비공개")
    ;

    private String label;

    SharingType(String label) {
        this.label = label;
    }
}
