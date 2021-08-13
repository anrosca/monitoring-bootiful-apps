package inc.evil.stock.investment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InvestmentRepository extends JpaRepository<Investment, String> {
    @Query("select i from Investment i where i.user.id = :userId order by i.createdAt")
    List<Investment> findAllInvestmentsForUser(@Param("userId") String userId);

    @Query(
            value = "select i from Investment i where i.user.id = :userId order by i.createdAt",
            countQuery = "select count(i) from Investment i where i.user.id = :userId"
    )
    Page<Investment> findAllInvestmentsForUser(@Param("userId") String userId, Pageable pageable);
}
