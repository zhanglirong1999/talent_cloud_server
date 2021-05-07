package seu.talents.cloud.talent.model.dao.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import seu.talents.cloud.talent.model.dto.post.BriefInfo;

import java.util.List;
import java.util.Map;

@Repository
public interface V2ApiMapper {
//    @Select("Select accountId,name,avatar,college,gradYear,gradDegree,company,job from account where " +
//            "CONCAT (`name`,`college`,`company`,`job`) like '%${content}%' limit ${pageIndex},20")
//    List<Map<String,Object>> searchByName(String content, Integer pageIndex);

    @Select(" select a.accountId,\n" +
            "               a.openid,\n" +
            "               a.avatar,\n" +
            "               a.name,\n" +
            "               a.city,\n" +
            "               a.college,\n" +
            "               a.company,\n" +
            "               a.job,\n" +
            "        a.gradYear,\n" +
            "        '东南大学' as school\n" +
            "        from\n" +
            "              account a\n" +
            "        where a.accountId in (\n" +
            "            select distinct d.accountId\n" +
            "            from account d\n" +
            "            where d.city like '%${content}%')")
    List<BriefInfo> searchByCity(String content);

    @Select(" select a.accountId,\n" +
            "               a.openid,\n" +
            "               a.avatar,\n" +
            "               a.name,\n" +
            "               a.city,\n" +
            "               a.college,\n" +
            "               a.company,\n" +
            "               a.job,\n" +
            "        a.gradYear,\n" +
            "        '东南大学' as school\n" +
            "        from\n" +
            "              account a\n" +
            "        where a.accountId in (\n" +
            "            select distinct d.accountId\n" +
            "            from account d\n" +
            "            where d.name like '%${content}%')")
    List<BriefInfo> searchByName(String content);

    @Select(" select a.accountId,\n" +
            "               a.openid,\n" +
            "               a.avatar,\n" +
            "               a.name,\n" +
            "               a.city,\n" +
            "               a.college,\n" +
            "               a.company,\n" +
            "               a.job,\n" +
            "        a.gradYear,\n" +
            "        '东南大学' as school\n" +
            "        from\n" +
            "              account a\n" +
            "        where a.accountId in (\n" +
            "            select distinct d.accountId\n" +
            "            from account d\n" +
            "            where d.college like '%${content}%')")
    List<BriefInfo> searchByCollege(String content);

    @Select(" select a.accountId,\n" +
            "               a.openid,\n" +
            "               a.avatar,\n" +
            "               a.name,\n" +
            "               a.city,\n" +
            "               a.college,\n" +
            "               a.company,\n" +
            "               a.job,\n" +
            "        a.gradYear,\n" +
            "        '东南大学' as school\n" +
            "        from\n" +
            "              account a\n" +
            "        where a.accountId in (\n" +
            "            select distinct d.accountId\n" +
            "            from account d\n" +
            "            where d.company like '%${content}%')")
    List<BriefInfo> searchByCompany(String content);

    @Select(" select a.accountId,\n" +
            "               a.openid,\n" +
            "               a.avatar,\n" +
            "               a.name,\n" +
            "               a.city,\n" +
            "               a.college,\n" +
            "               a.company,\n" +
            "               a.job,\n" +
            "        a.gradYear,\n" +
            "        '东南大学' as school\n" +
            "        from\n" +
            "              account a\n" +
            "        where a.accountId in (\n" +
            "            select distinct d.accountId\n" +
            "            from account d\n" +
            "            where d.job like '%${content}%')")
    List<BriefInfo> searchByPosition(String content);





}
