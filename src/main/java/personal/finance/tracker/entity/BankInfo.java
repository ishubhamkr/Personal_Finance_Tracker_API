package personal.finance.tracker.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

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
    @LastModifiedDate
    private LocalDateTime updatedDt;
    @CreatedDate
    private LocalDateTime createdDt;
}
