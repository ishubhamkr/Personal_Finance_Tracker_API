package personal.finance.tracker.model;

import lombok.Data;
import personal.finance.tracker.entity.Transaction;

import java.util.List;

@Data
public class TransactionResponseModel {
    private String name;
    private BankDetails bankDetails;
    private List<Transaction> transactions;
}
