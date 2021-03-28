package seu.talents.cloud.talent.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import seu.talents.cloud.talent.common.CONST;
import seu.talents.cloud.talent.common.annotation.TokenRequired;
import seu.talents.cloud.talent.common.annotation.WebResponse;
import seu.talents.cloud.talent.exception.BizException;
import seu.talents.cloud.talent.model.dao.entity.Friend;
import seu.talents.cloud.talent.model.dao.entity.Job;
import seu.talents.cloud.talent.model.dao.mapper.*;
import seu.talents.cloud.talent.model.dto.PageResult;
import seu.talents.cloud.talent.model.dto.post.AlumniCircleBasicInfoDTO;
import seu.talents.cloud.talent.model.dto.post.FriendStatus;
import seu.talents.cloud.talent.model.dto.post.MessageType;
import seu.talents.cloud.talent.model.dto.post.SearchType;
import seu.talents.cloud.talent.model.dto.returnDTO.JobDTO;
import seu.talents.cloud.talent.service.MessageService;
import seu.talents.cloud.talent.service.QCloudFileManager;
import seu.talents.cloud.talent.service.SecurityService;
import seu.talents.cloud.talent.service.SubscribeMessageService;
import seu.talents.cloud.talent.util.ConstantUtil;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebResponse
@RestController
@CrossOrigin
public class CommonController {

    @Autowired
    QCloudFileManager qCloudFileManager;

    @Autowired
    BannerMapper bannerMapper;

    @Autowired
    JobMapper jobMapper;

    @Autowired
    SecurityService securityService;


    @Autowired
    AlumniCircleMapper alumniCircleMapper;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private V2ApiMapper v2ApiMapper;

    @TokenRequired
    @PostMapping("/uploadFile")
    public Object uploadFile(
            @RequestParam(name = "file") MultipartFile multipartFile
    ) throws IOException {
        // 首先获取 newName
        String newNameWithoutType = String.valueOf(ConstantUtil.generateId());
        String newNameWithType = this.qCloudFileManager.buildNewFileNameWithType(
                multipartFile, newNameWithoutType
        );
        String ansUrl = null;
        try {
            ansUrl = qCloudFileManager.uploadOneFile(
                    multipartFile,
                    newNameWithoutType
            );
        } catch (IOException e) {
            return "上传文件失败";
        }
        // 要删除文件
        ConstantUtil.deleteFileUnderProjectDir(newNameWithType);
        // 返回最终结果
        return ansUrl;
    }

    @TokenRequired
    @GetMapping("/banner")
    public Object getBanner(){
        return bannerMapper.getBanner();
    }

    @TokenRequired
    @GetMapping("/job")
    public Object getJobExperience(@RequestParam Long jobId) {
        Job job = jobMapper.selectByPrimaryKey(jobId);
        return new JobDTO(job);
    }
    @TokenRequired
    @PostMapping("/job")
    public Object changeJobExperience(@RequestBody JobDTO jobDTO) {
        //检验用户创建的文字和图片有没有敏感内容
        boolean isLegal = securityService.checkoutJobDTOSecurity(jobDTO);
        if (!isLegal) {
            throw new BizException(ConstantUtil.BizExceptionCause.FAIL_PICTURE);
        }

        if (jobDTO.getJobId() != null &&
                !jobDTO.getJobId().equals("")) {
            jobMapper.updateByPrimaryKeySelective(jobDTO.toJob());
        } else {
            Job job = jobDTO.toJob();
            job.setJobId(ConstantUtil.generateId());
            jobMapper.insert(job);
        }
        return "success";
    }
    @TokenRequired
    @DeleteMapping("/job")
    public Object deleteJobExperience(@RequestBody JobDTO jobDTO) {
        Job job = jobDTO.toJob();
        job.setDeleted(1);
        jobMapper.updateByPrimaryKeySelective(job);
        return "success";
    }

