package seu.talents.cloud.talent.model.dto.returnDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class InformationWithTotalCountDTO {
    Integer count;
    List<?> content;
}
