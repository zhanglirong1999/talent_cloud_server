package seu.talents.cloud.talent.model.dto.returnDTO;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import seu.talents.cloud.talent.model.dao.entity.Job;

@Data
public class JobDTO {
    private String accountId;
    private Long jobId;
    private String company;
    private String job;
    private Long startTime;
    private Long endTime;
    private String industry;

    public JobDTO() {
    }

    public JobDTO(Job job) {
        BeanUtils.copyProperties(job, this);
    }

    public Job toJob() {
        Job job = new Job();
        BeanUtils.copyProperties(this, job);
        return job;
    }
}
