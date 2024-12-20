package personal.finance.tracker.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Bank_Details")
@Data
public class BankInfo {
    @Id
    private String id;
    private String bankName;
    private String bankIFSC;
    private String accountNumber;
    private String userId;
    private Integer balance;
}
