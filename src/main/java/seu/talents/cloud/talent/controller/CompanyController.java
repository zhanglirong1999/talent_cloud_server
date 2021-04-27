package seu.talents.cloud.talent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seu.talents.cloud.talent.common.annotation.TokenRequired;
import seu.talents.cloud.talent.common.annotation.WebResponse;
import seu.talents.cloud.talent.model.dao.entity.Company;
import seu.talents.cloud.talent.model.dto.post.CompanyDTO;
import seu.talents.cloud.talent.service.CompanyService;

@RequestMapping("/company")
@RestController
@CrossOrigin(origins = "http://localhost:18081", maxAge = 7200)
@WebResponse
public class CompanyController {
    @Autowired
    private CompanyService companyService;

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
        return companyService.getCompanyInfo(cid);
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
    public Object getCompanyList(@RequestParam Integer pageIndex,@RequestParam String filter,
                                 @RequestParam Integer alumni){
        return companyService.getCompanyList(filter, pageIndex,alumni);
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

