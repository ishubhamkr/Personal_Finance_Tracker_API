package personal.finance.tracker.model;

import lombok.Data;

@Data
public class BankDetails {
    private String bankName;
    private String accountNumber;
    private Integer balance;
}
