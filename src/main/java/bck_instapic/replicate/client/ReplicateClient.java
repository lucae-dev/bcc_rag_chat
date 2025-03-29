package bck_instapic.replicate.client;

import bck_instapic.core.HttpClientWrapper;
import bck_instapic.replicate.configuration.ReplicateProperties;
import org.springframework.stereotype.Component;

@Component
public class ReplicateClient {
    private final HttpClientWrapper httpClientWrapper;
    private final ReplicateProperties replicateProperties;
    private final ReplicateTrainingService replicateTrainingService;
    private final FluxDevLoraTrainerService fluxDevLoraTrainerService;
    private final ReplicatePredictionService replicatePredictionService;

    public ReplicateClient(ReplicateProperties replicateProperties) {
        this.replicateProperties = replicateProperties;
        this.httpClientWrapper = new HttpClientWrapper(replicateProperties.getBaseUrl(), replicateProperties.getApiKey());

        this.replicateTrainingService = new ReplicateTrainingService(httpClientWrapper);
        this.fluxDevLoraTrainerService = new FluxDevLoraTrainerService(httpClientWrapper);
        this.replicatePredictionService = new ReplicatePredictionService(httpClientWrapper);
    }


}
