package seu.talents.cloud.talent.model.dao.mapper;

import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import seu.talents.cloud.talent.model.dao.entity.Account;
import seu.talents.cloud.talent.model.dto.returnDTO.AccountDTO;
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
            "from talentCloud.account b inner join talentCloud.account a on b.gradYear = a.gradYear and a.accountId='${accountId}'\n" +
            "limit ${page},20")
    List<Map<String,Object>> getListByGradYear(String accountId,Integer page);

    @Select("select b.accountId,b.avatar,b.name,b.city,b.college as college,b.gradYear,b.gradDegree,b.company,b.job\n" +
            "from talentCloud.account b inner join talentCloud.account a on b.company = a.company and a.accountId='${accountId}'\n" +
            "limit ${page},20")
    List<Map<String,Object>> getListByCompany(String accountId,Integer page);

    @Select("select b.accountId,b.avatar,b.name,b.city,b.college as college,b.gradYear,b.gradDegree,b.company,b.job\n" +
            "from talentCloud.account b inner join talentCloud.account a on b.job = a.job and a.accountId='${accountId}'\n" +
            "limit ${page},20")
    List<Map<String,Object>> getListByJob(String accountId,Integer page);

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
            "from talentCloud.account b where isDaShi=1\n" +
            "limit ${page},20")
    List<Map<String,Object>> getListByDaShi(Integer page);


    @Select("select accountId,avatar,name,job,isDaShi from " +
            "account where company='${name}' limit 0,5")
    List<AccountDTO> getCompanyAccount(String name);

    @Select("select accountId,avatar,name,job,isDaShi from " +
            "account where company='${name}' limit ${pageIndex},20")
    List<AccountDTO> getCompanyAccountAll(String name,Integer pageIndex);

    @Select("select count(*) from talentCloud.account where company='${name}'")
    Integer getAccountCount(String name);

    @Update("update account set name='${name}'," +
            "gradYear='${time}',college='${college}' where accountId='${accountId}'")
    Integer updateAlumniInfo(String name,String time,String college,String accountId);




}
