package seu.talents.cloud.talent.service;

import org.springframework.validation.annotation.Validated;
import seu.talents.cloud.talent.model.dto.post.Register;

@Validated
public interface AccountService {
    void registerUser(Register register,String accountId);
    Object getUserInfo(String accountId);
}
