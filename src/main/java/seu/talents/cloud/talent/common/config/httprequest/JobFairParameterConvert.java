package seu.talents.cloud.talent.common.config.httprequest;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;

@Data
@Configuration
@Scope("singleton")
@PropertySource(value = "classpath:httprequest/job-fair-parameter-convert.properties", encoding = "UTF-8")
@ConfigurationProperties(prefix = "convert.job-fair")
public class JobFairParameterConvert {
    private HashMap<String, String> rangeMap;
    private HashMap<String, String> typeMap;
    private HashMap<String, String> timeMap;
}
