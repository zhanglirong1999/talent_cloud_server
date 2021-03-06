package seu.talents.cloud.talent.service;

import io.swagger.models.auth.In;
import seu.talents.cloud.talent.model.dao.entity.Company;
import seu.talents.cloud.talent.model.dto.post.CompanyDTO;

public interface CompanyService {

    void addCompany(CompanyDTO companyDTO);
    void deleteCompany(Long cid);
    void modifyCompany(Company company);
    Company getCompanyInfo(Long cid) throws Exception;
    Object getSearch(String keyword,Integer pageIndex);
    Object getCompanyList(String key, Integer pageIndex, Integer alumni);
    Object getNameList();
}