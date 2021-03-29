package seu.talents.cloud.talent.model.dao.mapper;

import org.springframework.stereotype.Repository;
import seu.talents.cloud.talent.model.dao.entity.Job;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface JobMapper extends Mapper<Job> {
}
