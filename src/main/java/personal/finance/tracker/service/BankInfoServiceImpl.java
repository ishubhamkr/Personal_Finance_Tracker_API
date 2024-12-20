package personal.finance.tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import personal.finance.tracker.entity.BankInfo;
import personal.finance.tracker.entity.User;
import personal.finance.tracker.model.BankDetails;
import personal.finance.tracker.repository.BankInfoRepository;
import personal.finance.tracker.repository.UserRepository;
import personal.finance.tracker.request.BankRegisterRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BankInfoServiceImpl implements  BankInfoService{

    @Autowired
    private BankInfoRepository bankInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public BankDetails addBankInfo(BankRegisterRequest bankData) {
        // Validate that the user exists
        User user = userRepository.findByUserName(bankData.getUserId());
        if (ObjectUtils.isEmpty(user)) {
            throw new IllegalArgumentException("Invalid userId: User does not exist");
        }

        List<BankInfo> bankInfoList = bankInfoRepository.findByUserIdAndBankNameAndBankIFSC(bankData.getUserId(), bankData.getBankName(), bankData.getBankIFSC());

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
        bankDataUpdate.setUpdatedDt(LocalDateTime.now());
        bankDataUpdate.setCreatedDt(LocalDateTime.now());
        bankInfoRepository.save(bankDataUpdate);

        BankDetails bankDetails = new BankDetails();
        bankDetails.setBankName(bankData.getBankName());
        bankDetails.setBalance(bankData.getBalance());


        return bankDetails;

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
            BankDetails bankDetails = new BankDetails();
            bankDetails.setBalance(bankInfo.getBalance());
            bankDetails.setBankName(bankInfo.getBankName());
            bankDetailsList.add(bankDetails);
        }
        return bankDetailsList;
    }
}
