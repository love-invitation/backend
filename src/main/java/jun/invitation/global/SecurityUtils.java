package jun.invitation.global;

import jun.invitation.auth.PrincipalDetails;
import jun.invitation.domain.user.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;
public class SecurityUtils {
    public static User getCurrentUser() {
        PrincipalDetails principalDetails = (PrincipalDetails) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();

        return principalDetails.getUser();
    }
}
