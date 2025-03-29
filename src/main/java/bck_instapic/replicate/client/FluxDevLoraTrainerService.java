package bck_instapic.replicate.client;

import bck_instapic.core.HttpClientWrapper;
import bck_instapic.replicate.client.request.CreateFluxDevLoraTrainingRequest;
import bck_instapic.replicate.client.request.CreateFluxDevLoraTrainingRequestBuilder;
import bck_instapic.replicate.client.request.FluxDevLoraTrainerInput;
import bck_instapic.replicate.client.response.ReplicateTraining;

import java.io.IOException;

/**
 * A dedicated service for ostris/flux-dev-lora-trainer model,
 * version d995297071a44dcb72244e6c19462111649ec86a9646c32df56daa7f14801944
 *
 * This saves you from repeating the same path structure in your code.
 */
public class FluxDevLoraTrainerService {

    private static final String OWNER = "ostris";
    private static final String MODEL_NAME = "flux-dev-lora-trainer";
    private static final String VERSION_ID = "d995297071a44dcb72244e6c19462111649ec86a9646c32df56daa7f14801944";

    private final HttpClientWrapper httpClientWrapper;

    public FluxDevLoraTrainerService(HttpClientWrapper httpClientWrapper) {
        this.httpClientWrapper = httpClientWrapper;
    }

    /**
     * Create a new training job for the flux-dev-lora-trainer model.
     *
     * @param request a specialized request object containing:
     *                - destination
     *                - input (inputImages, triggerWord, optional steps, etc.)
     *                - optional webhooks
     * @return the newly created Training object
     */
    public ReplicateTraining createFluxDevLoraTraining(CreateFluxDevLoraTrainingRequest request) throws IOException {
        // Build the endpoint:
        // POST /v1/models/ostris/flux-dev-lora-trainer/versions/<VERSION_ID>/trainings
        String url = String.format(
                "/v1/models/%s/%s/versions/%s/trainings",
                OWNER,
                MODEL_NAME,
                VERSION_ID
        );

        return httpClientWrapper.post(url, request, ReplicateTraining.class);
    }

    /**
     * Convenience method if you just want to pass the bare minimum fields.
     */
    public ReplicateTraining createFluxDevLoraTraining(
            String destination,
            String inputImages,
            String triggerWord,
            Integer steps,
            Integer loraRank
    ) throws IOException {
        FluxDevLoraTrainerInput input = new FluxDevLoraTrainerInput();
        input.setInputImages(inputImages);
        input.setTriggerWord(triggerWord);
        if (steps != null) {
            input.setSteps(steps);
        }
        if (loraRank != null) {
            input.setLoraRank(loraRank);
        }

        CreateFluxDevLoraTrainingRequest req = new CreateFluxDevLoraTrainingRequestBuilder()
                .withDestination(destination)
                .withInput(input)
                .build();

        // optionally set webhooks, etc. if you want them to default to something

        return createFluxDevLoraTraining(req);
    }

    // If you want methods to retrieve or cancel these trainings by ID,
    // you can either reuse the standard TrainingService or replicate a few calls:

    /**
     * Retrieve a training by ID. (Uses the same /v1/trainings/{id} endpoint)
     */
    public ReplicateTraining getFluxDevLoraTraining(String trainingId) throws IOException {
        String path = "/v1/trainings/" + trainingId;
        return httpClientWrapper.get(path, ReplicateTraining.class);
    }

    /**
     * Cancel a flux-dev-lora training by ID. (Uses the same /v1/trainings/{id}/cancel)
     */
    public ReplicateTraining cancelFluxDevLoraTraining(String trainingId) throws IOException {
        String path = String.format("/v1/trainings/%s/cancel", trainingId);
        return httpClientWrapper.post(path, null, ReplicateTraining.class);
    }
}