package personal.finance.tracker.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "BorrowerDetails")
@Data
public class BorrowerDetails {
    @Id
    private String id;
    private String userId;
    private String bankId;
    private Integer amount;
    private String moneyBorrowerName;
}
