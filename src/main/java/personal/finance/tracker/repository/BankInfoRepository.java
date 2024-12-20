package personal.finance.tracker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import personal.finance.tracker.entity.BankInfo;

@Repository
public interface BankInfoRepository extends MongoRepository<BankInfo, String> {
    // Define custom query methods if needed
}
