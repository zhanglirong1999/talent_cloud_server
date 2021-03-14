package seu.talents.cloud.talent.service;

import org.springframework.validation.annotation.Validated;
import seu.talents.cloud.talent.model.dto.post.RecommendDTO;
import seu.talents.cloud.talent.model.dto.post.RecommendModifyDTO;

@Validated
public interface RecommendService {
    void addRecommend(RecommendDTO recommendDTO,String accountId);
    void deleteRecommend(Long rid);
    void modifyRecommend(RecommendModifyDTO recommendModifyDTO);
    Object getRecommendList(Integer pageIndex);
    Object getRecommendDetail(Long rid);
    Object getRecommendSearch(String keyword,Integer pageIndex);
}
