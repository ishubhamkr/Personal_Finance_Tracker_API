package personal.finance.tracker.service;

import personal.finance.tracker.entity.BankInfo;
import personal.finance.tracker.model.BankDetails;
import personal.finance.tracker.request.BankRegisterRequest;

import java.util.List;

public interface BankInfoService {
    BankDetails addBankInfo(BankRegisterRequest bankInfo);

    List<BankDetails> getBankInfoByUserId(String userId);
}
