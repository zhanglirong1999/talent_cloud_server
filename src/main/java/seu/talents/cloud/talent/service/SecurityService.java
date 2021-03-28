package seu.talents.cloud.talent.service;

import seu.talents.cloud.talent.model.dto.returnDTO.JobDTO;

public interface SecurityService {
    boolean checkoutJobDTOSecurity(JobDTO obDTO);

}
