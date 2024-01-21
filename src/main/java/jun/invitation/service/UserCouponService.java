package jun.invitation.service;

import jun.invitation.domain.User;
import jun.invitation.domain.coupon.Coupon;
import jun.invitation.domain.coupon.UserCoupon;
import jun.invitation.repository.CouponRepository;
import jun.invitation.repository.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCouponService {

    private final UserCouponRepository userCouponRepository;
    private final CouponService couponService;

    public List<UserCoupon> getCouponList(Long userId) {
        return userCouponRepository.findByUserId(userId);
    }

    public void save(User user, Coupon coupon) {
        UserCoupon userCoupon = new UserCoupon();

        couponService.saveCoupon(coupon);

        userCoupon.setCoupon(coupon);
        userCoupon.setUser(user);

        userCouponRepository.save(userCoupon);
    }
}
