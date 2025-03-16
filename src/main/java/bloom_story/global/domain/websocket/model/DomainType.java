package bloom_story.global.domain.websocket.model;

public enum DomainType {
    USER("user"),
    STORY("story"),
    FRIEND("friend"),
    ;

    private String label;

    DomainType(String label) {
        this.label = label;
    }

    public static DomainType from(String label) {
        for (DomainType type : DomainType.values()) {
            if (type.label.equals(label)) {
                return type;
            }
        }
        return null;
    }

}
