package seu.talents.cloud.talent.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import seu.talents.cloud.talent.common.config.httprequest.*;
import seu.talents.cloud.talent.model.dto.post.JobFairSearchDTO;
import seu.talents.cloud.talent.model.dto.post.LectureSearchDTO;
import seu.talents.cloud.talent.model.dto.post.PostSearchDTO;
import seu.talents.cloud.talent.model.dto.post.RecruitmentSearchDTO;

import java.util.HashMap;

@Component
@Aspect
@Slf4j
public class ConvertParametersAspect {

    private final PostParameterConvert postParameterConvert;
    private final JobFairParameterConvert jobFairParameterConvert;
    private final DistrictParameterConvert districtParameterConvert;
    private final TimeParameterConvert timeParameterConvert;
    private final RecruitmentParameterConvert recruitmentParameterConvert;

    @Autowired
    public ConvertParametersAspect(
            PostParameterConvert postParameterConvert,
            JobFairParameterConvert jobFairParameterConvert,
            RecruitmentParameterConvert recruitmentParameterConvert,
            DistrictParameterConvert districtParameterConvert,
            TimeParameterConvert timeParameterConvert
    ) {
        this.postParameterConvert = postParameterConvert;
        this.jobFairParameterConvert = jobFairParameterConvert;
        this.recruitmentParameterConvert = recruitmentParameterConvert;
        this.districtParameterConvert = districtParameterConvert;
        this.timeParameterConvert = timeParameterConvert;
    }

    @Pointcut("@annotation(seu.talents.cloud.talent.common.annotation.ConvertParameters)")
    public void method() {}

    @Around("method()")
    public Object convertParameters(ProceedingJoinPoint joinPoint) throws Throwable {
        Object parameter = joinPoint.getArgs()[0];
        Object newDTO = null;
        if (parameter instanceof PostSearchDTO) {
            newDTO = constructNew((PostSearchDTO) parameter);
        } else if (parameter instanceof JobFairSearchDTO) {
            newDTO = constructNew((JobFairSearchDTO) parameter);
        } else if (parameter instanceof LectureSearchDTO) {
            newDTO = constructNew((LectureSearchDTO) parameter);
        } else if (parameter instanceof RecruitmentSearchDTO) {
            newDTO = constructNew((RecruitmentSearchDTO) parameter);
        } else {
            log.error("错误使用了@ConvertParameters注解!请仅在需要进行参数转换的方法前添加");
        }

        Object ret;
        try {
            ret = joinPoint.proceed(new Object[]{newDTO});
        } catch (Throwable throwable) {
            log.error("执行转换参数时发送错误。错误为：" + throwable.getMessage());
            throw throwable;
        }

        return ret;
    }

    private PostSearchDTO constructNew(PostSearchDTO postSearchDTO) {
        HashMap<String, String> postParameterMap = postParameterConvert.getParametersMap();
        HashMap<String, String> districtMap = districtParameterConvert.getParametersMap();
        HashMap<String, String> timeMap = timeParameterConvert.getParametersMap();
        PostSearchDTO newPostSearchDTO = new PostSearchDTO();
        newPostSearchDTO.setPageIndex(postSearchDTO.getPageIndex());
        newPostSearchDTO.setPostType(postSearchDTO.getPostType());
        newPostSearchDTO.setKeyWord(postSearchDTO.getKeyWord());
        newPostSearchDTO.setEducation(postParameterMap.get(postSearchDTO.getEducation()));
        newPostSearchDTO.setCity(districtMap.get(postSearchDTO.getCity()));
        newPostSearchDTO.setTime(timeMap.get(postSearchDTO.getTime()));
        newPostSearchDTO.setIndustry(postParameterMap.get(postSearchDTO.getIndustry()));
        newPostSearchDTO.setNature(postParameterMap.get(postSearchDTO.getNature()));
        newPostSearchDTO.setSalary(postParameterMap.get(postSearchDTO.getSalary()));
        newPostSearchDTO.setScale(postParameterMap.get(postSearchDTO.getScale()));
        newPostSearchDTO.setSkill(postParameterMap.get(postSearchDTO.getSkill()));
        return newPostSearchDTO;
    }

    private JobFairSearchDTO constructNew(JobFairSearchDTO oldDTO) {
        JobFairSearchDTO newJobFairSearchDTO = new JobFairSearchDTO();
        newJobFairSearchDTO.setPageIndex(oldDTO.getPageIndex());
        newJobFairSearchDTO.setTime(jobFairParameterConvert.getTimeMap().get(oldDTO.getTime()));
        newJobFairSearchDTO.setCity(districtParameterConvert.getParametersMap().get(oldDTO.getCity()));
        newJobFairSearchDTO.setKeyWord(oldDTO.getKeyWord());
        newJobFairSearchDTO.setRange(jobFairParameterConvert.getRangeMap().get(oldDTO.getRange()));
        newJobFairSearchDTO.setType(jobFairParameterConvert.getTypeMap().get(oldDTO.getType()));
        return newJobFairSearchDTO;
    }

    private LectureSearchDTO constructNew(LectureSearchDTO oldDTO) {
        LectureSearchDTO newLectureSearchDTO = new LectureSearchDTO();
        newLectureSearchDTO.setKeyWord(oldDTO.getKeyWord());
        newLectureSearchDTO.setPageIndex(oldDTO.getPageIndex());
        newLectureSearchDTO.setTime(timeParameterConvert.getParametersMap().get(oldDTO.getTime()));
        return newLectureSearchDTO;
    }

    private RecruitmentSearchDTO constructNew(RecruitmentSearchDTO oldDTO) {
        RecruitmentSearchDTO newRecruitmentSearchDTO = new RecruitmentSearchDTO();
        newRecruitmentSearchDTO.setPageIndex(oldDTO.getPageIndex());
        newRecruitmentSearchDTO.setKeyWord(oldDTO.getKeyWord());
        newRecruitmentSearchDTO.setCity(districtParameterConvert.getParametersMap().get(oldDTO.getCity()));
        newRecruitmentSearchDTO.setRange(recruitmentParameterConvert.getRangeMap().get(oldDTO.getRange()));
        newRecruitmentSearchDTO.setTime(timeParameterConvert.getParametersMap().get(oldDTO.getTime()));
        return newRecruitmentSearchDTO;
    }
}
