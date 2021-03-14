package seu.talents.cloud.talent.model.dao.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import seu.talents.cloud.talent.model.dao.entity.RecommendPicture;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface RecommendPictureMapper extends Mapper<RecommendPicture> {
    @Insert("INSERT INTO recommend_picture (rid,url) values (${rid},'${url}')")
    Integer insertIMG(Long rid,String url);

    @Select("SELECT url FROM recommend_picture WHERE rid=${rid}")
    List<String> getUrl(Long rid);

}
