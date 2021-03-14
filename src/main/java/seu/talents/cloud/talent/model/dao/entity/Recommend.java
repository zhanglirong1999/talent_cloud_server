package seu.talents.cloud.talent.model.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recommend {
    Long rid;
    String name;
    String company;
    String department;
    String degree;
    String city;
    String aid;
    String tag;
    String salarymin;
    String salarymax;
    String info;
    String method;
    @Column(name = "createTime")
    String createTime;
}
