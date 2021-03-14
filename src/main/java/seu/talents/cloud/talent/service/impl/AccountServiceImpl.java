package seu.talents.cloud.talent.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seu.talents.cloud.talent.exception.BizException;
import seu.talents.cloud.talent.model.dao.entity.Account;
import seu.talents.cloud.talent.model.dao.mapper.AccountMapper;
import seu.talents.cloud.talent.model.dto.post.Register;
import seu.talents.cloud.talent.service.AccountService;
import seu.talents.cloud.talent.util.ConstantUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountMapper accountMapper;


    @Override
    public void registerUser(Register register, String accountId) {
        Account account = new Account();
        account.setAccountId(accountId);
        if(accountMapper.selectByPrimaryKey(account)==null){
            throw new BizException(ConstantUtil.BizExceptionCause.NOT_USER);
        }
        account.setAvatar(register.getAvatar());
        account.setName(register.getName());
        account.setLastTime(new Date());
        accountMapper.updateByPrimaryKeySelective(account);
    }

    @Override
    public Object getUserInfo(String accountId) {
        Account account = accountMapper.selectByPrimaryKey(accountId);
        if(account==null)
        {
            throw new BizException(ConstantUtil.BizExceptionCause.NOT_USER);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("name",account.getName());
        map.put("avatar",account.getAvatar());
        return map;
    }






}
