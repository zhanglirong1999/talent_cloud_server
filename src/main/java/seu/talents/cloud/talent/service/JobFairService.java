package seu.talents.cloud.talent.service;

import seu.talents.cloud.talent.model.dao.entity.JobFair;
import seu.talents.cloud.talent.model.dto.post.JobFairSearchDTO;
import seu.talents.cloud.talent.model.dto.returnDTO.InformationWithTotalCountDTO;

import java.util.List;

public interface JobFairService {
    List<JobFair> getJobFairsByPage(Integer pageIndex, Integer pageSize);
    List<JobFair> searchJobFairs(JobFairSearchDTO jobFairSearchDTO);
    InformationWithTotalCountDTO getJobFairsWithTotalCountByPage(Integer pageIndex, Integer pageSize);
    void addJobFair(JobFair jobFair);
    void deleteById(Long jobFairId);
    void updateById(JobFair jobFair);
}
