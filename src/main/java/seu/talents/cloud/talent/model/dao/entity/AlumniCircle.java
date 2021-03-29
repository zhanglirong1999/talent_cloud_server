package seu.talents.cloud.talent.model.dao.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

public class AlumniCircle implements Serializable {

    @Id
    @Column(name = "alumniCircleId")
    private Long alumniCircleId;
    @Column(name = "creatorId")
    private Long creatorId;
    @Column(name = "alumniCircleType")
    private Integer alumniCircleType;
    @Column(name = "alumniCircleName")
    private String alumniCircleName;

    @Column(name = "alumniCircleDesc")
    private String alumniCircleDesc;

    @Column(name = "alumniCircleAnnouncement")
    private String alumniCircleAnnouncement;

    private String avatar;

    @Column(name = "authorizationStatus")
    private Boolean authorizationStatus;

    @Column(name = "cTime")
    private Date cTime;
    @Column(name = "uTime")
    private Date uTime;
    @Column(name = "isAvailable")
    private Boolean isAvailable;

    private static final long serialVersionUID = 1L;
}
