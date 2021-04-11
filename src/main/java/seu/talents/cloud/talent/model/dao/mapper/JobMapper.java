package seu.talents.cloud.talent.model.dao.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import seu.talents.cloud.talent.model.dao.entity.Job;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface JobMapper extends Mapper<Job> {
    @Select("select * from job where jobId=${id}")
    Job getJob(Long id);
}
