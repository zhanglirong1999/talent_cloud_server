package seu.talents.cloud.talent.model.dto.returnDTO;

import lombok.Data;
import seu.talents.cloud.talent.model.dao.entity.Company;
import seu.talents.cloud.talent.model.dao.entity.Recommend;
import seu.talents.cloud.talent.model.dto.post.BriefInfo;
import seu.talents.cloud.talent.model.dto.post.SearchType;

import java.util.List;
import java.util.Map;

@Data
public class CompanyResDTO {
    private Company company;
    private List<Map<String,Object>> recommends;
    private List<AccountDTO> accounts;
    private Integer count;

    public CompanyResDTO(Integer count, Company company, List<Map<String,Object>> recommends,List<AccountDTO> accounts) {
        this.count = count;
        this.company = company;
        this.recommends = recommends;
        this.accounts = accounts;
    }

}
