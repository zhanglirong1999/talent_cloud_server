package seu.talents.cloud.talent.service.impl;

import com.github.pagehelper.PageHelper;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seu.talents.cloud.talent.common.CONST;
import seu.talents.cloud.talent.model.dao.entity.JobFair;
import seu.talents.cloud.talent.model.dao.mapper.JobFairMapper;
import seu.talents.cloud.talent.model.dto.post.JobFairSearchDTO;
import seu.talents.cloud.talent.service.JobFairService;

import java.util.List;

@Service("databaseJobFairService")
public class DatabaseJobFairServiceImpl implements JobFairService {

    private final JobFairMapper jobFairMapper;

    @Autowired
    public DatabaseJobFairServiceImpl(JobFairMapper jobFairMapper) {
        this.jobFairMapper = jobFairMapper;
    }

    @Override
    public List<JobFair> getJobFairsByPage(Integer pageIndex, Integer pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        return jobFairMapper.getAll();
    }

    @Override
    public List<JobFair> searchJobFairs(JobFairSearchDTO jobFairSearchDTO) {
        PageHelper.startPage(jobFairSearchDTO.getPageIndex(), CONST.PAGE_SIZE);
        return jobFairMapper.searchJobFair(
                jobFairSearchDTO.getKeyWord(),
                jobFairSearchDTO.getRange(),
                jobFairSearchDTO.getType(),
                jobFairSearchDTO.getCity(),
                jobFairSearchDTO.getTime()
        );
    }

    @Override
    public void addJobFair(JobFair jobFair) {
        jobFairMapper.add(jobFair);
    }

    @Override
    public void deleteById(Long jobFairId) {
        jobFairMapper.deleteById(jobFairId);
    }

    @Override
    public void updateById(JobFair jobFair) {
        jobFairMapper.updateById(jobFair);
    }
}
