package jun.invitation.service;

import jun.invitation.domain.Review;
import jun.invitation.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public void writeReview(Review review) {
        reviewRepository.save(review);
    }

}
