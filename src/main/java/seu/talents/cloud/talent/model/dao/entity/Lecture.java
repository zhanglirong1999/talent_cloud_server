package seu.talents.cloud.talent.model.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lecture {
    @JsonIgnore
    Long lectureId;
    String lectureName;
    String detailLink;
    String city;
    String school;
    String address;
    String time;
}
