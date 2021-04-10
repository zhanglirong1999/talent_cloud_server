package seu.talents.cloud.talent.model.dto.post;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

@Data
public class PhoneDTO {
    String encryptedData;
    String iv;
    String js_code;
}
