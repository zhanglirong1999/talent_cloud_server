package seu.talents.cloud.talent.model.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @Column(name = "accountId")
    private String accountId;
    @Column(name = "unionId")
    private String unionId;
    private String name;
    private String avatar;
    private String college;
    @Column(name = "gradYear")
    private String gradYear;
    private String phone;
    @Column(name = "openId")
    private String openId;
    @Column(name="gradDegree")
    private String gradDegree;
    private String company;
    private String job;
    @Column(name = "canRecom")
    private String canRecom;
    private String password;
    private Integer gender;
    private String city;
    @Column(name = "isDaShi")
    private Integer isDaShi;
    private String industry;
    @Column(name = "start_time")
    private String startTime;
    @Column(name = "login_time")
    private String loginTime;

}