    @TokenRequired
    @RequestMapping("/recommend")
    public Object recommend(@RequestParam int pageIndex,
                                 @RequestParam int pageSize) {
        String accountId = (String) request.getAttribute(CONST.ACL_ACCOUNTID);
        PageHelper.startPage(pageIndex, pageSize);
        List<AlumniCircleBasicInfoDTO> res = alumniCircleMapper.alumniCirclesRecommend(accountId);
//        res.forEach(alumniCircleBasicInfoDTO -> {
//            alumniCircleBasicInfoDTO.setIsJoined(
//                    alumniCircleMemberMapper.isJoined(alumniCircleBasicInfoDTO.getAlumniCircleId(), accountId)
//            );
//        });
        return new PageResult<AlumniCircleBasicInfoDTO>(((Page)res).getTotal(),res);

    }

    @TokenRequired
    @RequestMapping("/query")
    public Object query(@RequestParam String content,
                             @RequestParam int pageSize,
                             @RequestParam int pageIndex) {

            return v2ApiMapper.searchByName(content,pageIndex,pageSize);

    }

    @Autowired
    private FriendMapper friendMapper;

    @Autowired
    private MessageService messageService;

    @Autowired
    private SubscribeMessageService subscribeMessageService;

    @PostMapping("/friend/manage")
    @Transactional
    public Object friendAction(@RequestBody Map<String, String> req) {

        String A = req.get("A");
        String B = req.get("B");

        if (A == null || B == null) {
            throw new BizException(ConstantUtil.BizExceptionCause.FAIL_FRIEND);
        }


        if (Integer.valueOf(req.get("action")) == CONST.FRIEND_ACTION_Y) {
            Friend f = new Friend();
            f.setStatus(FriendStatus.friend.getStatus());

            // 更新两个人的好友关系
            Example e1 = new Example(Friend.class);
            e1.createCriteria()
                    .andEqualTo("accountId", req.get("B"))
                    .andEqualTo("friendAccountId", req.get("A"));
            friendMapper.updateByExampleSelective(f, e1);

            Example e2 = new Example(Friend.class);
            e2.createCriteria()
                    .andEqualTo("accountId", req.get("A"))
                    .andEqualTo("friendAccountId", req.get("B"));
            friendMapper.updateByExampleSelective(f, e2);

//            Message message = new Message();
//            message.setMessageId(Utils.generateId());
//            message.setFromUser(req.get("A"));
//            message.setToUser(req.get("B"));
//            message.setType(MessageType.AGREE.getValue());
//            messageMapper.insertSelective(message);

            messageService.newMessage(req.get("A"), req.get("B"),
                    MessageType.AGREE.getValue());
            //发送推送
            subscribeMessageService.sendSubscribeMessage(req.get("B"),req.get("A"),CONST.AGREE_MESSAGE);
        }

        if (Integer.valueOf(req.get("action")) == CONST.FRIEND_ACTION_N) {
            Friend f = new Friend();

            // B 查看A的名片，还是申请中状态
            f.setStatus(FriendStatus.apply.getStatus());
            Example e1 = new Example(Friend.class);
            e1.createCriteria()
                    .andEqualTo("accountId", req.get("B"))
                    .andEqualTo("friendAccountId", req.get("A"));
            friendMapper.updateByExampleSelective(f, e1);

            // A查看B的名片，正常的陌生人状态
            f.setStatus(FriendStatus.stranger.getStatus());
            Example e2 = new Example(Friend.class);
            e2.createCriteria()
                    .andEqualTo("accountId", req.get("A"))
                    .andEqualTo("friendAccountId", req.get("B"));
            friendMapper.updateByExampleSelective(f, e2);
            /**
             * 1、拒绝机制变更
             * 现象：A拒绝B的好友申请后，B会收到通知（消息模块上线的情况下）
             * 调整为：A拒绝B的好友申请后，B不会收到任何通知，且A的名片对于B一直保持“已申请”的状态。
             * 原因：不引导用户间发生负面反馈
             */
//            Message message = new Message();
//            message.setMessageId(Utils.generateId());
//            message.setFromUser(req.get("A"));
//            message.setToUser(req.get("B"));
//            message.setType(MessageType.REJECT.getValue());
//            messageMapper.insertSelective(message);
        }

        return "success";
    }


}
