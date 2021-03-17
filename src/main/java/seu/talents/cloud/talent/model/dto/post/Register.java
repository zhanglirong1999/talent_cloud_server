package seu.talents.cloud.talent.model.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Register {
    String name;
    String phone;
    String college;
    String gradYear;
    String gradDegree;
    String company;
    String job;
    String canRecom;
}
