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
import seu.talents.cloud.talent.model.dao.entity.*;
import seu.talents.cloud.talent.model.dao.mapper.*;
import seu.talents.cloud.talent.model.dto.PageResult;
import seu.talents.cloud.talent.model.dto.post.*;
import seu.talents.cloud.talent.model.dto.returnDTO.JobDTO;
import seu.talents.cloud.talent.model.dto.returnDTO.SearchResultDTO;
import seu.talents.cloud.talent.service.*;
import seu.talents.cloud.talent.util.ConstantUtil;
import seu.talents.cloud.talent.util.SignUtil;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private FriendMapper friendMapper;

    @Autowired
    private MessageService messageService;

    @Autowired
    private SubscribeMessageService subscribeMessageService;

    @Autowired
    FavoriteMapper favoriteMapper;

    @Autowired
    private AccountService accountService;

    @TokenRequired
    @PostMapping("/uploadFile")
    public Object uploadFile(
            @RequestParam(name = "file") MultipartFile multipartFile
    ) throws IOException {
        // ???????????? newName
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
            return "??????????????????";
        }
        // ???????????????
        ConstantUtil.deleteFileUnderProjectDir(newNameWithType);
        // ??????????????????
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
        //?????????????????????????????????????????????????????????
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
    @RequestMapping("/alumni/recommend")
    public Object recommend( @RequestParam int filter,
                                @RequestParam int pageIndex
                               ) {
        String accountId = (String) request.getAttribute(CONST.ACL_ACCOUNTID);
        if(filter==0){
            return accountMapper.getListByCollege(accountId,pageIndex);
        }else if(filter==1){
            return accountMapper.getListByCity(accountId,pageIndex);
        }else if(filter==2){
            return accountMapper.getListByMaybe(accountId,pageIndex);
        }else if(filter==3){
            return accountMapper.getListByGradYear(accountId,pageIndex);
        }else if(filter==4){
            return accountMapper.getListByCompany(accountId,pageIndex);
        }else if(filter==5) {
            return accountMapper.getListByJob(accountId,pageIndex);
        }else  {
            return accountMapper.getListByDaShi(pageIndex);
        }

    }

