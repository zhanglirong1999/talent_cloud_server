package seu.talents.cloud.talent.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seu.talents.cloud.talent.exception.BizException;
import seu.talents.cloud.talent.model.dao.entity.Company;
import seu.talents.cloud.talent.model.dao.mapper.CompanyMapper;
import seu.talents.cloud.talent.model.dto.post.CompanyDTO;
import seu.talents.cloud.talent.service.CompanyService;
import seu.talents.cloud.talent.util.ConstantUtil;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.*;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    CompanyMapper companyMapper;

    @Override
    public void addCompany(CompanyDTO companyDTO) {
        Company company = new Company();
        int count = companyMapper.getCompanyCount(companyDTO.getName());
        if(count>0){
            throw new BizException(ConstantUtil.BizExceptionCause.HAVE_EXSIT);
        }
        company.setCid(ConstantUtil.generateId());
        company.setName(companyDTO.getName());
        company.setLogo(companyDTO.getLogo());
        company.setInfo(companyDTO.getInfo());
        company.setUrl(companyDTO.getUrl());
        company.setHeat(companyDTO.getHeat());
        company.setCount(companyDTO.getCount());
        company.setType(companyDTO.getType());
        company.setTakeCount(companyDTO.getTakeCount());
        company.setCity(companyDTO.getCity());
        company.setPassRate(companyDTO.getPassRate());
        company.setDeleted(0);
        company.setAlumni(companyDTO.getAlumni());  //企业/校友企业
        companyMapper.insert(company);
    }

    @Override
    public void deleteCompany(Long cid) {
        companyMapper.deleteCompany(cid);
//        Company company = companyMapper.selectOneByExample(
//                Example.builder(Company.class).where(Sqls.custom().andEqualTo("cid",cid))
//                        .build()
//        );
//        company.setDeleted(1);
//        companyMapper.updateByExample(company,Example.builder(Company.class).where(Sqls.custom().andEqualTo("cid",company.getCid()))
//                .build());
    }

    @Override
    public void modifyCompany(Company company) {
        Company company2 = companyMapper.selectOneByExample(
                Example.builder(Company.class).where(Sqls.custom().andEqualTo("cid",company.getCid()))
                        .build()
        );
        company2.setType(company.getType());
        company2.setCount(company.getCount());
        company2.setHeat(company.getHeat());
        company2.setUrl(company.getUrl());
        company2.setInfo(company.getInfo());
        company2.setLogo(company.getLogo());
        company2.setName(company.getName());
        company2.setTakeCount(company.getTakeCount());
        company2.setCity(company.getCity());
        company2.setPassRate(company.getPassRate());
        companyMapper.updateByExample(company2,Example.builder(Company.class).where(Sqls.custom().andEqualTo("cid",company.getCid()))
                .build());

    }

    @Override
    public Company getCompanyInfo(Long cid) {
        Company company2 = companyMapper.selectOneByExample(
                Example.builder(Company.class).where(Sqls.custom().andEqualTo("cid",cid))
                        .build()
        );
        if(company2.getDeleted()==1){
            throw new BizException(ConstantUtil.BizExceptionCause.HAS_DELETE);
        }
        return company2;
    }

    @Override
    public Object getSearch(String keyword,Integer pageIndex) {
        Iterator<Company> iterator = companyMapper.getSearch(keyword,pageIndex).iterator();
        Integer count = companyMapper.getSearchCount(keyword);
        List<Map<String,Object>> list = new LinkedList<>();
        while (iterator.hasNext()){
            Map<String,Object> map = new HashMap<>();
            Company company = iterator.next();
            String name = company.getName();
            map.put("cid",company.getCid());
            map.put("name",company.getName());
            map.put("logo",company.getLogo());
            map.put("info",company.getInfo());
            map.put("url",company.getUrl());
            map.put("heat",company.getHeat());
            map.put("count",company.getCount());
            map.put("type",company.getType());
            map.put("takeCount",company.getTakeCount());
            map.put("city",company.getCity());
            map.put("passRate",company.getPassRate());
            map.put("recommendCount",companyMapper.getRecommendCount(name));
            map.put("experience","经验贴");
            map.put("pageCounts",count);
            list.add(map);
        }

        return list;
    }

    @Override
    public Object getCompanyList(String key, Integer pageIndex,Integer alumni) {
        List<Map<String,Object>> list = new LinkedList<>();
        Integer count = companyMapper.getCompanyCount1();
        if(key.equals("0")) {
            Iterator<Company> iterator = companyMapper.getCompanyListByHeat(pageIndex,alumni).iterator();
            while (iterator.hasNext()){
                Map<String,Object> map = new HashMap<>();
                Company company = iterator.next();
                String name = company.getName();
                map.put("cid",company.getCid());
                map.put("name",company.getName());
                map.put("logo",company.getLogo());
                map.put("info",company.getInfo());
                map.put("url",company.getUrl());
                map.put("heat",company.getHeat());
                map.put("count",company.getCount());
                map.put("type",company.getType());
                map.put("takeCount",company.getTakeCount());
                map.put("city",company.getCity());
                map.put("passRate",company.getPassRate());
                map.put("recommendCount",companyMapper.getRecommendCount(name));
                map.put("experience","经验贴");
                map.put("pageCounts",count);
                list.add(map);
            }

        } else {
            Iterator<Company> iterator = companyMapper.getCompanyListByCount(pageIndex,alumni).iterator();
            while (iterator.hasNext()){
                Map<String,Object> map = new HashMap<>();
                Company company = iterator.next();
                String name = company.getName();
                map.put("cid",company.getCid());
                map.put("name",company.getName());
                map.put("logo",company.getLogo());
                map.put("info",company.getInfo());
                map.put("url",company.getUrl());
                map.put("heat",company.getHeat());
                map.put("count",company.getCount());
                map.put("type",company.getType());
                map.put("takeCount",company.getTakeCount());
                map.put("city",company.getCity());
                map.put("passRate",company.getPassRate());
                map.put("recommendCount",companyMapper.getRecommendCount(name));
                map.put("experience","经验贴");
                map.put("pageCounts",count);
                list.add(map);
            }
        }
        return list;
    }

    @Override
    public Object getNameList() {
        return companyMapper.getNameList();
    }

}

