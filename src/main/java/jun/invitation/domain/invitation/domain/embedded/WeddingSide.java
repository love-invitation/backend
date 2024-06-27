package jun.invitation.domain.invitation.domain.embedded;

import jun.invitation.domain.priority.PriorityName;

public enum WeddingSide {
    BRIDE("bride"),
    GROOM("groom");
    private String side;

    WeddingSide(String side) {
        this.side = side;
    }

    public String getSide() {
        return side;
    }

    public static WeddingSide fromWeddingSide(String weddingSide) {
        for (WeddingSide w : WeddingSide.values()) {
            if (w.getSide().equals(weddingSide)) {
                return w;
            }
        }
        throw new IllegalArgumentException("No WeddingSide in Enum :" + weddingSide);
    }
}
