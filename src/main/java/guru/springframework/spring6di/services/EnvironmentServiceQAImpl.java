package guru.springframework.spring6di.services;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("QA")
@Service
public class EnvironmentServiceQAImpl implements EnvironmentService{
    @Override
    public String getEnv() {
        return "QA";
    }
}
