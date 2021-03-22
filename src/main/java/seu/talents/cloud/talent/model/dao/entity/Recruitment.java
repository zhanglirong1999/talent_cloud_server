package seu.talents.cloud.talent.model.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recruitment {
    @JsonIgnore
    Long recruitmentId;
    String recruitmentName;
    String detailLink;
    String city;
    @Column(name = "time")
    String time;
    String publishRange;
}
