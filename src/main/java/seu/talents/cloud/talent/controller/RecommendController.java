package seu.talents.cloud.talent.controller;

import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seu.talents.cloud.talent.common.CONST;
import seu.talents.cloud.talent.common.annotation.TokenRequired;
import seu.talents.cloud.talent.common.annotation.WebResponse;
import seu.talents.cloud.talent.model.dto.post.RecommendDTO;
import seu.talents.cloud.talent.model.dto.post.RecommendModifyDTO;
import seu.talents.cloud.talent.service.RecommendService;
import seu.talents.cloud.talent.util.RedisUtil;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/recommend")
@RestController
@CrossOrigin(origins = "http://localhost:18081", maxAge = 7200)
@WebResponse
public class RecommendController {
    @Autowired
    private RecommendService recommendService;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RedisUtil redisUtil;
    /**
     * 发起内推
     * @param recommendDTO
     * @return
     */
    @TokenRequired
    @PostMapping("/add")
    public Object addRecommend(@RequestBody RecommendDTO recommendDTO){
        String accountId = (String) request.getAttribute(CONST.ACL_ACCOUNTID);
        recommendService.addRecommend(recommendDTO,accountId);
        return "ok";
    }

    /**
     * 删除内推
     * @param rid
     * @return
     */
    @TokenRequired
    @DeleteMapping("/delete")
    public Object deleteRecommend(@RequestParam Long rid){
        recommendService.deleteRecommend(rid);
        return "ok";
    }

    /**
     * 修改内推
     * @param recommendModifyDTO
     * @return
     */
    @TokenRequired
    @PostMapping("/modify")
    public Object modifyRecommend(@RequestBody RecommendModifyDTO recommendModifyDTO){
        recommendService.modifyRecommend(recommendModifyDTO);
        return "ok";
    }

    /**
     * 获取内推list
     * @return
     */
    @TokenRequired
    @GetMapping("/list")
    public Object getRecommend(@RequestParam Integer pageIndex,
                               @RequestParam Integer type){
        return recommendService.getRecommendList(pageIndex,type);
    }

    /**
     * 获取内推详情
     * @param rid
     * @return
     */
    @TokenRequired
    @GetMapping("/detail")
    public Object getDetail(@RequestParam Long rid){
        return recommendService.getRecommendDetail(rid);
    }

    /**
     * 搜索内推
     * @param keyword
     * @param pageIndex
     * @return
     */
    @TokenRequired
    @GetMapping("/search")
    public Object getRecommendSearch(@RequestParam String keyword,@RequestParam Integer pageIndex){
        return recommendService.getRecommendSearch(keyword, pageIndex);
    }


}
