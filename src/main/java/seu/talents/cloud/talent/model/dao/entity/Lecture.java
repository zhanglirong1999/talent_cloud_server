package seu.talents.cloud.talent.model.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lecture {
    Long lectureId;
    String lectureName;
    String detailLink;
    String city;
    String school;
    String address;
    @Column(name = "startTime")
    String startTime;
    @Column(name = "endTime")
    String endTime;
    String time;
}
