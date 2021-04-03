package seu.talents.cloud.talent.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seu.talents.cloud.talent.model.dao.entity.Account;
import seu.talents.cloud.talent.model.dao.entity.Company;
import seu.talents.cloud.talent.model.dao.entity.Recommend;
import seu.talents.cloud.talent.model.dao.mapper.AccountMapper;
import seu.talents.cloud.talent.model.dao.mapper.CompanyMapper;
import seu.talents.cloud.talent.model.dao.mapper.RecommendMapper;
import seu.talents.cloud.talent.model.dao.mapper.RecommendPictureMapper;
import seu.talents.cloud.talent.model.dto.post.RecommendDTO;
import seu.talents.cloud.talent.model.dto.post.RecommendModifyDTO;
import seu.talents.cloud.talent.service.RecommendService;
import seu.talents.cloud.talent.util.ConstantUtil;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.*;

@Service
public class RecommendServiceImpl implements RecommendService {
    @Autowired
    private RecommendMapper recommendMapper;
    @Autowired
    private RecommendPictureMapper recommendPictureMapper;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public void addRecommend(RecommendDTO recommendDTO, String accountId) {
        Recommend recommend = new Recommend();
        Long rid = ConstantUtil.generateId();
        recommend.setRid(rid);
        recommend.setName(recommendDTO.getName());
        recommend.setDepartment(recommendDTO.getDepartment());
        recommend.setCompany(recommendDTO.getCompany());
        recommend.setDegree(recommendDTO.getDegree());
        recommend.setCity(recommendDTO.getCity());
        recommend.setTag(recommendDTO.getTag());
        recommend.setSalarymin(recommendDTO.getSalarymin());
        recommend.setSalarymax(recommendDTO.getSalarymax());
        recommend.setInfo(recommendDTO.getInfo());
        recommend.setMethod(recommendDTO.getMethod());
        recommend.setAid(accountId);
        recommend.setType(recommendDTO.getType());
        recommend.setUnit(recommendDTO.getUnit());
        recommendMapper.insert(recommend);
        Iterator<String> iterator = recommendDTO.getImg().iterator();
        while(iterator.hasNext()){
            String url = iterator.next();
            recommendPictureMapper.insertIMG(rid,url);
        }

    }

    @Override
    public void deleteRecommend(Long rid) {
        recommendMapper.deleteRecommend(rid);
    }

    @Override
    public void modifyRecommend(RecommendModifyDTO recommendDTO) {
        Recommend recommend = recommendMapper.selectOneByExample(
                Example.builder(Recommend.class).where(Sqls.custom().andEqualTo("rid",recommendDTO.getRid()))
                        .build()
        );
        recommend.setName(recommendDTO.getName());
        recommend.setDepartment(recommendDTO.getDepartment());
        recommend.setCompany(recommendDTO.getCompany());
        recommend.setDegree(recommendDTO.getDegree());
        recommend.setCity(recommendDTO.getCity());
        recommend.setTag(recommendDTO.getTag());
        recommend.setSalarymin(recommendDTO.getSalarymin());
        recommend.setSalarymax(recommendDTO.getSalarymax());
        recommend.setInfo(recommendDTO.getInfo());
        recommend.setMethod(recommendDTO.getMethod());
        recommend.setCreateTime(recommendDTO.getCreateTime());
        recommend.setType(recommendDTO.getType());
        recommend.setUnit(recommendDTO.getUnit());
        recommendMapper.updateByExample(recommend,Example.builder(Recommend.class).where(Sqls.custom().andEqualTo("rid",recommendDTO.getRid()))
                .build());
    }

