package personal.finance.tracker.service;

import personal.finance.tracker.entity.BorrowerDetails;

import java.util.List;

public interface BorrowerDetailsService {
    BorrowerDetails saveBorrowerDetails(BorrowerDetails borrowerDetails);

    List<BorrowerDetails> getBorrowerDetailsByUserId(String userId);
}
