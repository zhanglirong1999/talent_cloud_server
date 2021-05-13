package seu.talents.cloud.talent.model.dao.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import seu.talents.cloud.talent.model.dao.entity.Graduation;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface GraduationMapper extends Mapper<Graduation> {
    @Select("select *\n" +
            "from graduation where name='${name}' AND identify='${identify}' limit 1")
    Graduation getAccountByName(String name,String identify);

    @Select("select *\n" +
            "from graduation where name='${name}' OR identify='${identify} limit 1")
    Graduation getAccountByName2(String name,String identify);


}
