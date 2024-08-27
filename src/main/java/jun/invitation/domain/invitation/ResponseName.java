package jun.invitation.domain.invitation;

import java.util.Arrays;

public enum ResponseName {
    THUMBNAIL("thumbnail"),
    GALLERY("gallery"),
    CONTACT("contact"),
    ACCOUNT("account"),
    TRANSPORT("transport"),
    BOOKING("booking"),
    ARTICLE("article"),
    TSID("tsid"),
    COVER("cover"),
    PLACE("place"),
    GUESTBOOKCHECK("guestbookcheck"),;

    private final String priorityName;

    ResponseName(String priorityName) {
        this.priorityName = priorityName;
    }

    public String getPriorityName() {
        return this.priorityName;
    }

    public static ResponseName fromPriorityName(String priorityName) {
        return Arrays.stream(ResponseName.values())
                .filter(p -> p.getPriorityName().equals(priorityName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No priorityName in Enum :" + priorityName));
    }
}