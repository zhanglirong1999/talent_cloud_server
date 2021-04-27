package seu.talents.cloud.talent.model.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import seu.talents.cloud.talent.model.dao.entity.Company;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface CompanyMapper extends Mapper<Company> {
    @Update("update company set deleted=1 where cid=${cid}")
    Integer deleteCompany(@Param("cid")Long cid);

    @Select("select * from company where name like '%${index}%' and deleted=0 limit ${pageIndex},20")
    List<Company> getSearch(@Param("index")String index,@Param("pageIndex") Integer pageIndex);

    @Select("select count(*) from company where name like '%${index}%' and deleted=0")
    Integer getSearchCount(String index);

    @Select("select count(*) from company where deleted=0")
    Integer getCompanyCount1();

    @Select("select * from company where deleted=0 and alumni=#{alumni} order by heat+0 desc limit ${pageIndex},20")
    List<Company> getCompanyListByHeat(@Param("pageIndex") Integer pageIndex,@Param("alumni") Integer alumni);

    @Select("select * from company where deleted=0 alumni=#{alumni} order by count desc limit ${pageIndex},20")
    List<Company> getCompanyListByCount(@Param("pageIndex") Integer pageIndex,@Param("alumni") Integer alumni);

    @Select("select cid,name from company where deleted=0")
    List<Map<String,Object>> getNameList();

    @Select("select cid from company where name='${name}'")
    String getCompanyId(String name);

    @Select("select count(*) from company where name='${name}'")
    Integer getCompanyCount(String name);

    @Select("select * from company where name='${name}'")
    Company getCompany(String name);

    @Select("select count(*) from recommend where company='${name}'")
    Integer getRecommendCount(String name);
}
