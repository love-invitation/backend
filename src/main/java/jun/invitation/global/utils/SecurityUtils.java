package jun.invitation.global.utils;

import jun.invitation.global.auth.oauth.PrincipalDetails;
import jun.invitation.domain.user.domain.User;
import jun.invitation.domain.user.exception.UserNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static User getCurrentUser() {

        if (SecurityContextHolder.getContext().
                getAuthentication() == null) {
            return null;
        }
        
        try {

            Object principal = SecurityContextHolder.getContext().
                    getAuthentication().getPrincipal();

            if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof PrincipalDetails) {
                PrincipalDetails principalDetails = (PrincipalDetails) principal;

                return principalDetails.getUser();
            } else {
                return null;
            }

        } catch (NullPointerException e) {
            throw new UserNotFoundException(e);
        }
    }
}