//    @TokenRequired
//    @RequestMapping("/query")
//    public Object query(@RequestParam String keyword,
//
//                             @RequestParam int pageIndex) {
//            return v2ApiMapper.searchByName(keyword,pageIndex);
//    }

    @RequestMapping("/query")
    public Object query(@RequestParam String keyword,
                             @RequestParam String type,
                             @RequestParam int pageIndex) {
        List<SearchResultDTO> res = new ArrayList<SearchResultDTO>();
        int pageSize =20;
        if (type.equals("") || type == null || type.equals(SearchType.name.getValue())) {
            PageHelper.startPage(pageIndex, pageSize);
            List<BriefInfo> temp = v2ApiMapper.searchByName(keyword);
            res.add(new SearchResultDTO(
                    ((Page) temp).getTotal(),
                    SearchType.name,
                    temp));
        }

        if (type.equals("") || type == null || type.equals(SearchType.city.getValue())) {
            PageHelper.startPage(pageIndex, pageSize);
            List<BriefInfo> temp = v2ApiMapper.searchByCity(keyword);
            res.add(new SearchResultDTO(
                    ((Page) temp).getTotal(),
                    SearchType.city,
                    temp));
        }
        if (type.equals("") || type == null || type.equals(SearchType.company.getValue())) {
            PageHelper.startPage(pageIndex, pageSize);
            List<BriefInfo> temp = v2ApiMapper.searchByCompany(keyword);
            res.add(new SearchResultDTO(
                    ((Page) temp).getTotal(),
                    SearchType.company,
                    temp));
        }
        if (type.equals("") || type == null || type.equals(SearchType.position.getValue())) {
            PageHelper.startPage(pageIndex, pageSize);
            List<BriefInfo> temp = v2ApiMapper.searchByPosition(keyword);
            res.add(new SearchResultDTO(
                    ((Page) temp).getTotal(),
                    SearchType.position,
                    temp));
        }

        if (type.equals("") || type == null || type.equals(SearchType.college.getValue())) {
            PageHelper.startPage(pageIndex, pageSize);
            List<BriefInfo> temp = v2ApiMapper.searchByCollege(keyword);
            res.add(new SearchResultDTO(
                    ((Page) temp).getTotal(),
                    SearchType.college,
                    temp));
        }
        return res;
    }



    @TokenRequired
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

            // ??????????????????????????????
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
            //????????????
            subscribeMessageService.sendSubscribeMessage(req.get("B"),req.get("A"),CONST.AGREE_MESSAGE);
        }

        if (Integer.valueOf(req.get("action")) == CONST.FRIEND_ACTION_N) {
            Friend f = new Friend();

            // B ??????A?????????????????????????????????
            f.setStatus(FriendStatus.apply.getStatus());
            Example e1 = new Example(Friend.class);
            e1.createCriteria()
                    .andEqualTo("accountId", req.get("B"))
                    .andEqualTo("friendAccountId", req.get("A"));
            friendMapper.updateByExampleSelective(f, e1);

            // A??????B????????????????????????????????????
            f.setStatus(FriendStatus.stranger.getStatus());
            Example e2 = new Example(Friend.class);
            e2.createCriteria()
                    .andEqualTo("accountId", req.get("A"))
                    .andEqualTo("friendAccountId", req.get("B"));
            friendMapper.updateByExampleSelective(f, e2);
            /**
             * 1?????????????????????
             * ?????????A??????B?????????????????????B???????????????????????????????????????????????????
             * ????????????A??????B?????????????????????B??????????????????????????????A???????????????B???????????????????????????????????????
             * ?????????????????????????????????????????????
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

    @TokenRequired
    @PostMapping("/friend/apply")
    @Transactional
    public Object friendApply(@RequestBody FriendApplyDTO friendApplyDTO) {
        String accountId = (String) request.getAttribute(CONST.ACL_ACCOUNTID);
        Friend f = new Friend();
        f.setAccountId(accountId);
        f.setFriendAccountId(friendApplyDTO.getFriendAccountId());


        Friend fRes = friendMapper.selectOne(f);
        if (fRes != null && fRes.getStatus() == FriendStatus.apply.getStatus()) {
            throw new BizException(ConstantUtil.BizExceptionCause.TWICE_APPLY);
        }

        Friend A2B = new Friend();
        A2B.setAccountId(accountId);
        A2B.setFriendAccountId(friendApplyDTO.getFriendAccountId());
        A2B.setStatus(FriendStatus.apply.getStatus());

        friendMapper.insertOnDuplicateKeyUpdate(A2B.getAccountId(),A2B.getFriendAccountId(),A2B.getStatus());

        Friend B2A = new Friend();
        B2A.setAccountId(friendApplyDTO.getFriendAccountId());
        B2A.setFriendAccountId(accountId);
        B2A.setStatus(FriendStatus.todo.getStatus());
        friendMapper.insertOnDuplicateKeyUpdate(B2A.getAccountId(),B2A.getFriendAccountId(),B2A.getStatus());

        //?????????
        messageService.newMessage(accountId, friendApplyDTO.getFriendAccountId(),
                MessageType.APPLY.getValue(),"", friendApplyDTO.getMessage());

        //????????????
        subscribeMessageService.sendSubscribeMessage(friendApplyDTO.getFriendAccountId(),accountId,CONST.NEW_FRIEND_MESSAGE);
        return "success";
    }


   // @RegistrationRequired
    @TokenRequired
    @PostMapping("/favorite")
    public Object changeFavoriteStatus(@RequestBody Map req) {
        String accountId = (String) request.getAttribute(CONST.ACL_ACCOUNTID);
        String favoriteAccountId = (String) req.get("favoriteAccountId");
        Integer status = (Integer) req.get("status");

        if (status != 0 && status != 1) {
            throw new BizException(ConstantUtil.BizExceptionCause.FILE_STATUS);
        }
        Favorite favorite = new Favorite();
        favorite.setAccountId(accountId);
        favorite.setFavoriteAccountId(favoriteAccountId);
        if (favoriteMapper.select(favorite).size() == 0) {
            favorite.setStatus(status);
            favoriteMapper.insertSelective(favorite);
        } else {
            favorite.setStatus(status);
            Example example = new Example(Favorite.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("accountId", accountId);
            criteria.andEqualTo("favoriteAccountId", favoriteAccountId);

            favoriteMapper.updateByExampleSelective(favorite, example);
        }
        return "success";
    }

    @GetMapping("/test")
    public Object test(){
        String accountId = "3d008aa1-681e-4100-9aaa-9cce15ac2bec";
        String favoriteAccountId = "a71067ee-b9f0-4d18-a2d0-f322fe48b575";
        Example example = new Example(Favorite.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("accountId", accountId);
        criteria.andEqualTo("favoriteAccountId", favoriteAccountId);
        Favorite favorite = new Favorite();
        favorite.setAccountId(accountId);
        favorite.setFavoriteAccountId(favoriteAccountId);
        favorite.setStatus(1);
        favoriteMapper.updateByExampleSelective(favorite, example);
        return 1;
    }

    @TokenRequired
    @RequestMapping("/accountAll")
    public Object getAccountInfo(@RequestParam String accountId) {

        String myAccountId = (String) request.getAttribute(CONST.ACL_ACCOUNTID);

        AccountAllDTO accountAllDTO = accountService.getAccountAllDTOById(accountId);
        if (!myAccountId.equals(accountId)) {
            // ??????????????????
            Friend relationShip = friendMapper.getRelationShip(myAccountId, accountId);
            if (relationShip != null) {
                System.out.println("relationShip"+relationShip.getStatus());
                accountAllDTO.setRelationShip(relationShip.getStatus());
                if (relationShip.getStatus() != FriendStatus.friend.getStatus()) {
//                    accountAllDTO.getAccount().setBirthday(null);
//                    accountAllDTO.getAccount().setWechat(null);
                    accountAllDTO.getAccount().setPhone(null);
                }
            }
            // ??????????????????
            Favorite favorite = new Favorite();
            favorite.setAccountId(myAccountId);
            favorite.setFavoriteAccountId(accountId);
            List<Favorite> temp = favoriteMapper.select(favorite);
            if (temp.size() > 0) {
                accountAllDTO.setFavorite(temp.get(0).getStatus());
            }
        }

        return accountAllDTO;
    }
    /**
     * ??????banner
     */
    @TokenRequired
    @PostMapping("/banner")
    public Object addBanner(@RequestBody BannerDTO bannerDTO){
        Banner banner = new Banner();
        banner.setImg(bannerDTO.getImg());
        banner.setLink(bannerDTO.getLink());
        banner.setDeleted(0);
        bannerMapper.insert(banner);
        return "success";
    }

    @TokenRequired
    @PostMapping("/banner/stop")
    public Object stopBanner(@RequestParam Integer id){
        if(bannerMapper.banner(id)==null){
            throw new BizException(ConstantUtil.BizExceptionCause.NO_BANNER);
        }
        bannerMapper.stopBanner(id);
        return "success";
    }

    @TokenRequired
    @GetMapping("/favorite")
    public Object getFavorite(@RequestParam int pageIndex,
                              @RequestParam int filter
    ) {
        String accountId = (String) request.getAttribute(CONST.ACL_ACCOUNTID);
        if(filter==1) {
            Favorite favorite = new Favorite();
            favorite.setAccountId(accountId);
            favorite.setStatus(1);
            PageHelper.startPage(pageIndex, 20);
            List<FavoriteDTO> res = favoriteMapper.getFavoriteList(accountId, pageIndex);
            return new PageResult<FavoriteDTO>(favoriteMapper.selectCount(favorite), res);
        }else if(filter==0){
            PageHelper.startPage(pageIndex, 20);
            List<FriendDTO> friends = friendMapper.getFriends(accountId);
            return new PageResult(((Page) friends).getTotal(), friends);
        }else{
            throw new BizException(ConstantUtil.BizExceptionCause.ERROR_FILTER);
        }
    }


    /**
     * ????????????
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public void get(HttpServletRequest request, HttpServletResponse response) throws Exception{

        // ?????????????????????signature???????????????????????????token?????????????????????timestamp?????????nonce?????????
        String signature = request.getParameter("signature");
        // ?????????
        String timestamp = request.getParameter("timestamp");
        // ?????????
        String nonce = request.getParameter("nonce");
        // ???????????????
        String echostr = request.getParameter("echostr");


        PrintWriter out = null;
        try {
            out = response.getWriter();
            // ????????????signature??????????????????????????????????????????????????????echostr?????????????????????
            if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                out.print(echostr);
                out.flush();   //????????????????????????????????????????????????Token?????????????????????????????????????????????????????????????????????????????????
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
            out = null;

        }
    }



}
