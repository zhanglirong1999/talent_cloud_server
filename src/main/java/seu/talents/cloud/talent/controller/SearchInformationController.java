package seu.talents.cloud.talent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import seu.talents.cloud.talent.common.annotation.WebResponse;
import seu.talents.cloud.talent.model.dao.entity.*;
import seu.talents.cloud.talent.model.dto.post.*;
import seu.talents.cloud.talent.service.*;

import java.util.List;

@RestController
@WebResponse
@RequestMapping("/search")
@CrossOrigin
public class SearchInformationController {
    private final PostService dynamicParsePostService;
    private final PostService databasePostService;
    private final JobFairService dynamicParseJobFairService;
    private final JobFairService databaseJobFairService;
    private final LectureService dynamicParseLectureService;
    private final LectureService databaseLectureService;
    private final RecruitmentService dynamicParseRecruitmentService;
    private final RecruitmentService databaseRecruitmentService;
    private final SelectedGraduatesAndInternationalOrganizationService dynamicParseSelectedGraduatesAndInternationalOrganizationService;
    private final SelectedGraduatesAndInternationalOrganizationService databaseSelectedGraduatesAndInternationalOrganizationService;


    @Autowired
    public SearchInformationController(
            @Qualifier("databasePostService") PostService databasePostService,
            @Qualifier("dynamicParsePostService") PostService dynamicParsePostService,
            @Qualifier("databaseJobFairService") JobFairService databaseJobFairService,
            @Qualifier("dynamicParseJobFairService") JobFairService dynamicParseJobFairService,
            @Qualifier("databaseLectureService") LectureService databaseLectureService,
            @Qualifier("dynamicParseLectureService") LectureService dynamicParseLectureService,
            @Qualifier("databaseRecruitmentService") RecruitmentService databaseRecruitmentService,
            @Qualifier("dynamicParseRecruitmentService") RecruitmentService dynamicParseRecruitmentService,
            @Qualifier("databaseSelectedGraduatesAndInternationalOrganizationService") SelectedGraduatesAndInternationalOrganizationService databaseSelectedGraduatesAndInternationalOrganizationService,
            @Qualifier("dynamicParseSelectedGraduatesAndInternationalOrganizationService") SelectedGraduatesAndInternationalOrganizationService dynamicParseSelectedGraduatesAndInternationalOrganizationService
    ) {
        this.databaseJobFairService = databaseJobFairService;
        this.dynamicParseJobFairService = dynamicParseJobFairService;
        this.databasePostService = databasePostService;
        this.dynamicParsePostService = dynamicParsePostService;
        this.databaseLectureService = databaseLectureService;
        this.dynamicParseLectureService = dynamicParseLectureService;
        this.databaseRecruitmentService = databaseRecruitmentService;
        this.dynamicParseRecruitmentService = dynamicParseRecruitmentService;
        this.databaseSelectedGraduatesAndInternationalOrganizationService = databaseSelectedGraduatesAndInternationalOrganizationService;
        this.dynamicParseSelectedGraduatesAndInternationalOrganizationService = dynamicParseSelectedGraduatesAndInternationalOrganizationService;
    }

    @PostMapping("/posts")
    public Object searchPosts(@RequestBody PostSearchDTO postSearchDTO) {
        List<Post> result = dynamicParsePostService.searchPost(postSearchDTO);
        result.addAll(databasePostService.searchPost(postSearchDTO));
        return result;
    }

    @PostMapping("/jobfairs")
    public Object searchJobFairs(@RequestBody JobFairSearchDTO jobFairSearchDTO) {
        List<JobFair> result = dynamicParseJobFairService.searchJobFairs(jobFairSearchDTO);
        result.addAll(databaseJobFairService.searchJobFairs(jobFairSearchDTO));
        return result;
    }

    @PostMapping("/lectures")
    public Object searchLectures(@RequestBody LectureSearchDTO lectureSearchDTO) {
        List<Lecture> result = dynamicParseLectureService.searchLecture(lectureSearchDTO);
        result.addAll(databaseLectureService.searchLecture(lectureSearchDTO));
        return result;
    }

    @PostMapping("/recruitments")
    public Object searchRecruitment(@RequestBody RecruitmentSearchDTO recruitmentSearchDTO) {
        List<Recruitment> result = dynamicParseRecruitmentService.searchRecruitment(recruitmentSearchDTO);
        result.addAll(databaseRecruitmentService.searchRecruitment(recruitmentSearchDTO));
        return result;
    }

    @PostMapping("/selected-graduates")
    public Object searchSelectedGraduates(@RequestBody SelectedGraduatesAndInternationalOrganizationSearchDTO dto) {
        List<SelectedGraduatesOrInternationalOrganization> result = dynamicParseSelectedGraduatesAndInternationalOrganizationService.search(0, dto);
        result.addAll(databaseSelectedGraduatesAndInternationalOrganizationService.search(0, dto));
        return result;
    }

    @PostMapping("/international-organizations")
    public Object searchInternationalOrganization(@RequestBody SelectedGraduatesAndInternationalOrganizationSearchDTO dto) {
        List<SelectedGraduatesOrInternationalOrganization> result = dynamicParseSelectedGraduatesAndInternationalOrganizationService.search(1, dto);
        result.addAll(databaseSelectedGraduatesAndInternationalOrganizationService.search(1, dto));
        return result;
    }
}
