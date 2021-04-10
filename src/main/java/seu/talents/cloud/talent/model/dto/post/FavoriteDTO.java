package seu.talents.cloud.talent.model.dto.post;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteDTO {
    private String accountId;
    private String favoriteAccountId;
    private Integer status;
    private String avatar;
    private String name;
    private String city;
    private String collage;
    private String startTime;
    private String company;
    private String position;

    public void setStartTime(final String startTime){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        this.startTime = dateFormat.format(new Date(Long.parseLong(startTime)));
    }

}
