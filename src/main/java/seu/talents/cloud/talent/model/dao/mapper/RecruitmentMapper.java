package seu.talents.cloud.talent.model.dao.mapper;

import org.springframework.stereotype.Repository;
import seu.talents.cloud.talent.model.dao.entity.Recruitment;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface RecruitmentMapper extends Mapper<Recruitment> {
    List<Recruitment> getAll();
    List<Recruitment> searchRecruitment(String keyWord, String range, String city, String time);
}
