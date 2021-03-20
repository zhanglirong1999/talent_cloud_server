package seu.talents.cloud.talent.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public Object getRecommendList(Integer pageIndex) {
        Iterator<Recommend> iterator= recommendMapper.getRecommendList(pageIndex).iterator();
        List<Map<String,Object>> list = new LinkedList<>();
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
        map.put("companyId",companyMapper.getCompanyId(recommend.getName()));
        return map;
    }

    @Override
    public Object getRecommendSearch(String keyword, Integer pageIndex) {
        Iterator<Recommend> iterator= recommendMapper.getRecommendSearch(keyword, pageIndex).iterator();
        List<Map<String,Object>> list = new LinkedList<>();
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
            list.add(map);
        }
        return list;
    }
}
