package jun.invitation.service;

import jun.invitation.domain.coupon.Coupon;
import jun.invitation.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    public void saveCoupon(Coupon coupon) {
        couponRepository.save(coupon);
    }

}
