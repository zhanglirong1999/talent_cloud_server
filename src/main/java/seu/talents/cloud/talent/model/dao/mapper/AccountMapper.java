package seu.talents.cloud.talent.model.dao.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import seu.talents.cloud.talent.model.dao.entity.Account;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface AccountMapper extends Mapper<Account> {
    @Select("SELECT name from account where accountId='${aid}'")
    String getName(String aid);

    @Select("SELECT gradYear from account where accountId='${aid}'")
    String getGradYear(String aid);

    @Select("SELECT college from account where accountId='${aid}'")
    String getcollege(String aid);

    @Select("SELECT job from account where accountId='${aid}'")
    String getJob(String aid);

    @Update("UPDATE account set avatar='${avatar}',unionId='${unionId}',gender=${gender},city='${city}' WHERE accountId='${aid}'")
    Integer updateAvatar(String avatar,String unionId,String aid,Integer gender,String city);

    @Select("SELECT * FROM account WHERE name='${name}' and password='${password}'")
    Account getAdmin(String name,String password);

    @Select("Select * from account where accountId='${aid}'")
    Account getAccount(String aid);

    @Select("select b.accountId,b.avatar,b.name,b.city,b.college as college,b.gradYear,b.gradDegree,b.company,b.job\n" +
            "from talentCloud.account b inner join talentCloud.account a on b.city = a.city and a.accountId='${accountId}'\n" +
            "limit ${page},20")
    List<Map<String,Object>> getListByCity(String accountId,Integer page);

    @Select("select b.accountId,b.avatar,b.name,b.city,b.college as college,b.gradYear,b.gradDegree,b.company,b.job\n" +
            "from talentCloud.account b inner join talentCloud.account a on b.college = a.college and a.accountId='${accountId}'\n" +
            "limit ${page},20")
    List<Map<String,Object>> getListByCollege(String accountId,Integer page);

    @Select("select b.accountId,b.avatar,b.name,b.city,b.college as college,b.gradYear,b.gradDegree,b.company,b.job\n" +
            "from talentCloud.account b inner join talentCloud.account a on (a.accountId = '${accountId}' and (b.company=a.company or b.job=a.job or b.gradYear=a.gradYear))\n" +
            "where b.accountId != '${accountId}'\n" +
            "limit ${page},20")
    List<Map<String,Object>> getListByMaybe(String accountId,Integer page);

    @Select("select b.accountId,b.avatar,b.name,b.city,b.college as college,b.gradYear,b.gradDegree,b.company,b.job\n" +
            "from talentCloud.account b where isDaShi=1'\n" +
            "limit ${page},20")
    List<Map<String,Object>> getListByDaShi(Integer page);



}
