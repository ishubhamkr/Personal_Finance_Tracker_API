package personal.finance.tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import personal.finance.tracker.entity.BankInfo;
import personal.finance.tracker.entity.Transaction;
import personal.finance.tracker.entity.User;
import personal.finance.tracker.model.BankDetails;
import personal.finance.tracker.repository.BankInfoRepository;
import personal.finance.tracker.repository.TransactionRepository;
import personal.finance.tracker.repository.UserRepository;
import personal.finance.tracker.request.BankRegisterRequest;
import personal.finance.tracker.util.Constant;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BankInfoServiceImpl implements  BankInfoService{

    @Autowired
    private BankInfoRepository bankInfoRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public BankDetails addBankInfo(BankRegisterRequest bankData) {
        // Validate that the user exists
        User user = userRepository.findByUserName(bankData.getUserId());
        if (ObjectUtils.isEmpty(user)) {
            throw new IllegalArgumentException("Invalid userId: User does not exist");
        }

        BankInfo bankInfoList = bankInfoRepository.findByUserIdAndAccountNumber(bankData.getUserId(), bankData.getAccountNumber());

        if(!ObjectUtils.isEmpty(bankInfoList))
        {
            throw new IllegalArgumentException("Bank Details already exist");
        }

        BankInfo bankDataUpdate = new BankInfo();
        bankDataUpdate.setBankIFSC(bankData.getBankIFSC());
        bankDataUpdate.setBankName(bankData.getBankName());
        bankDataUpdate.setUserId(bankData.getUserId());
        bankDataUpdate.setAccountNumber(bankData.getAccountNumber());
        bankDataUpdate.setBalance(bankData.getBalance());
        bankInfoRepository.save(bankDataUpdate);

        Transaction transaction = new Transaction();
        transaction.setAccountNumber(bankData.getAccountNumber());
        transaction.setUserId(bankData.getUserId());
        transaction.setType(Constant.INITIAL_AMOUNT);
        transaction.setAmount(bankData.getBalance());
        transaction.setGainOrLoss(true);
        transaction.setCreatedDt(LocalDateTime.now());
        transactionRepository.save(transaction);


        return getBankDetails(bankDataUpdate);

    }

    @Override
    public List<BankDetails> getBankInfoByUserId(String userId) {

        List<BankInfo> bankInfoList = bankInfoRepository.findByUserId(userId);

        if(ObjectUtils.isEmpty(bankInfoList))
        {
            throw new IllegalArgumentException("Bank Details does not exist");
        }

        List<BankDetails> bankDetailsList = new ArrayList<>();

        for(BankInfo bankInfo : bankInfoList)
        {
            BankDetails bankDetails = getBankDetails(bankInfo);
            bankDetailsList.add(bankDetails);
        }
        return bankDetailsList;
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
