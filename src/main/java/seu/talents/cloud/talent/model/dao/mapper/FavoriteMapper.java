package seu.talents.cloud.talent.model.dao.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import seu.talents.cloud.talent.model.dao.entity.Favorite;
import seu.talents.cloud.talent.model.dto.post.FavoriteDTO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface FavoriteMapper extends Mapper<Favorite> {
    @Select(" select a.account_id  as accountId,\n" +
            "               a.favorite_account_id as favoriteAccountId,\n" +
            "               a.status,\n" +
            "               b.avatar,\n" +
            "               b.name,\n" +
            "               b.city,\n" +
            "               b.collage,\n" +
            "               t2.company,\n" +
            "               t2.position\n" +
            "        from favorite a\n" +
            "                 left join account b on a.favorite_account_id = b.accountId\n" +
            "                 left join (select max(j.accountId) as account_id_t1, company, position\n" +
            "                            from job j\n" +
            "                            group by j.accountId) as t2 on a.favorite_account_id = t2.account_id_t1\n" +
            "        where a.account_id = '#{accountId}'\n" +
            "          and a.status = 1")
    List<FavoriteDTO> getFavoriteList(String accountId, Integer pageIndex);
}
