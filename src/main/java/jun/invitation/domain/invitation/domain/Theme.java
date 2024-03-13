package jun.invitation.domain.invitation.domain;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Data
public class Theme {

    private String theme;
    private String font;
    private String color;
    private String themeOption;

}
