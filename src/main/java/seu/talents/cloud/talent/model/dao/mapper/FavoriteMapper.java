package seu.talents.cloud.talent.model.dao.mapper;

import org.springframework.stereotype.Repository;
import seu.talents.cloud.talent.model.dao.entity.Favorite;
import seu.talents.cloud.talent.model.dto.post.FavoriteDTO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface FavoriteMapper extends Mapper<Favorite> {
    List<FavoriteDTO> getFavoriteList(Long accountId, Integer pageIndex, Integer pageSize);
}
