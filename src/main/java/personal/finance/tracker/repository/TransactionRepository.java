package personal.finance.tracker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import personal.finance.tracker.entity.Transaction;

import java.util.List;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {
    List<Transaction> findByUserIdAndAccountNumber(String userId,String accountNumber);

    List<Transaction> findByUserIdAndAccountNumberAndTypeIgnoreCase(String userId, String accountNumber, String type);
}
