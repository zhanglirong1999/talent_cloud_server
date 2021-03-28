package seu.talents.cloud.talent.model.dao.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import seu.talents.cloud.talent.model.dao.entity.Banner;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository

public interface BannerMapper extends Mapper<Banner> {
    @Select("SELECT id,img from banner where deleted=0")
    List<Map<String,Object>> getBanner();
}
