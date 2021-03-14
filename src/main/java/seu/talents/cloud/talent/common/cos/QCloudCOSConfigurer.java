package seu.talents.cloud.talent.common.cos;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = {"classpath:qcloud/qcloud-cos.properties"})
public class QCloudCOSConfigurer {

}
