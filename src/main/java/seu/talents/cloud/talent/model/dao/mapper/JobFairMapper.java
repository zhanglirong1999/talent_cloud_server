package seu.talents.cloud.talent.model.dao.mapper;

import org.springframework.stereotype.Repository;
import seu.talents.cloud.talent.model.dao.entity.JobFair;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface JobFairMapper extends Mapper<JobFair> {
    List<JobFair> getAll();
    List<JobFair> searchJobFair(String keyWord, String range, String type, String city, String time);
    void add(JobFair jobFair);
    void deleteById(Long jobFairId);
    void updateById(JobFair jobFair);
}
