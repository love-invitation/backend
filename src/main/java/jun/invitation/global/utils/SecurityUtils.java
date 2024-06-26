package jun.invitation.global.utils;

import jun.invitation.auth.PrincipalDetails;
import jun.invitation.domain.user.domain.User;
import jun.invitation.domain.user.exception.UserNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
public class SecurityUtils {
    public static User getCurrentUser() {

        try {
            PrincipalDetails principalDetails = (PrincipalDetails) SecurityContextHolder.
                    getContext().getAuthentication().getPrincipal();

            return principalDetails.getUser();
        } catch (NullPointerException e) {
            throw new UserNotFoundException(e);
        }
    }
}
