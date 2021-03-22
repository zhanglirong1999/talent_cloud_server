package seu.talents.cloud.talent.model.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @JsonIgnore
    Long postId;
    String postName;
    String detailLink;
    String companyName;
    String companyLink;
    String city;
    String industry;
    @Column(name = "time")
    String time;
    @JsonIgnore
    Integer deleted;
    @JsonIgnore
    String skill;
    @JsonIgnore
    String salary;
    @JsonIgnore
    String education;
    @JsonIgnore
    String nature;
    @JsonIgnore
    String scale;
}
