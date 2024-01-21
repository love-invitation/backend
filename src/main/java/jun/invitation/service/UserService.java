package jun.invitation.service;

import jun.invitation.domain.User;
import jun.invitation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> findUser(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findUserByUser_id(Long user_id) {
        return userRepository.findById(user_id);
    }

}
