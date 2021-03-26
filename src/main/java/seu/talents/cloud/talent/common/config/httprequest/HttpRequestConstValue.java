package seu.talents.cloud.talent.common.config.httprequest;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

@Data
@Configuration
@Scope("singleton")
@PropertySource(value = "classpath:httprequest/request-config.properties", encoding = "UTF-8")
@ConfigurationProperties(prefix = "template")
public class HttpRequestConstValue {
    private String postUrlTemplateForSearchFullTimeJob;
    private String postUrlTemplateForSearchIntern;
    private String postUrlTemplateForGetFullTimeJob;
    private String postUrlTemplateForGetIntern;
    private String jobFairUrlTemplateForSearch;
    private String jobFairUrlTemplateForGet;
    private String lectureUrlTemplateForGet;
    private String lectureUrlTemplateForSearch;
    private String recruitmentUrlTemplateForGet;
    private String recruitmentUrlTemplateForSearch;
    private String selectedGraduatesUrlTemplateForGet;
    private String selectedGraduatesUrlTemplateForSearch;
    private String internationalOrganizationUrlTemplateForGet;
    private String internationalOrganizationUrlTemplateForSearch;
    private String urlPrefix;
    private String source;
}
