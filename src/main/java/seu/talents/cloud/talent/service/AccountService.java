package seu.talents.cloud.talent.service;

import org.springframework.validation.annotation.Validated;
import seu.talents.cloud.talent.model.dto.post.*;

@Validated
public interface AccountService {
    void registerUser(Register register,String accountId);
    Object getUserInfo(String accountId);
    String adminLogin(AdminDTO adminDTO);
    AccountAllDTO getAccountAllDTOById(String accountId);
    void modifyUser(ModifyDTO register,String accountId);
    void addJob(AddJob addJob,String accountId);
    void modifyJob(ModifyJobDTO modifyJobDTO,String accountId);
    void deleteJob(Long jobId);
}
