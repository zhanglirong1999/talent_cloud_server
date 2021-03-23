package seu.talents.cloud.talent.model.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    Long postId;
    Integer postType;
    String postName;
    String detailLink;
    String companyName;
    String companyLink;
    String city;
    String industry;
    @Column(name = "time")
    String time;
    String skill;
    String salary;
    String education;
    String nature;
    String scale;
}
