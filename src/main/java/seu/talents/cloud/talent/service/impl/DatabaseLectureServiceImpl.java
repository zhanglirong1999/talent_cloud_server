package seu.talents.cloud.talent.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seu.talents.cloud.talent.common.CONST;
import seu.talents.cloud.talent.model.dao.entity.Lecture;
import seu.talents.cloud.talent.model.dao.mapper.LectureMapper;
import seu.talents.cloud.talent.model.dto.post.LectureSearchDTO;
import seu.talents.cloud.talent.model.dto.returnDTO.InformationWithTotalCountDTO;
import seu.talents.cloud.talent.service.LectureService;

import java.util.List;

@Service("databaseLectureService")
public class DatabaseLectureServiceImpl implements LectureService {

    private final LectureMapper lectureMapper;

    @Autowired
    public DatabaseLectureServiceImpl(LectureMapper lectureMapper) {
        this.lectureMapper = lectureMapper;
    }

    @Override
    public List<Lecture> getLectureByPage(Integer pageIndex, Integer pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        return lectureMapper.getAll();
    }

    @Override
    public List<Lecture> searchLecture(LectureSearchDTO lectureSearchDTO) {
        PageHelper.startPage(lectureSearchDTO.getPageIndex(), CONST.PAGE_SIZE);
        return lectureMapper.searchLecture(lectureSearchDTO.getKeyWord(), lectureSearchDTO.getTime());
    }

    @Override
    @Transactional
    public InformationWithTotalCountDTO getLectureWithTotalCountByPage(Integer pageIndex, Integer pageSize) {
        Integer count = lectureMapper.getTotalCount();
        PageHelper.startPage(pageIndex, pageSize);
        List<Lecture> res = lectureMapper.getAll();
        return new InformationWithTotalCountDTO(count, res);
    }

    @Override
    public void addLecture(Lecture lecture) {
        lectureMapper.add(lecture);
    }

    @Override
    public void deleteById(Long lectureId) {
        lectureMapper.deleteById(lectureId);
    }

    @Override
    public void updateById(Lecture lecture) {
        lectureMapper.updateById(lecture);
    }
}
