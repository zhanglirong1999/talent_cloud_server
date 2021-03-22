package seu.talents.cloud.talent.model.dto.post;

import lombok.Data;

@Data
public class JobFairSearchDTO {
    String keyWord;
    String range;
    String type;
    String city;
    String time;
    Integer pageIndex;
}
