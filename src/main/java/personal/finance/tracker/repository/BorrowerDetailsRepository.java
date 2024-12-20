package personal.finance.tracker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import personal.finance.tracker.entity.BorrowerDetails;

@Repository
public interface BorrowerDetailsRepository extends MongoRepository<BorrowerDetails, String> {
    // Custom methods for Lender data
}
