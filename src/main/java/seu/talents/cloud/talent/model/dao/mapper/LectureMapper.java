package seu.talents.cloud.talent.model.dao.mapper;

import org.springframework.stereotype.Repository;
import seu.talents.cloud.talent.model.dao.entity.Lecture;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface LectureMapper extends Mapper<Lecture> {
    List<Lecture> getAll();
    List<Lecture> searchLecture(String keyWord, String time);
    Integer getTotalCount();
    void add(Lecture lecture);
    void deleteById(Long lectureId);
    void updateById(Lecture lecture);
}
