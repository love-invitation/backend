package jun.invitation.domain.image.dao;

import jun.invitation.domain.image.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    @Modifying
    @Query("delete from Image i where i in :images")
    void deleteByImages(@Param("images")List<Image> images);

}
