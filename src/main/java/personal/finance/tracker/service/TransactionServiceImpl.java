package personal.finance.tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import personal.finance.tracker.entity.BankInfo;
import personal.finance.tracker.entity.Transaction;
import personal.finance.tracker.entity.User;
import personal.finance.tracker.model.BankDetails;
import personal.finance.tracker.model.TransactionResponseModel;
import personal.finance.tracker.repository.BankInfoRepository;
import personal.finance.tracker.repository.TransactionRepository;
import personal.finance.tracker.repository.UserRepository;
import personal.finance.tracker.request.TransactionRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankInfoRepository bankInfoRepo;

    @Override
    @Transactional
    public BankDetails addTransaction(Transaction transaction) {
        // Validate that the user exists
        User user = userRepository.findByUserName(transaction.getUserId());
        if (ObjectUtils.isEmpty(user)) {
            throw new IllegalArgumentException("Invalid userId: User does not exist");
        }
        BankInfo bankInfo = bankInfoRepo.findByUserIdAndAccountNumber(transaction.getUserId(),transaction.getAccountNumber());
        if (ObjectUtils.isEmpty(bankInfo)) {
            throw new IllegalArgumentException("Invalid request: Bank Details does not exist");
        }

        if(transaction.getGainOrLoss())
        {
            bankInfo.setBalance(bankInfo.getBalance() + transaction.getAmount());
        }
        else{
            bankInfo.setBalance(bankInfo.getBalance() - transaction.getAmount());
        }
        bankInfoRepo.save(bankInfo);
        transaction.setCreatedDt(LocalDateTime.now());
        transactionRepository.save(transaction);
        return getBankDetails(bankInfo);
    }

    @Override
    public TransactionResponseModel getTransactionForUser(TransactionRequest request) {

        User user = userRepository.findByUserName(request.getUserId());
        if (ObjectUtils.isEmpty(user)) {
            throw new IllegalArgumentException("Invalid userId: User does not exist");
        }
        BankInfo bankInfo = bankInfoRepo.findByUserIdAndAccountNumber(request.getUserId(),request.getAccountNumber());
        if (ObjectUtils.isEmpty(bankInfo)) {
            throw new IllegalArgumentException("Invalid request: Bank Details does not exist");
        }

        List<Transaction> transactions = new ArrayList<>();

        if(request.getType()==null || request.getType().isEmpty()) {
            transactions = transactionRepository.findByUserIdAndAccountNumber(request.getUserId(), request.getAccountNumber());
        }
        else{
            transactions = transactionRepository.findByUserIdAndAccountNumberAndTypeIgnoreCase(request.getUserId(), request.getAccountNumber(),request.getType());
        }

        if (ObjectUtils.isEmpty(bankInfo)) {
            throw new IllegalArgumentException("Invalid request: Transactions does not exist");
        }

        TransactionResponseModel transactionResponseModel = new TransactionResponseModel();
        transactionResponseModel.setName(user.getName());
        transactionResponseModel.setBankDetails(getBankDetails(bankInfo));
        transactionResponseModel.setTransactions(transactions);
        return transactionResponseModel;
    }

    private BankDetails getBankDetails(BankInfo bankInfo)
    {
        BankDetails bankDetails = new BankDetails();
        bankDetails.setBankName(bankInfo.getBankName());
        bankDetails.setBalance(bankInfo.getBalance());
        bankDetails.setAccountNumber(bankInfo.getAccountNumber());
        return bankDetails;
    }
}
