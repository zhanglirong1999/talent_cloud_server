package seu.talents.cloud.talent.model.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class job {
    String accountId;
    String job;
    String company;
    @Column(name = "startTime")
    String startTime;
    @Column(name = "endTime")
    String endTime;
    Integer deleted;
}
