package seu.talents.cloud.talent.model.dao.entity;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

@Data
public class Favorite implements Serializable {
    @Column(name = "account_id")
    private String accountId;

    @Column(name = "favorite_account_id")
    private String favoriteAccountId;

    private Integer status;

    @Column(name = "c_time")
    private Date cTime;

    @Column(name = "u_time")
    private Date uTime;

    private static final long serialVersionUID = 1L;
}