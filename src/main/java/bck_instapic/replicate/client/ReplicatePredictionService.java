package bck_instapic.replicate.client;

import bck_instapic.core.HttpClientWrapper;
import bck_instapic.replicate.client.request.ReplicatePredictionInput;
import bck_instapic.replicate.client.request.ReplicatePredictionRequest;
import bck_instapic.replicate.client.response.PredictionListResponse;
import bck_instapic.replicate.client.response.ReplicatePredictionAsyncResponse;

import java.io.IOException;
import java.util.List;

/**
 * Handles creation, retrieval, listing, and cancellation of predictions.
 */
public class ReplicatePredictionService {
    private static final String PREDICTIONS_PATH = "/v1/predictions";
    private final HttpClientWrapper httpClientWrapper;

    public ReplicatePredictionService(HttpClientWrapper httpClientWrapper) {
        this.httpClientWrapper = httpClientWrapper;
    }

    /**
     * Create a new prediction.
     */
    public <T extends ReplicatePredictionInput> ReplicatePredictionAsyncResponse<T> createPrediction(ReplicatePredictionRequest<T> request) throws IOException {
        return httpClientWrapper.post(
                PREDICTIONS_PATH,
                request,
                ReplicatePredictionAsyncResponse.class
        );
    }

    /**
     * Retrieve an existing prediction by ID.
     */
    public <T extends ReplicatePredictionInput> ReplicatePredictionAsyncResponse<T> getPrediction(String predictionId) throws IOException {
        String path = PREDICTIONS_PATH + "/" + predictionId;
        return httpClientWrapper.get(path, ReplicatePredictionAsyncResponse.class);
    }

    /**
     * List predictions (optionally with pagination).
     */
    public List<ReplicatePredictionAsyncResponse> listPredictions() throws IOException {
        // This method typically returns a list or a paginated wrapper
        // The exact shape of the response from Replicate is:
        // { "results": [ { ... }, { ... } ], "next": "..." }
        // So you may need a small wrapper object. For simplicity:
        PredictionListResponse response = httpClientWrapper.get(PREDICTIONS_PATH, PredictionListResponse.class);
        return response.getResults();
    }

    /**
     * Cancel a prediction by ID.
     */
    public <T extends ReplicatePredictionInput> ReplicatePredictionAsyncResponse<T> cancelPrediction(String predictionId) throws IOException {
        String path = PREDICTIONS_PATH + "/" + predictionId + "/cancel";
        return httpClientWrapper.post(path, null, ReplicatePredictionAsyncResponse.class);
    }

}
