package seu.talents.cloud.talent.model.dao.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import seu.talents.cloud.talent.model.dao.entity.AlumniCircle;
import seu.talents.cloud.talent.model.dto.post.AlumniCircleBasicInfoDTO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface AlumniCircleMapper extends Mapper<AlumniCircle> {

    @Select("SELECT ALC.*\n" +
            "        FROM alumni_circle ALC\n" +
            "        where  ALC.alumniCircleId in (\n" +
            "            select alumniCircleId\n" +
            "            from alumni_circle_member\n" +
            "            where account_id = '${accountId}'")
    public List<AlumniCircleBasicInfoDTO> alumniCirclesRecommend(String accountId);

}
