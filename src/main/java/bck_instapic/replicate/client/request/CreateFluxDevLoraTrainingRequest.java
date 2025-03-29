package bck_instapic.replicate.client.request;

import java.util.List;

public class CreateFluxDevLoraTrainingRequest extends CreateTrainingRequest {
    private final FluxDevLoraTrainerInput input;

    public CreateFluxDevLoraTrainingRequest(FluxDevLoraTrainerInput input) {
        this.input = input;
    }

    public CreateFluxDevLoraTrainingRequest(String destination, String webhook, String webhookCompleted, List<String> webhookEventsFilter, FluxDevLoraTrainerInput input) {
        super(destination, webhook, webhookCompleted, webhookEventsFilter);
        this.input = input;
    }

    public FluxDevLoraTrainerInput getInput() {
        return input;
    }
}
