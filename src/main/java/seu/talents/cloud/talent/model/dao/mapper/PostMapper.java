package seu.talents.cloud.talent.model.dao.mapper;

import org.springframework.stereotype.Repository;
import seu.talents.cloud.talent.model.dao.entity.Post;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface PostMapper extends Mapper<Post> {
    List<Post> getAll(Integer postType);
    List<Post> searchPost(
            Integer postType,
            String keyWord,
            String industry,
            String skill,
            String city,
            String salary,
            String education,
            String time,
            String nature,
            String scale
    );
    Integer getTotalCount(Integer postType);
    void add(Post post);
    void deleteById(Long postId);
    void updateById(Post post);
}
