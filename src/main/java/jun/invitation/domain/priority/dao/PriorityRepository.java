package jun.invitation.domain.priority.dao;

import jun.invitation.domain.priority.domain.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {

    @Modifying
    @Query(value = "delete from Priority p where p.product_id= :id", nativeQuery = true)
    void deleteByProductId(@Param("id") Long productId);
}
