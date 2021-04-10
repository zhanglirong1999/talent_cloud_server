package seu.talents.cloud.talent.model.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Job {
    @Column(name ="accountId")
    String accountId;
    @Column(name ="jobId")
    Long jobId;
    String company;
    @Column(name = "startTime")
    Long startTime;
    @Column(name = "endTime")
    Long endTime;
    Integer deleted;
    String position;
    String industry;
}
