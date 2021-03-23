package seu.talents.cloud.talent.service;

import seu.talents.cloud.talent.model.dao.entity.Post;
import seu.talents.cloud.talent.model.dto.post.PostSearchDTO;

import java.util.List;

public interface PostService {
    List<Post> getPostInformationByPage(Integer pageIndex, Integer pageSize, Integer postType);
    List<Post> searchPost(PostSearchDTO postSearchDTO);
    void addPost(Post post);
    void deleteById(Long postId);
    void updateById(Post post);
}
