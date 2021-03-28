package seu.talents.cloud.talent.model.dao.mapper;

import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;
import seu.talents.cloud.talent.model.dao.entity.Friend;
import tk.mybatis.mapper.common.Mapper;
@Repository
public interface FriendMapper extends Mapper<Friend> {
    @Insert("insert into friend\n" +
            "            (account_id, friend_account_id, status)\n" +
            "        values ('#{accountId}', '#{friendAccountId}', #{status})\n" +
            "        ON DUPLICATE KEY UPDATE status= #{status}")
    int insertOnDuplicateKeyUpdate(String accountId,String friendAccountId,Integer status);
}
