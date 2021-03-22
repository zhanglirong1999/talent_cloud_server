package seu.talents.cloud.talent.model.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDTO {
    String code;
    String rawData;
    String encryptedData;
    String signature;
    String iv;
}
