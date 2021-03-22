package seu.talents.cloud.talent.model.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobFair {
    @JsonIgnore
    Long jobFairId;
    String jobFairName;
    String city;
    String address;
    String type;
    String time;
    String detailLink;
}
