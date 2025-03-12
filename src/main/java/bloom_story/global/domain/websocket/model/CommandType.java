package bloom_story.global.domain.websocket.model;

public enum CommandType {
    SIGN_UP ("sign_up"),
    LOGIN ("login"),
    GET_USER ("get_user"),

    CREATE_STORY ("create_story"),
    GET_STORY ("get_story"),
    GET_STORIES ("get_stories"),
    GET_MY_STORIES ("get_my_stories"),
    ;

    private String label;

    CommandType(String label) {
        this.label = label;
    }

    public static CommandType from(String label) {
        for (CommandType type : CommandType.values()) {
            if (type.label.equals(label)) {
                return type;
            }
        }
        return null;
    }
}
