package inc.evil.stock.investment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InvestmentRecordRepository extends JpaRepository<InvestmentRecord, String> {
    @Query("select ir from InvestmentRecord  ir join fetch ir.investment i where i.user.id = :userId order by ir.createdAt")
    List<InvestmentRecord> findInvestmentRecordsByUserId(@Param("userId") String userId);

    @Query("select ir from InvestmentRecord  ir join fetch ir.investment i where i.id = :investmentId order by ir.createdAt")
    List<InvestmentRecord> findInvestmentRecordsForInvestment(@Param("investmentId") String investmentId);

    @Query(
            value = "select ir from InvestmentRecord  ir join fetch ir.investment i where i.id = :investmentId order by ir.createdAt",
            countQuery = "select count(ir) from InvestmentRecord  ir join ir.investment i where i.id = :investmentId"
    )
    Page<InvestmentRecord> findInvestmentRecordsForInvestment(@Param("investmentId") String investmentId, Pageable pageable);
}
