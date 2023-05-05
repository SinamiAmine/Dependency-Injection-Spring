package guru.springframework.spring6di.services;


import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("PROD")
@Service
public class EnvironmentServicePRODImpl implements EnvironmentService {
    @Override
    public String getEnv() {
        return "PROD";
    }
}
