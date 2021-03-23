package seu.talents.cloud.talent.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seu.talents.cloud.talent.common.CONST;
import seu.talents.cloud.talent.model.dao.entity.Post;
import seu.talents.cloud.talent.model.dao.mapper.PostMapper;
import seu.talents.cloud.talent.model.dto.post.PostSearchDTO;
import seu.talents.cloud.talent.service.PostService;

import java.util.List;

@Service("databasePostService")
public class DatabasePostServiceImpl implements PostService {

    private final PostMapper postMapper;

    @Autowired
    public DatabasePostServiceImpl(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    @Override
    public List<Post> getPostInformationByPage(Integer pageIndex, Integer pageSize, Integer postType) {
        PageHelper.startPage(pageIndex, pageSize);
        return postMapper.getAll(postType);
    }

    @Override
    public List<Post> searchPost(PostSearchDTO postSearchDTO) {
        PageHelper.startPage(postSearchDTO.getPageIndex(), CONST.PAGE_SIZE);
        return postMapper.searchPost(
                postSearchDTO.getPostType(),
                postSearchDTO.getKeyWord(),
                postSearchDTO.getIndustry(),
                postSearchDTO.getSkill(),
                postSearchDTO.getCity(),
                postSearchDTO.getSalary(),
                postSearchDTO.getEducation(),
                postSearchDTO.getTime(),
                postSearchDTO.getNature(),
                postSearchDTO.getScale()
        );
    }

    @Override
    public void addPost(Post post) {
        postMapper.add(post);
    }

    @Override
    public void deleteById(Long postId) {
        postMapper.deleteById(postId);
    }

    @Override
    public void updateById(Post post) {
        postMapper.updateById(post);
    }
}
