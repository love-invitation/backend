package jun.invitation.domain.priority;

public enum PriorityName {
    BRIDE("bride"),
    GROOM("groom"),
    THUMBNAIL("thumbnail"),
    GALLERY("gallery"),
    CONTACT("contact"),
    ACCOUNT("account"),
    TRANSPORT("transport"),
    BOOKING("booking"),
    ARTICLE("article"),
    TSID("tsid"),
    ISPAID("isPaid"),
    COVER("cover"),
    PLACE("place");

    private String priorityName;

    PriorityName(String priorityName) {
        this.priorityName = priorityName;
    }

    public String getPriorityName() {
        return this.priorityName;
    }

    public static PriorityName fromPriorityName(String priorityName) {
        for (PriorityName invitationProperties : PriorityName.values()) {
            if (invitationProperties.getPriorityName().equals(priorityName)) {
                return invitationProperties;
            }
        }
        throw new IllegalArgumentException("No priorityName in Enum :" + priorityName);
    }
}
