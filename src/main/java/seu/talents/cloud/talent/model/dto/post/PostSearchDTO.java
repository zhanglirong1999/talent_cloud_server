package seu.talents.cloud.talent.model.dto.post;

import lombok.Data;

@Data
public class PostSearchDTO {
	Integer pageIndex;
   	Integer postType;
   	String keyWord;
   	String industry;
   	String skill;
   	String city;
   	String salary;
   	String education;
   	String time;
   	String nature;
   	String scale;
}
