package seu.talents.cloud.talent.model.dao.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import seu.talents.cloud.talent.model.dao.entity.Account;
import tk.mybatis.mapper.common.Mapper;
@Repository
public interface AccountMapper extends Mapper<Account> {
    @Select("SELECT name from account where accountId='${aid}'")
    String getName(String aid);
}
