package seu.talents.cloud.talent.model.dto.post;

import lombok.Data;

@Data
public class RecruitmentSearchDTO {
    String keyWord;
    String range;
    String city;
    String time;
    Integer pageIndex;
}
