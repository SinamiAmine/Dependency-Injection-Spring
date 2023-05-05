package guru.springframework.spring6di.services;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("UAT")
@Service
public class EnvironmentServiceUATImpl implements EnvironmentService{
    @Override
    public String getEnv() {
        return "UAT";
    }
}
