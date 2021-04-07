package seu.talents.cloud.talent.model.dto.post;

import lombok.Data;
import seu.talents.cloud.talent.model.dao.entity.Account;
import seu.talents.cloud.talent.model.dto.returnDTO.JobDTO;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class AccountAllDTO {
    @NotNull
    private Account account;

    @NotNull
    private List<JobDTO> jobs;

    private List<FriendDTO> friends;

    /**
     * 供前台展示两人关系用
     * 本人 null
     * 其他状态 FriendStatus
     */
    private Integer relationShip;
    /**
     *
     */
    private Integer favorite;

}
