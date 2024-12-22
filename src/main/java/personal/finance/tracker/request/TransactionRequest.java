package personal.finance.tracker.request;

import lombok.Data;
import lombok.NonNull;

@Data
public class TransactionRequest {
    @NonNull
    private String accountNumber;
    @NonNull
    private String userId;

    private String type;

}
