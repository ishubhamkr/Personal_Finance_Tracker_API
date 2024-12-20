package personal.finance.tracker.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "User_Authentication")
@Data
public class User {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String userName;
    private String address;

}
