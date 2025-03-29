package bck_instapic.replicate.client.request;

import java.util.List;
import java.util.Map;

public class CreateGenericTrainingRequest extends CreateTrainingRequest {
    /**
     * The input to the training.
     * E.g., { "prompt": "A scenic mountain", "num_steps": 5000, ... }
     */
    private final Map<String, Object> input;

    public CreateGenericTrainingRequest(Map<String, Object> input) {
        this.input = input;
    }

    public CreateGenericTrainingRequest(String destination, String webhook, String webhookCompleted, List<String> webhookEventsFilter, Map<String, Object> input) {
        super(destination, webhook, webhookCompleted, webhookEventsFilter);
        this.input = input;
    }

    public Map<String, Object> getInput() {
        return input;
    }
}
