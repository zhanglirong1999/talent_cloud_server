package seu.talents.cloud.talent.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seu.talents.cloud.talent.common.CONST;
import seu.talents.cloud.talent.model.dao.entity.Lecture;
import seu.talents.cloud.talent.model.dao.mapper.LectureMapper;
import seu.talents.cloud.talent.model.dto.post.LectureSearchDTO;
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
}
