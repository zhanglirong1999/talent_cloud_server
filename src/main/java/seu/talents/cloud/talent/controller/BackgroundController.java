package seu.talents.cloud.talent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import seu.talents.cloud.talent.common.annotation.WebResponse;
import seu.talents.cloud.talent.model.dao.entity.*;
import seu.talents.cloud.talent.service.*;
import seu.talents.cloud.talent.util.ConstantUtil;

@RestController
@WebResponse
@CrossOrigin
@RequestMapping("/background")
public class BackgroundController {

    private final PostService databasePostService;
    private final JobFairService databaseJobFairService;
    private final LectureService databaseLectureService;
    private final RecruitmentService databaseRecruitmentService;
    private final SelectedGraduatesAndInternationalOrganizationService databaseSelectedGraduatesAndInternationalOrganizationService;


    @Autowired
    public BackgroundController(
            @Qualifier("databasePostService") PostService databasePostService,
            @Qualifier("databaseJobFairService") JobFairService databaseJobFairService,
            @Qualifier("databaseLectureService") LectureService databaseLectureService,
            @Qualifier("databaseRecruitmentService") RecruitmentService databaseRecruitmentService,
            @Qualifier("databaseSelectedGraduatesAndInternationalOrganizationService") SelectedGraduatesAndInternationalOrganizationService databaseSelectedGraduatesAndInternationalOrganizationService
    ) {
        this.databaseJobFairService = databaseJobFairService;
        this.databasePostService = databasePostService;
        this.databaseLectureService = databaseLectureService;
        this.databaseRecruitmentService = databaseRecruitmentService;
        this.databaseSelectedGraduatesAndInternationalOrganizationService = databaseSelectedGraduatesAndInternationalOrganizationService;
    }

    @GetMapping("/posts")
    public Object getPosts(@RequestParam Integer pageIndex, @RequestParam Integer pageSize, @RequestParam Integer postType) {
        return databasePostService.getPostInformationByPage(pageIndex, pageSize, postType);
    }

    @GetMapping("/job-fairs")
    public Object getJobFairs(@RequestParam Integer pageIndex, @RequestParam Integer pageSize) {
        return databaseJobFairService.getJobFairsByPage(pageIndex, pageSize);
    }

    @GetMapping("/lectures")
    public Object getLectures(@RequestParam Integer pageIndex, @RequestParam Integer pageSize) {
        return databaseLectureService.getLectureByPage(pageIndex, pageSize);
    }

    @GetMapping("/recruitments")
    public Object getRecruitments(@RequestParam Integer pageIndex, @RequestParam Integer pageSize) {
        return databaseRecruitmentService.getRecruitmentByPage(pageIndex, pageSize);
    }

    @GetMapping("/selected-graduates")
    public Object getSelectedGraduates(@RequestParam Integer pageIndex, @RequestParam Integer pageSize) {
        return databaseSelectedGraduatesAndInternationalOrganizationService.getByPage(pageIndex, pageSize, 0);
    }

    @GetMapping("/international-organizations")
    public Object getInternationalOrganizations(@RequestParam Integer pageIndex, @RequestParam Integer pageSize) {
        return databaseSelectedGraduatesAndInternationalOrganizationService.getByPage(pageIndex, pageSize, 1);
    }

    @PostMapping("/posts")
    public Object addPost(@RequestBody Post post) {
        post.setPostId(ConstantUtil.generateId());
        databasePostService.addPost(post);
        return "职位添加成功!";
    }

    @PostMapping("/job-fairs")
    public Object addJobFair(@RequestBody JobFair jobFair) {
        jobFair.setJobFairId(ConstantUtil.generateId());
        databaseJobFairService.addJobFair(jobFair);
        return "招聘会添加成功!";
    }

    @PostMapping("/lectures")
    public Object addLecture(@RequestBody Lecture lecture) {
        lecture.setLectureId(ConstantUtil.generateId());
        databaseLectureService.addLecture(lecture);
        return "宣讲会添加成功!";
    }

    @PostMapping("/recruitments")
    public Object addRecruitment(@RequestBody Recruitment recruitment) {
        recruitment.setRecruitmentId(ConstantUtil.generateId());
        databaseRecruitmentService.addRecruitment(recruitment);
        return "招聘公告添加成功!";
    }

