package personal.finance.tracker.request;

import lombok.Data;

@Data
public class BankRegisterRequest {
    private String bankName;
    private String bankIFSC;
    private String accountNumber;
    private String userId;
    private Integer balance;
}
