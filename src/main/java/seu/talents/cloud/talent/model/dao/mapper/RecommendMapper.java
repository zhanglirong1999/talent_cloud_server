package seu.talents.cloud.talent.model.dao.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import seu.talents.cloud.talent.model.dao.entity.Recommend;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface RecommendMapper extends Mapper<Recommend> {
    @Delete("DELETE FROM recommend WHERE rid=${rid}")
    Integer deleteRecommend(Long rid);

    @Select("SELECT * FROM recommend ORDER BY createTime DESC LIMIT ${pageIndex},20")
    List<Recommend> getRecommendList(Integer pageIndex);

    @Select("SELECT * FROM recommend WHERE rid=${rid}")
    Recommend getRecommend(Long rid);

    @Select("SELECT * FROM recommend WHERE name like '%${keyword}%' ORDER BY createTime DESC LIMIT ${pageIndex},20")
    List<Recommend> getRecommendSearch(String keyword,Integer pageIndex);

}
