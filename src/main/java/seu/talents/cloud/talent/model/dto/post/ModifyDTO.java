package seu.talents.cloud.talent.model.dto.post;

import lombok.Data;

@Data
public class ModifyDTO extends Register{
    Integer gender;
    String city;
    String avatar;
}
