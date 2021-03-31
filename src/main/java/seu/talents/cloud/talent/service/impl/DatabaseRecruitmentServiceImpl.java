package seu.talents.cloud.talent.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seu.talents.cloud.talent.common.CONST;
import seu.talents.cloud.talent.model.dao.entity.Recruitment;
import seu.talents.cloud.talent.model.dao.mapper.RecruitmentMapper;
import seu.talents.cloud.talent.model.dto.post.RecruitmentSearchDTO;
import seu.talents.cloud.talent.model.dto.returnDTO.InformationWithTotalCountDTO;
import seu.talents.cloud.talent.service.RecruitmentService;

import java.util.List;

@Service("databaseRecruitmentService")
public class DatabaseRecruitmentServiceImpl implements RecruitmentService {
    private final RecruitmentMapper recruitmentMapper;

    @Autowired
    public DatabaseRecruitmentServiceImpl(RecruitmentMapper recruitmentMapper) {
        this.recruitmentMapper = recruitmentMapper;
    }


    @Override
    public List<Recruitment> getRecruitmentByPage(Integer pageIndex, Integer pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        return recruitmentMapper.getAll();
    }

    @Override
    public List<Recruitment> searchRecruitment(RecruitmentSearchDTO recruitmentSearchDTO) {
        PageHelper.startPage(recruitmentSearchDTO.getPageIndex(), CONST.PAGE_SIZE);
        return recruitmentMapper.searchRecruitment(
                recruitmentSearchDTO.getKeyWord(),
                recruitmentSearchDTO.getRange(),
                recruitmentSearchDTO.getCity(),
                recruitmentSearchDTO.getTime()
        );
    }

    @Override
    @Transactional
    public InformationWithTotalCountDTO getRecruitmentWithTotalCountByPage(Integer pageIndex, Integer pageSize) {
        Integer count = recruitmentMapper.getTotalCount();
        PageHelper.startPage(pageIndex, pageSize);
        List<Recruitment> res = recruitmentMapper.getAll();
        return new InformationWithTotalCountDTO(count, res);
    }

    @Override
    public void addRecruitment(Recruitment recruitment) {
        recruitmentMapper.add(recruitment);
    }

    @Override
    public void deleteById(Long recruitmentId) {
        recruitmentMapper.deleteById(recruitmentId);
    }

    @Override
    public void updateById(Recruitment recruitment) {
        recruitmentMapper.updateById(recruitment);
    }
}