    @PostMapping("/selected-graduates")
    public Object addSelectedGraduates(@RequestBody SelectedGraduatesOrInternationalOrganization selectedGraduatesOrInternationalOrganization) {
        selectedGraduatesOrInternationalOrganization.setId(ConstantUtil.generateId());
        selectedGraduatesOrInternationalOrganization.setType(0);
        databaseSelectedGraduatesAndInternationalOrganizationService.add(selectedGraduatesOrInternationalOrganization);
        return "基层选调信息添加成功!";
    }

    @PostMapping("/international-organizations")
    public Object addInternationalOrganization(@RequestBody SelectedGraduatesOrInternationalOrganization selectedGraduatesOrInternationalOrganization) {
        selectedGraduatesOrInternationalOrganization.setId(ConstantUtil.generateId());
        selectedGraduatesOrInternationalOrganization.setType(1);
        databaseSelectedGraduatesAndInternationalOrganizationService.add(selectedGraduatesOrInternationalOrganization);
        return "国际组织信息添加成功!";
    }

    @DeleteMapping("/posts")
    public Object deletePostById(@RequestParam Long postId) {
        databasePostService.deleteById(postId);
        return "删除职位成功! ID: " + postId;
    }

    @DeleteMapping("/job-fairs")
    public Object deleteJobFairById(@RequestParam Long jobFairId) {
        databaseJobFairService.deleteById(jobFairId);
        return "删除招聘会成功! ID: " + jobFairId;
    }

    @DeleteMapping("/lectures")
    public Object deleteLectureById(@RequestParam Long lectureId) {
        databaseLectureService.deleteById(lectureId);
        return "删除宣讲会成功! ID: " + lectureId;
    }

    @DeleteMapping("/recruitments")
    public Object deleteRecruitmentById(@RequestParam Long recruitmentId) {
        databaseRecruitmentService.deleteById(recruitmentId);
        return "删除招聘信息成功! ID: " + recruitmentId;
    }

    @DeleteMapping("/selected-graduates")
    public Object deleteSelectedGraduateById(@RequestParam Long id) {
        databaseSelectedGraduatesAndInternationalOrganizationService.deleteById(id);
        return "基层选调信息删除成功! ID: " + id;
    }


    @DeleteMapping("/international-organizations")
    public Object deleteInternationalOrganizationById(@RequestParam Long id) {
        databaseSelectedGraduatesAndInternationalOrganizationService.deleteById(id);
        return "国际组织信息删除成功! ID: " + id;
    }

    @PutMapping("/posts")
    public Object updatePostById(@RequestBody Post post) {
        databasePostService.updateById(post);
        return "职位信息更新成功!";
    }

    @PutMapping("/job-fairs")
    public Object updateJobFairById(@RequestBody JobFair jobFair) {
        databaseJobFairService.updateById(jobFair);
        return "招聘会信息更新成功!";
    }

    @PutMapping("/lectures")
    public Object updateLectureById(@RequestBody Lecture lecture) {
        databaseLectureService.updateById(lecture);
        return "宣讲会信息更新成功!";
    }

    @PutMapping("/recruitments")
    public Object updateRecruitmentById(@RequestBody Recruitment recruitment) {
        databaseRecruitmentService.updateById(recruitment);
        return "招聘公告更新成功!";
    }

    @PutMapping("/selected-graduates")
    public Object updateSelectedGraduatesById(@RequestBody SelectedGraduatesOrInternationalOrganization selectedGraduatesOrInternationalOrganization) {
        databaseSelectedGraduatesAndInternationalOrganizationService.updateById(selectedGraduatesOrInternationalOrganization);
        return "基层选调信息更新成功!";
    }

    @PutMapping("/international-organizations")
    public Object updateInternationalOrganizationById(@RequestBody SelectedGraduatesOrInternationalOrganization selectedGraduatesOrInternationalOrganization) {
        databaseSelectedGraduatesAndInternationalOrganizationService.updateById(selectedGraduatesOrInternationalOrganization);
        return "国际组织信息更新成功!";
    }
}
