package personal.finance.tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import personal.finance.tracker.entity.BankInfo;
import personal.finance.tracker.entity.BorrowerDetails;
import personal.finance.tracker.entity.Transaction;
import personal.finance.tracker.entity.User;
import personal.finance.tracker.repository.BankInfoRepository;
import personal.finance.tracker.repository.BorrowerDetailsRepository;
import personal.finance.tracker.repository.TransactionRepository;
import personal.finance.tracker.repository.UserRepository;
import personal.finance.tracker.util.Constant;

import java.time.LocalDateTime;
import java.util.List;

public class BorrowerDetailsServiceImpl implements  BorrowerDetailsService{
    @Autowired
    private BorrowerDetailsRepository borrowerDetailsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankInfoRepository bankInfoRepo;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public BorrowerDetails saveBorrowerDetails(BorrowerDetails borrowerDetails) {
        // Validate that the user exists
        User user = userRepository.findByUserName(borrowerDetails.getUserId());
        if (ObjectUtils.isEmpty(user)) {
            throw new IllegalArgumentException("Invalid userId: User does not exist");
        }

        BankInfo bankInfo = bankInfoRepo.findByUserIdAndAccountNumber(borrowerDetails.getUserId(),borrowerDetails.getAccountNumber());
        if (ObjectUtils.isEmpty(bankInfo)) {
            throw new IllegalArgumentException("Invalid request: Bank Details does not exist");
        }
        bankInfo.setBalance(bankInfo.getBalance() - borrowerDetails.getAmount());
        bankInfoRepo.save(bankInfo);

        Transaction transaction = new Transaction();
        transaction.setAccountNumber(borrowerDetails.getAccountNumber());
        transaction.setUserId(bankInfo.getUserId());
        transaction.setType(Constant.EXPENSE_MONEY_LEND);
        transaction.setAmount(bankInfo.getBalance());
        transaction.setGainOrLoss(false);
        transaction.setCreatedDt(LocalDateTime.now());
        transactionRepository.save(transaction);

        return borrowerDetailsRepository.save(borrowerDetails);
    }

    @Override
    public List<BorrowerDetails> getBorrowerDetailsByUserIdAndBankId(String userId, String bankId) {
        return borrowerDetailsRepository.findByUserIdAndBankId(userId, bankId);
    }
}
