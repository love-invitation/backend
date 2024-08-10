package jun.invitation.domain.priority;

import java.util.Arrays;

public enum PriorityName {
    THUMBNAIL("thumbnail"),
    GALLERY("gallery"),
    CONTACT("contact"),
    ACCOUNT("account"),
    TRANSPORT("transport"),
    BOOKING("booking"),
    ARTICLE("article"),
    TSID("tsid"),
    COVER("cover"),
    PLACE("place");

    private final String priorityName;

    PriorityName(String priorityName) {
        this.priorityName = priorityName;
    }

    public String getPriorityName() {
        return this.priorityName;
    }

    public static PriorityName fromPriorityName(String priorityName) {
        return Arrays.stream(PriorityName.values())
                .filter(p -> p.getPriorityName().equals(priorityName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No priorityName in Enum :" + priorityName));
    }
}
