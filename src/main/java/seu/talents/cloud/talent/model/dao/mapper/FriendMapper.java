package seu.talents.cloud.talent.model.dao.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import seu.talents.cloud.talent.model.dao.entity.Friend;
import seu.talents.cloud.talent.model.dto.post.FriendDTO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface FriendMapper extends Mapper<Friend> {
    @Insert("insert into friend\n" +
            "            (account_id, friend_account_id, status)\n" +
            "        values (#{accountId}, #{friendAccountId}, #{status})\n" +
            "        ON DUPLICATE KEY UPDATE status= #{status}")
    int insertOnDuplicateKeyUpdate(String accountId,String friendAccountId,Integer status);

    @Select("select a.account_id as accountId, a.friend_account_id, a.status, a.valid_status\n" +
            "        from friend a\n" +
            "        where a.account_id = #{myAccountId}\n" +
            "          and a.friend_account_id = #{accountId}")
    Friend getRelationShip(String myAccountId,String accountId);

    @Select("select b.accountId as friendAccountId,\n" +
            "               b.name,\n" +
            "               c.company,\n" +
            "               c.job,\n" +
            "               b.avatar,\n" +
            "               b.city,\n" +
            "               a.status,\n" +
            "               b.college\n" +
            "        from friend a\n" +
            "                 left join account b on a.friend_account_id = b.accountId\n" +
            "                 left join (select max(j.accountId) as account_id_t, company, job\n" +
            "                            from job j\n" +
            "                            group by j.accountId) c on b.accountId = c.account_id_t\n" +
            "\n" +
            "        where a.account_id = #{accountId}\n" +
            "          and a.status = 2\n" +
            "        UNION\n" +
            "        DISTINCT\n" +
            "        select b.accountId as friendAccountId,\n" +
            "               b.name,\n" +
            "               c.company,\n" +
            "               c.job,\n" +
            "               b.avatar,\n" +
            "               b.city,\n" +
            "               (CASE a.status\n" +
            "                    WHEN 0 THEN 0\n" +
            "                    WHEN 1 THEN 3\n" +
            "                    WHEN 2 THEN 2\n" +
            "                   END)     as status,\n" +
            "               b.college\n" +
            "        from friend a\n" +
            "                 left join account b on a.friend_account_id = b.accountId\n" +
            "                 left join (select max(j.accountId) as account_id_t, company, job\n" +
            "                            from job j\n" +
            "                            group by j.accountId) c on b.accountId = c.account_id_t\n" +
            "        where a.friend_account_id = #{accountId}\n" +
            "          and a.status = 2")
    List<FriendDTO> getFriends(String accountId);
}
