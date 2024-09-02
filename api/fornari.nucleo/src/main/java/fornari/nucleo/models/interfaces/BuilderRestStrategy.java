package fornari.nucleo.models.interfaces;

import org.springframework.web.client.RestClient;

public interface BuilderRestStrategy {
    RestClient builderRest(String url);
}