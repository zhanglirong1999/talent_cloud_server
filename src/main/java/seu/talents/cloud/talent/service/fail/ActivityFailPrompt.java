package seu.talents.cloud.talent.service.fail;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import seu.talents.cloud.talent.service.fail.ServiceFailPrompt;

import java.io.IOException;

public class ActivityFailPrompt extends ServiceFailPrompt {

    public ActivityFailPrompt(String relativePath) throws IOException {
        this.relativePath = relativePath;
        this.configByProperties(relativePath);
    }
}