    @Override
    public Object getRecommendList(Integer pageIndex,Integer type) {
        Iterator<Recommend> iterator= recommendMapper.getRecommendList(pageIndex,type).iterator();
        List<Map<String,Object>> list = new LinkedList<>();
        Integer count = recommendMapper.getRecommendListCount(type);
        while(iterator.hasNext()){
            Recommend recommend = iterator.next();
            Map<String,Object> map = new HashMap<>();
            map.put("rid",recommend.getRid());
            map.put("name",recommend.getName());
            map.put("salarymin",recommend.getSalarymin());
            map.put("department",recommend.getDepartment());
            map.put("degree",recommend.getDegree());
            map.put("city",recommend.getCity());
            map.put("tag",recommend.getTag());
            map.put("company",recommend.getCompany());
            map.put("salarymax",recommend.getSalarymax());
            map.put("info",recommend.getInfo());
            map.put("method",recommend.getMethod());
            map.put("createTime",recommend.getCreateTime());
            map.put("account",accountMapper.getName(recommend.getAid()));
            map.put("img",recommendPictureMapper.getUrl(recommend.getRid()));
            map.put("gap",accountMapper.getGradYear(recommend.getAid()));
            map.put("college",accountMapper.getCollage(recommend.getAid()));
            map.put("unit",recommend.getUnit());
            map.put("type",recommend.getType());
            map.put("pageCount",count);
            list.add(map);
        }
        return list;
    }

    @Override
    public Object getRecommendDetail(Long rid) {
        Recommend recommend = recommendMapper.getRecommend(rid);
        Map<String,Object> map = new HashMap<>();
        map.put("rid",recommend.getRid());
        map.put("name",recommend.getName());
        map.put("salarymin",recommend.getSalarymin());
        map.put("department",recommend.getDepartment());
        map.put("degree",recommend.getDegree());
        map.put("city",recommend.getCity());
        map.put("tag",recommend.getTag());
        map.put("company",recommend.getCompany());
        map.put("salarymax",recommend.getSalarymax());
        map.put("info",recommend.getInfo());
        map.put("method",recommend.getMethod());
        map.put("createTime",recommend.getCreateTime());
        map.put("account",accountMapper.getName(recommend.getAid()));
        map.put("img",recommendPictureMapper.getUrl(recommend.getRid()));
        map.put("accountId",recommend.getAid());
        map.put("companyId",companyMapper.getCompanyId(recommend.getCompany()));
        map.put("unit",recommend.getUnit());
        map.put("type",recommend.getType());
        map.put("job",accountMapper.getJob(recommend.getAid()));
        Company company = companyMapper.getCompany(recommend.getCompany());
        map.put("heat",company.getHeat());
        map.put("count",company.getCount());
        map.put("takeCount",company.getTakeCount());
        map.put("passRate",company.getPassRate());
        map.put("logo",company.getLogo());
        map.put("companyCity",company.getCity());
        Account account = accountMapper.getAccount(recommend.getAid());
        map.put("gradYear",account.getGradYear());
        map.put("college",account.getCollage());
        map.put("accountCompany",account.getCompany());
        map.put("avatar",account.getAvatar());
        map.put("experience",0);
        map.put("recommendCount",recommendMapper.getCompanyName(recommend.getCompany()));

        return map;
    }

    @Override
    public Object getRecommendSearch(String keyword, Integer pageIndex) {
        Iterator<Recommend> iterator= recommendMapper.getRecommendSearch(keyword, pageIndex).iterator();
        List<Map<String,Object>> list = new LinkedList<>();
        Integer count = recommendMapper.getRecommendSearchCount(keyword);
        while(iterator.hasNext()){
            Recommend recommend = iterator.next();
            Map<String,Object> map = new HashMap<>();
            map.put("rid",recommend.getRid());
            map.put("name",recommend.getName());
            map.put("salarymin",recommend.getSalarymin());
            map.put("department",recommend.getDepartment());
            map.put("degree",recommend.getDegree());
            map.put("city",recommend.getCity());
            map.put("tag",recommend.getTag());
            map.put("company",recommend.getCompany());
            map.put("salarymax",recommend.getSalarymax());
            map.put("info",recommend.getInfo());
            map.put("method",recommend.getMethod());
            map.put("createTime",recommend.getCreateTime());
            map.put("account",accountMapper.getName(recommend.getAid()));
            map.put("img",recommendPictureMapper.getUrl(recommend.getRid()));
            map.put("count",count);
            list.add(map);
        }
        return list;
    }
}
