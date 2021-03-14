package seu.talents.cloud.talent.model.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    String takeCount;
    String city;
    String passRate;
}
