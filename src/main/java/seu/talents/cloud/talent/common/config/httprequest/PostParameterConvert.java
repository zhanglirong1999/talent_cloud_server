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
@ConfigurationProperties(prefix = "convert.post")
@PropertySource(value = "classpath:httprequest/post-parameter-convert.properties", encoding = "UTF-8")
public class PostParameterConvert {
    private HashMap<String, String> parametersMap;
}
