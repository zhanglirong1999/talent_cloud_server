package seu.talents.cloud.talent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableConfigurationProperties
@MapperScan(basePackages ="seu.talents.cloud.talent.model.dao.mapper" ) // 注意，要换成 tk 提供的 @MapperScan 注解
public class TalentApplication {

    public static void main(String[] args) {
        SpringApplication.run(TalentApplication.class, args);
    }

}
