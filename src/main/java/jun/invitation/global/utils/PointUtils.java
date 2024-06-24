package jun.invitation.global.utils;

import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

@Slf4j
public class PointUtils {
    public static Point PointConvert(Double longitude, Double latitude) {

        if (longitude == null || latitude == null) {
            return null;
        }

        Point point = null;
        try {
            String pointWKT = String.format("POINT(%s %s)", longitude, latitude);
            point = (Point) new WKTReader().read(pointWKT);
        } catch (ParseException e) {
            log.info("[message : WKTReader().read(pointWKT) 수행 중 ParseException] 발생, point = null 후 정상 흐름으로 이어가겠습니다.");
            point = null;
        }
        return point;
    }
}
