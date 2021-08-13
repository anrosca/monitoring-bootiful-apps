package inc.evil.stock.investment;

import inc.evil.stock.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InvestmentService {
    private final InvestmentRepository investmentRepository;
    private final UserService userService;

    public InvestmentService(InvestmentRepository investmentRepository, UserService userService) {
        this.investmentRepository = investmentRepository;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public List<Investment> findAllInvestmentsForUser(String userId) {
        return investmentRepository.findAllInvestmentsForUser(userId);
    }

    @Transactional(readOnly = true)
    public Page<Investment> findAllInvestmentsForUser(String userId, Pageable pageable) {
        return investmentRepository.findAllInvestmentsForUser(userId, pageable);
    }

    @Transactional(readOnly = true)
    public Investment findById(String id) {
        return investmentRepository.findById(id)
                .orElseThrow(() -> new InvestmentNotFoundException("No investment with id: '" + id + "' was found."));
    }

    @Transactional
    public Investment create(String userId, Investment investment) {
        investment.setUser(userService.findById(userId));
        return investmentRepository.save(investment);
    }
}
