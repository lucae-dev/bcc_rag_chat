package bck_instapic.replicate.client.response;

import bck_instapic.replicate.client.request.ReplicatePredictionInput;

import java.util.List;

public class PredictionListResponse<T extends ReplicatePredictionInput> {
    private List<ReplicatePredictionAsyncResponse<T>> results;
    // Possibly pagination fields like next, previous, etc.

    public List<ReplicatePredictionAsyncResponse<T>> getResults() {
        return results;
    }

    public void setResults(List<ReplicatePredictionAsyncResponse<T>> results) {
        this.results = results;
    }
}
