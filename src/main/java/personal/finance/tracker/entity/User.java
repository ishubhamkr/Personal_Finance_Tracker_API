package personal.finance.tracker.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

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
    @LastModifiedDate
    private LocalDateTime updatedDt;
    @CreatedDate
    private LocalDateTime createdDt;

}
