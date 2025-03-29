package bck_instapic.replicate.client;

import bck_instapic.core.HttpClientWrapper;
import bck_instapic.replicate.client.request.CreateTrainingRequest;
import bck_instapic.replicate.client.response.ReplicateTraining;

import java.io.IOException;

/**
 * Manages creation, retrieval, listing, and cancellation of trainings on Replicate.
 * Adapts the endpoints from the Python snippet:
 *
 *   - POST /v1/models/{owner}/{name}/versions/{versionId}/trainings to create
 *   - GET /v1/trainings/{id} to fetch
 *   - GET /v1/trainings to list
 *   - POST /v1/trainings/{id}/cancel to cancel
 *
 * Also includes naive pagination if you want to handle `next`/`previous`.
 */
public class ReplicateTrainingService {
    private static final String TRAININGS_PATH = "/v1/trainings";
    private final HttpClientWrapper httpClientWrapper;

    public ReplicateTrainingService(HttpClientWrapper httpClientWrapper) {
        this.httpClientWrapper = httpClientWrapper;
    }

    /**
     * Create a new training using an existing base model/version on Replicate.
     *
     * @param owner     Model owner (e.g., "your-username" or org name)
     * @param modelName Model name (e.g., "sd-dreambooth")
     * @param versionId The version ID of the base model for training
     * @param request   The body describing the training input, destination, webhooks, etc.
     * @return Training    The newly created training object
     */
    public ReplicateTraining createTraining(
            String owner,
            String modelName,
            String versionId,
            CreateTrainingRequest request
    ) throws IOException {
        // e.g. POST /v1/models/{owner}/{modelName}/versions/{versionId}/trainings
        String url = String.format("/v1/models/%s/%s/versions/%s/trainings", owner, modelName, versionId);

        return httpClientWrapper.post(url, request, ReplicateTraining.class);
    }

    /**
     * Retrieve a training by its unique ID.
     */
    public ReplicateTraining getTraining(String trainingId) throws IOException {
        String path = TRAININGS_PATH + "/" + trainingId;
        return httpClientWrapper.get(path, ReplicateTraining.class);
    }

    /**
     * Cancel a running training by ID.
     */
    public ReplicateTraining cancelTraining(String trainingId) throws IOException {
        // POST /v1/trainings/{id}/cancel
        String path = String.format("%s/%s/cancel", TRAININGS_PATH, trainingId);
        return httpClientWrapper.post(path, null, ReplicateTraining.class);
    }
}
