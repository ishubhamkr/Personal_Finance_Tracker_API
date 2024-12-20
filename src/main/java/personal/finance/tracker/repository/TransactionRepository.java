package personal.finance.tracker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import personal.finance.tracker.entity.Transaction;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {
    // Custom methods for Expense data
}
