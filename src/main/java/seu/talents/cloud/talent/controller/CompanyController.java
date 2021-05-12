package seu.talents.cloud.talent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seu.talents.cloud.talent.common.annotation.TokenRequired;
import seu.talents.cloud.talent.common.annotation.WebResponse;
import seu.talents.cloud.talent.model.dao.entity.Company;
import seu.talents.cloud.talent.model.dao.entity.Recommend;
import seu.talents.cloud.talent.model.dao.mapper.AccountMapper;
import seu.talents.cloud.talent.model.dao.mapper.CompanyMapper;
import seu.talents.cloud.talent.model.dao.mapper.RecommendMapper;
import seu.talents.cloud.talent.model.dto.post.CompanyDTO;
import seu.talents.cloud.talent.model.dto.returnDTO.AccountDTO;
import seu.talents.cloud.talent.model.dto.returnDTO.CompanyResDTO;
import seu.talents.cloud.talent.service.CompanyService;

import java.util.List;

@RequestMapping("/company")
@RestController
@CrossOrigin(origins = "http://localhost:18081", maxAge = 7200)
@WebResponse
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private RecommendMapper recommendMapper;

    @Autowired
    private CompanyMapper companyMapper;

    /**
     * 新增企业
     * @param companyDTO
     * @return
     */
    @TokenRequired
    @PostMapping("/add")
    public Object addCompany(@RequestBody CompanyDTO companyDTO){
        companyService.addCompany(companyDTO);
        return "ok";
    }

    /**
     * 注销企业（软删除）
     * @param cid
     * @return
     */
    @TokenRequired
    @PostMapping("/delete")
    public Object deleteCompany(@RequestParam Long cid){
        companyService.deleteCompany(cid);
        return "ok";
    }

    /**
     * 修改企业
     * @param company
     * @return
     */
    @TokenRequired
    @PostMapping("/modify")
    public Object modifyCompany(@RequestBody Company company){
        companyService.modifyCompany(company);
        return "ok";
    }

    /**
     * 获取企业详情
     * @param cid
     * @return
     */
    @TokenRequired
    @GetMapping("/info")
    public Object getCompanyInfo(@RequestParam Long cid) throws Exception {
        Company company = companyService.getCompanyInfo(cid);
        String name = companyMapper.getCompanyName(cid);
        int count = accountMapper.getAccountCount(name);
        List<AccountDTO> accountDTOS = accountMapper.getCompanyAccount(name);
        List<Recommend> recommends = recommendMapper.getRecommendFive(name);
        CompanyResDTO res = new CompanyResDTO(count,company,recommends,accountDTOS);
        return  res;
    }

    /**
     * 获取公司校友全部列表
     * @param cid
     * @return
     */
    @TokenRequired
    @GetMapping("/info/account")
    public Object getCompanyAccountDetail(@RequestParam Long cid,
                                          @RequestParam Integer pageIndex){
        String name = companyMapper.getCompanyName(cid);
        return accountMapper.getCompanyAccountAll(name,pageIndex);
    }

    /**
     * 获取公司全部内推
     * @param cid
     * @param pageIndex
     * @return
     */
    @TokenRequired
    @GetMapping("/info/recommend")
    public Object getCompanyRecommendDetail(@RequestParam Long cid,
                                            @RequestParam Integer pageIndex){
        String name = companyMapper.getCompanyName(cid);
        return  recommendMapper.getRecommendAll(name,pageIndex);
    }



    /**
     * 搜获企业
     * @param keyword
     * @return
     */
    @TokenRequired
    @GetMapping("/search")
    public Object getSearch(@RequestParam String keyword
            ,@RequestParam Integer pageIndex){
        return companyService.getSearch(keyword,pageIndex);
    }

    /**
     * 获取公司列表
     * @param pageIndex
     * @param filter
     * @return
     */
    @TokenRequired
    @GetMapping("/list")
    public Object getCompanyList(@RequestParam Integer pageIndex,@RequestParam Integer filter,
                                 @RequestParam String order){
        return companyService.getCompanyList(order, pageIndex,filter);
    }

    /**
     * 获取所有公司的cid和name
     * @return
     */
    @TokenRequired
    @GetMapping("/name")
    public Object getNameList(){
        return companyService.getNameList();
    }



}

