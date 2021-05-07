package seu.talents.cloud.talent.model.dto.post;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class BriefInfo {
    private String accountId;
    private String name;
    private String avatar;
    private String company;
    private String job;
    private String college;
    private String gradYear;
    private String startTime;
    private String school;

    private String recommondReason;

    public void setStartTime(final String startTime){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        this.startTime = dateFormat.format(new Date(Long.parseLong(startTime)));
    }
}
