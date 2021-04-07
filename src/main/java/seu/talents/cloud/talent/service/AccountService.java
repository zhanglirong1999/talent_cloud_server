package seu.talents.cloud.talent.service;

import org.springframework.validation.annotation.Validated;
import seu.talents.cloud.talent.model.dto.post.AccountAllDTO;
import seu.talents.cloud.talent.model.dto.post.AdminDTO;
import seu.talents.cloud.talent.model.dto.post.Register;

@Validated
public interface AccountService {
    void registerUser(Register register,String accountId);
    Object getUserInfo(String accountId);
    String adminLogin(AdminDTO adminDTO);
    AccountAllDTO getAccountAllDTOById(String accountId);
}
