package bck_instapic.replicate.client;

import bck_instapic.BaseTest;
import bck_instapic.core.HttpClientWrapper;
import bck_instapic.replicate.client.request.ReplicatePredictionInput;
import bck_instapic.replicate.client.request.ReplicatePredictionRequest;
import bck_instapic.replicate.client.request.ReplicatePredictionRequestBuilder;
import bck_instapic.replicate.client.response.PredictionListResponse;
import bck_instapic.replicate.client.response.ReplicatePredictionAsyncResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ReplicatePredictionServiceTest extends BaseTest {

    @Mock
    private HttpClientWrapper httpClientWrapper;

    private ReplicatePredictionService replicatePredictionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        replicatePredictionService = new ReplicatePredictionService(httpClientWrapper);
    }

    @Test
    void createPredictionShouldReturnCorrectResponse() throws IOException {
        // Given
        @SuppressWarnings("unchecked")
        ReplicatePredictionRequest<ReplicatePredictionInput> mockRequest =
                new ReplicatePredictionRequestBuilder().build();
        @SuppressWarnings("unchecked")
        ReplicatePredictionAsyncResponse<ReplicatePredictionInput> expectedResponse = new ReplicatePredictionAsyncResponse();

        when(httpClientWrapper.post(
                eq("/v1/predictions"),
                eq(mockRequest),
                eq(ReplicatePredictionAsyncResponse.class))
        ).thenReturn(expectedResponse);

        // When
        ReplicatePredictionAsyncResponse<ReplicatePredictionInput> actualResponse =
                replicatePredictionService.createPrediction(mockRequest);

        // Then
        assertEquals(expectedResponse, actualResponse);
        verify(httpClientWrapper).post(
                eq("/v1/predictions"),
                eq(mockRequest),
                eq(ReplicatePredictionAsyncResponse.class)
        );
    }

    @Test
    void getPredictionShouldReturnRetrievedResponse() throws IOException {
        // Given
        String predictionId = "123";
        String expectedPath = "/v1/predictions/" + predictionId;

        @SuppressWarnings("unchecked")
        ReplicatePredictionAsyncResponse<ReplicatePredictionInput> expectedResponse =
                (ReplicatePredictionAsyncResponse<ReplicatePredictionInput>) new ReplicatePredictionAsyncResponse<>();

        when(httpClientWrapper.get(
                eq(expectedPath),
                eq(ReplicatePredictionAsyncResponse.class))
        ).thenReturn(expectedResponse);

        // When
        ReplicatePredictionAsyncResponse<ReplicatePredictionInput> actualResponse =
                replicatePredictionService.getPrediction(predictionId);

        // Then
        assertEquals(expectedResponse, actualResponse);
        verify(httpClientWrapper).get(
                eq(expectedPath),
                eq(ReplicatePredictionAsyncResponse.class)
        );
    }

    @Test
    void listPredictionsShouldReturnListOfResponses() throws IOException {
        // Given
        PredictionListResponse mockListResponse = new PredictionListResponse();
        ReplicatePredictionAsyncResponse<?> mockPrediction =
                new ReplicatePredictionAsyncResponse<>();
        mockListResponse.setResults(Collections.singletonList(mockPrediction));

        when(httpClientWrapper.get(
                eq("/v1/predictions"),
                eq(PredictionListResponse.class))
        ).thenReturn(mockListResponse);

        // When
        List<ReplicatePredictionAsyncResponse> actualList =
                replicatePredictionService.listPredictions();

        // Then
        assertEquals(1, actualList.size());
        assertEquals(mockPrediction, actualList.get(0));
        verify(httpClientWrapper).get(
                eq("/v1/predictions"),
                eq(PredictionListResponse.class)
        );
    }

    @Test
    void cancelPredictionShouldReturnCanceledResponse() throws IOException {
        // Given
        String predictionId = "456";
        String expectedPath = "/v1/predictions/" + predictionId + "/cancel";

        @SuppressWarnings("unchecked")
        ReplicatePredictionAsyncResponse<ReplicatePredictionInput> expectedResponse =
                (ReplicatePredictionAsyncResponse<ReplicatePredictionInput>) new ReplicatePredictionAsyncResponse<>();

        when(httpClientWrapper.post(
                eq(expectedPath),
                eq(null),
                eq(ReplicatePredictionAsyncResponse.class))
        ).thenReturn(expectedResponse);

        // When
        ReplicatePredictionAsyncResponse<ReplicatePredictionInput> actualResponse =
                replicatePredictionService.cancelPrediction(predictionId);

        // Then
        assertEquals(expectedResponse, actualResponse);
        verify(httpClientWrapper).post(
                eq(expectedPath),
                eq(null),
                eq(ReplicatePredictionAsyncResponse.class)
        );
    }
}