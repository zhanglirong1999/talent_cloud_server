package seu.talents.cloud.talent.model.dao.entity;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class AlumniCircleMember implements Serializable {
    @Column(name = "alumni_circle_id")
    private Long alumniCircleId;
    @Column(name = "account_id")
    private Long accountId;
    @Column(name = "valid_status", insertable = false)
    private Boolean validStatus;
    @Column(name = "push_status")
    private Boolean pushStatus;

    private static final long serialVersionUID = 1L;
}
