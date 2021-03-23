package seu.talents.cloud.talent.service;

import seu.talents.cloud.talent.model.dao.entity.Recruitment;
import seu.talents.cloud.talent.model.dto.post.RecruitmentSearchDTO;

import java.util.List;

public interface RecruitmentService {
    List<Recruitment> getRecruitmentByPage(Integer pageIndex, Integer pageSize);
    List<Recruitment> searchRecruitment(RecruitmentSearchDTO recruitmentSearchDTO);
    void addRecruitment(Recruitment recruitment);
    void deleteById(Long recruitmentId);
    void updateById(Recruitment recruitment);
}
