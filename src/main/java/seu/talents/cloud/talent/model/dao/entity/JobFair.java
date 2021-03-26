package seu.talents.cloud.talent.model.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobFair {
    Long jobFairId;
    String jobFairName;
    String city;
    String address;
    String type;
    String time;
    @Column(name = "startTime")
    String startTime;
    @Column(name = "endTime")
    String endTime;
    String publishRange;
    String detailLink;
    String source;
}
