package seu.talents.cloud.talent.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import seu.talents.cloud.talent.service.fail.ActivityFailPrompt;

import java.io.IOException;

@Configuration
public class ServiceFailPromptConfig {

    @Bean
    public ActivityFailPrompt activityFailPrompt() throws IOException {
        return new ActivityFailPrompt("qcloud/fail-cos.properties");
    }

}
