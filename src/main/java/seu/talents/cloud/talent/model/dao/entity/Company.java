package seu.talents.cloud.talent.model.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Company {
    Long cid;
    String name;
    String logo;
    String info;
    String url;
    String heat;
    Integer count;
    String type;
    Integer deleted;
    @Column(name = "takeCount")
    String takeCount;
    String city;
    @Column(name = "passRate")
    String passRate;
    Integer alumni;
}
