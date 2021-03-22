package seu.talents.cloud.talent;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import seu.talents.cloud.talent.model.dao.entity.JobFair;
import seu.talents.cloud.talent.model.dto.post.JobFairSearchDTO;
import seu.talents.cloud.talent.service.JobFairService;

import java.util.List;

@SpringBootTest
public class TestGetInformationService {

    @Qualifier("dynamicParseJobFairServiceImpl")
    @Autowired
    private JobFairService dynamicParseJobFairService;

    @Test
    public void testJobFair() {
        JobFairSearchDTO dto = new JobFairSearchDTO();
        dto.setPageIndex(1);
        dto.setTime("7");
        List<JobFair> res = dynamicParseJobFairService.searchJobFairs(dto);
        System.out.println(res.size());
    }
}
