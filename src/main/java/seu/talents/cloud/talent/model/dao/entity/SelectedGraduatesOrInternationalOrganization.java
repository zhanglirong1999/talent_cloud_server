package seu.talents.cloud.talent.model.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectedGraduatesOrInternationalOrganization {
    Long id;
    String name;
    String detailLink;
    @Column(name = "time")
    String time;
    Integer type;
}
