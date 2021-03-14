package seu.talents.cloud.talent.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import seu.talents.cloud.talent.common.CONST;

import java.util.List;

@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate redisTemplate;


    public void setSchoolIdInRedis(Long schoolId,String accountId){
//        redisTemplate.opsForValue().set(CONST.SCHOOL_ID,schoolId);
        redisTemplate.opsForHash().put(accountId, CONST.SCHOOL_ID,schoolId);

    }
    public Long getSchoolId(String accountId){
        System.out.println(redisTemplate.opsForHash().get(accountId,CONST.SCHOOL_ID));
        if(redisTemplate.opsForHash().get(accountId,CONST.SCHOOL_ID)!=null) {
            return (Long) redisTemplate.opsForHash().get(accountId, CONST.SCHOOL_ID);
        }else {
            return null;
        }
    }


}
