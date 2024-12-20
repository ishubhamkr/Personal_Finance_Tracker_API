package personal.finance.tracker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import personal.finance.tracker.entity.BankInfo;

import java.util.List;

@Repository
public interface BankInfoRepository extends MongoRepository<BankInfo, String> {
    List<BankInfo> findByUserId(String userId);

    List<BankInfo> findByUserIdAndBankNameAndBankIFSC(String userId,String bankName,String bankIFSC);
}
