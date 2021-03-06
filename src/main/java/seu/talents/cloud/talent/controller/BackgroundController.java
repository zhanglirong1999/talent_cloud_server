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
        return databasePostService.getPostInformationWithTotalCountByPage(pageIndex, pageSize, postType);
    }

    @GetMapping("/job-fairs")
    public Object getJobFairs(@RequestParam Integer pageIndex, @RequestParam Integer pageSize) {
        return databaseJobFairService.getJobFairsWithTotalCountByPage(pageIndex, pageSize);
    }

    @GetMapping("/lectures")
    public Object getLectures(@RequestParam Integer pageIndex, @RequestParam Integer pageSize) {
        return databaseLectureService.getLectureWithTotalCountByPage(pageIndex, pageSize);
    }

    @GetMapping("/recruitments")
    public Object getRecruitments(@RequestParam Integer pageIndex, @RequestParam Integer pageSize) {
        return databaseRecruitmentService.getRecruitmentWithTotalCountByPage(pageIndex, pageSize);
    }

    @GetMapping("/selected-graduates")
    public Object getSelectedGraduates(@RequestParam Integer pageIndex, @RequestParam Integer pageSize) {
        return databaseSelectedGraduatesAndInternationalOrganizationService.getWithTotalCountByPage(pageIndex, pageSize, 0);
    }

    @GetMapping("/international-organizations")
    public Object getInternationalOrganizations(@RequestParam Integer pageIndex, @RequestParam Integer pageSize) {
        return databaseSelectedGraduatesAndInternationalOrganizationService.getWithTotalCountByPage(pageIndex, pageSize, 1);
    }

    @PostMapping("/posts")
    public Object addPost(@RequestBody Post post) {
        post.setPostId(ConstantUtil.generateId());
        databasePostService.addPost(post);
        return "??????????????????!";
    }

    @PostMapping("/job-fairs")
    public Object addJobFair(@RequestBody JobFair jobFair) {
        jobFair.setJobFairId(ConstantUtil.generateId());
        databaseJobFairService.addJobFair(jobFair);
        return "?????????????????????!";
    }

    @PostMapping("/lectures")
    public Object addLecture(@RequestBody Lecture lecture) {
        lecture.setLectureId(ConstantUtil.generateId());
        databaseLectureService.addLecture(lecture);
        return "?????????????????????!";
    }

    @PostMapping("/recruitments")
    public Object addRecruitment(@RequestBody Recruitment recruitment) {
        recruitment.setRecruitmentId(ConstantUtil.generateId());
        databaseRecruitmentService.addRecruitment(recruitment);
        return "????????????????????????!";
    }

    @PostMapping("/selected-graduates")
    public Object addSelectedGraduates(@RequestBody SelectedGraduatesOrInternationalOrganization selectedGraduatesOrInternationalOrganization) {
        selectedGraduatesOrInternationalOrganization.setId(ConstantUtil.generateId());
        selectedGraduatesOrInternationalOrganization.setType(0);
        databaseSelectedGraduatesAndInternationalOrganizationService.add(selectedGraduatesOrInternationalOrganization);
        return "??????????????????????????????!";
    }

    @PostMapping("/international-organizations")
    public Object addInternationalOrganization(@RequestBody SelectedGraduatesOrInternationalOrganization selectedGraduatesOrInternationalOrganization) {
        selectedGraduatesOrInternationalOrganization.setId(ConstantUtil.generateId());
        selectedGraduatesOrInternationalOrganization.setType(1);
        databaseSelectedGraduatesAndInternationalOrganizationService.add(selectedGraduatesOrInternationalOrganization);
        return "??????????????????????????????!";
    }

    @DeleteMapping("/posts")
    public Object deletePostById(@RequestParam Long postId) {
        databasePostService.deleteById(postId);
        return "??????????????????! ID: " + postId;
    }

    @DeleteMapping("/job-fairs")
    public Object deleteJobFairById(@RequestParam Long jobFairId) {
        databaseJobFairService.deleteById(jobFairId);
        return "?????????????????????! ID: " + jobFairId;
    }

    @DeleteMapping("/lectures")
    public Object deleteLectureById(@RequestParam Long lectureId) {
        databaseLectureService.deleteById(lectureId);
        return "?????????????????????! ID: " + lectureId;
    }

    @DeleteMapping("/recruitments")
    public Object deleteRecruitmentById(@RequestParam Long recruitmentId) {
        databaseRecruitmentService.deleteById(recruitmentId);
        return "????????????????????????! ID: " + recruitmentId;
    }

    @DeleteMapping("/selected-graduates")
    public Object deleteSelectedGraduateById(@RequestParam Long id) {
        databaseSelectedGraduatesAndInternationalOrganizationService.deleteById(id);
        return "??????????????????????????????! ID: " + id;
    }


    @DeleteMapping("/international-organizations")
    public Object deleteInternationalOrganizationById(@RequestParam Long id) {
        databaseSelectedGraduatesAndInternationalOrganizationService.deleteById(id);
        return "??????????????????????????????! ID: " + id;
    }

    @PutMapping("/posts")
    public Object updatePostById(@RequestBody Post post) {
        databasePostService.updateById(post);
        return "????????????????????????!";
    }

    @PutMapping("/job-fairs")
    public Object updateJobFairById(@RequestBody JobFair jobFair) {
        databaseJobFairService.updateById(jobFair);
        return "???????????????????????????!";
    }

    @PutMapping("/lectures")
    public Object updateLectureById(@RequestBody Lecture lecture) {
        databaseLectureService.updateById(lecture);
        return "???????????????????????????!";
    }

    @PutMapping("/recruitments")
    public Object updateRecruitmentById(@RequestBody Recruitment recruitment) {
        databaseRecruitmentService.updateById(recruitment);
        return "????????????????????????!";
    }

    @PutMapping("/selected-graduates")
    public Object updateSelectedGraduatesById(@RequestBody SelectedGraduatesOrInternationalOrganization selectedGraduatesOrInternationalOrganization) {
        databaseSelectedGraduatesAndInternationalOrganizationService.updateById(selectedGraduatesOrInternationalOrganization);
        return "??????????????????????????????!";
    }

    @PutMapping("/international-organizations")
    public Object updateInternationalOrganizationById(@RequestBody SelectedGraduatesOrInternationalOrganization selectedGraduatesOrInternationalOrganization) {
        databaseSelectedGraduatesAndInternationalOrganizationService.updateById(selectedGraduatesOrInternationalOrganization);
        return "??????????????????????????????!";
    }
}
