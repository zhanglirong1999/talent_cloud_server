package seu.talents.cloud.talent.model.dao.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import seu.talents.cloud.talent.model.dto.post.BriefInfo;

import java.util.List;
import java.util.Map;

@Repository
public interface V2ApiMapper {
    @Select("Select accountId,name,avatar,collage,gradYear,gradDegree,company,job from account where " +
            "CONCAT (`name`,`collage`,`company`,`job`) like '%${content}%' limit ${pageIndex},${pageSize}")
    List<Map<String,Object>> searchByName(String content, Integer pageIndex,Integer pageSize);



}
