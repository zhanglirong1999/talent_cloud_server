package seu.talents.cloud.talent.service;

import seu.talents.cloud.talent.model.dao.entity.Post;
import seu.talents.cloud.talent.model.dto.post.PostSearchDTO;
import seu.talents.cloud.talent.model.dto.returnDTO.InformationWithTotalCountDTO;

import java.util.List;

public interface PostService {
    List<Post> getPostInformationByPage(Integer pageIndex, Integer pageSize, Integer postType);
    List<Post> searchPost(PostSearchDTO postSearchDTO);
    InformationWithTotalCountDTO getPostInformationWithTotalCountByPage(Integer pageIndex, Integer pageSize, Integer postType);
    void addPost(Post post);
    void deleteById(Long postId);
    void updateById(Post post);
}
