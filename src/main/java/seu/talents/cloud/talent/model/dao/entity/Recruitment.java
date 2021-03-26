package seu.talents.cloud.talent.model.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recruitment {
    Long recruitmentId;
    String recruitmentName;
    String detailLink;
    String city;
    @Column(name = "time")
    String time;
    String publishRange;
    String source;
}
