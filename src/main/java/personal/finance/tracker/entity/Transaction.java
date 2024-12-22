package personal.finance.tracker.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "Transaction")
@Data
public class Transaction {
    @Id
    private String id;
    private String userId;
    private String accountNumber;
    private Integer amount;
    private String type;
    private Boolean gainOrLoss; //gain : true || loss : false
    @CreatedDate
    private LocalDateTime createdDt;
}
