package seu.talents.cloud.talent.model.dao.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import seu.talents.cloud.talent.model.dao.entity.Account;
import tk.mybatis.mapper.common.Mapper;
@Repository
public interface AccountMapper extends Mapper<Account> {
    @Select("SELECT name from account where accountId='${aid}'")
    String getName(String aid);

    @Update("UPDATE account set avatar='${avatar}',unionId='${unionId}' WHERE accountId='${aid}'")
    Integer updateAvatar(String avatar,String unionId,String aid);

    @Select("SELECT * FROM account WHERE name='${name}' and password='${password}'")
    Account getAdmin(String name,String password);
}
