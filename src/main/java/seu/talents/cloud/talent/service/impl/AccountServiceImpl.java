package seu.talents.cloud.talent.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seu.talents.cloud.talent.common.CONST;
import seu.talents.cloud.talent.exception.BizException;
import seu.talents.cloud.talent.model.dao.entity.Account;
import seu.talents.cloud.talent.model.dao.entity.Job;
import seu.talents.cloud.talent.model.dao.mapper.AccountMapper;
import seu.talents.cloud.talent.model.dao.mapper.JobMapper;
import seu.talents.cloud.talent.model.dto.post.AccountAllDTO;
import seu.talents.cloud.talent.model.dto.post.AdminDTO;
import seu.talents.cloud.talent.model.dto.post.Register;
import seu.talents.cloud.talent.model.dto.returnDTO.JobDTO;
import seu.talents.cloud.talent.service.AccountService;
import seu.talents.cloud.talent.util.ConstantUtil;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private JobMapper jobMapper;

    @Override
    public void registerUser(Register register,String accountId) {
        Account account = new Account();
        account.setAccountId(accountId);
        if(accountMapper.selectByPrimaryKey(account)==null){
            throw new BizException(ConstantUtil.BizExceptionCause.NOT_USER);
        }
        account.setName(register.getName());
        account.setPhone(register.getPhone());
        account.setCollage(register.getCollege());
        account.setGradYear(register.getGradYear());
        account.setGradDegree(register.getGradDegree());
        account.setCompany(register.getCompany());
        account.setJob(register.getJob());
        account.setCanRecom(register.getCanRecom());
        account.setAvatar(CONST.avatar);
        accountMapper.updateByPrimaryKeySelective(account);
    }

    @Override
    public Object getUserInfo(String accountId) {
        Account account = accountMapper.selectByPrimaryKey(accountId);
        if(account==null)
        {
            throw new BizException(ConstantUtil.BizExceptionCause.NOT_USER);
        }
        String avatar =null;
        if(account.getAvatar()==null){
            avatar = CONST.avatar;
        }else {
            avatar = account.getAvatar();
        }
        Map<String,Object> map = new HashMap<>();

        map.put("name",account.getName());
        map.put("avatar",avatar);
        map.put("gradYear",account.getGradYear());
        map.put("company",account.getCompany());
        map.put("job",account.getJob());
        map.put("college",account.getCollage());
        return map;
    }

    @Override
    public String adminLogin(AdminDTO adminDTO) {
        Account account = accountMapper.getAdmin(adminDTO.getUsername(),adminDTO.getPassword());
        if(account==null){
            throw new BizException(ConstantUtil.BizExceptionCause.ERROR_ADMIN);
        }
        String accountId = account.getAccountId();
        return accountId;
    }

    @Override
    public AccountAllDTO getAccountAllDTOById(String accountId) {
        AccountAllDTO accountAllDTO = new AccountAllDTO();
        // 查询 account 信息
        accountAllDTO.setAccount(accountMapper.getAccount(accountId));

//        // 查询 education 信息
//        Example example1 = new Example(Education.class);
//        example1.orderBy("endTime").desc();
//        example1.createCriteria().andEqualTo("accountId", accountId)
//                .andEqualTo("validStatus", true);
//        accountAllDTO.setEducations(educationMapper.selectByExample(example1)
//                .stream().map(EducationDTO::new).collect(Collectors.toList()));

        // 查询 job 信息

        Example example2 = new Example(Job.class);
        example2.orderBy("endTime").desc();
        example2.createCriteria().andEqualTo("accountId", accountId)
                .andEqualTo("deleted", 0);

        accountAllDTO.setJobs(jobMapper.selectByExample(example2)
                .stream().map(JobDTO::new).collect(Collectors.toList()));

        return accountAllDTO;
    }


}
