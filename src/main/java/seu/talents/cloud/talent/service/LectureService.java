package seu.talents.cloud.talent.service;

import seu.talents.cloud.talent.model.dao.entity.Lecture;
import seu.talents.cloud.talent.model.dto.post.LectureSearchDTO;
import seu.talents.cloud.talent.model.dto.returnDTO.InformationWithTotalCountDTO;

import java.util.List;

public interface LectureService {
    List<Lecture> getLectureByPage(Integer pageIndex, Integer pageSize);
    List<Lecture> searchLecture(LectureSearchDTO lectureSearchDTO);
    InformationWithTotalCountDTO getLectureWithTotalCountByPage(Integer pageIndex, Integer pageSize);
    void addLecture(Lecture lecture);
    void deleteById(Long lectureId);
    void updateById(Lecture lecture);
}
