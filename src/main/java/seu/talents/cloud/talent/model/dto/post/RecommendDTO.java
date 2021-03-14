package seu.talents.cloud.talent.model.dto.post;

import lombok.Data;

import java.util.List;

@Data
public class RecommendDTO {
    String name;
    String company;
    String department;
    String degree;
    String city;
    String tag;
    String salarymin;
    String salarymax;
    String info;
    String method;
    List<String> img;
}
