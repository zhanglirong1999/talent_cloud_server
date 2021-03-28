package seu.talents.cloud.talent.model.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Job {
    String accountId;
    @Column(name ="jobId")
    Long jobId;
    String company;
    @Column(name = "startTime")
    String startTime;
    @Column(name = "endTime")
    String endTime;
    Integer deleted;
    String position;
}
