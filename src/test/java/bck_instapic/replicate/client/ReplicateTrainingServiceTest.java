package bck_instapic.replicate.client;

import bck_instapic.BaseTest;
import bck_instapic.core.HttpClientWrapper;
import bck_instapic.replicate.client.request.CreateTrainingRequest;
import bck_instapic.replicate.client.response.ReplicateTraining;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ReplicateTrainingServiceTest extends BaseTest {
    @Mock
    private HttpClientWrapper httpClientWrapper;

    private ReplicateTrainingService replicateTrainingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        replicateTrainingService = new ReplicateTrainingService(httpClientWrapper);
    }

    @Test
    void createTrainingShouldReturnNewlyCreatedTraining() throws IOException {
        // Given
        String owner = "someOwner";
        String modelName = "someModel";
        String versionId = "someVersion";
        CreateTrainingRequest mockRequest = new CreateTrainingRequest();
        ReplicateTraining expectedTraining = new ReplicateTraining();

        String expectedUrl = String.format("/v1/models/%s/%s/versions/%s/trainings", owner, modelName, versionId);
        when(httpClientWrapper.post(
                eq(expectedUrl),
                eq(mockRequest),
                eq(ReplicateTraining.class))
        ).thenReturn(expectedTraining);

        // When
        ReplicateTraining actualTraining =
                replicateTrainingService.createTraining(owner, modelName, versionId, mockRequest);

        // Then
        assertEquals(expectedTraining, actualTraining);
        verify(httpClientWrapper).post(
                eq(expectedUrl),
                eq(mockRequest),
                eq(ReplicateTraining.class)
        );
    }

    @Test
    void getTrainingShouldReturnExistingTraining() throws IOException {
        // Given
        String trainingId = "training123";
        ReplicateTraining expectedTraining = new ReplicateTraining();
        String expectedPath = "/v1/trainings/" + trainingId;

        when(httpClientWrapper.get(
                eq(expectedPath),
                eq(ReplicateTraining.class))
        ).thenReturn(expectedTraining);

        // When
        ReplicateTraining actualTraining =
                replicateTrainingService.getTraining(trainingId);

        // Then
        assertEquals(expectedTraining, actualTraining);
        verify(httpClientWrapper).get(
                eq(expectedPath),
                eq(ReplicateTraining.class)
        );
    }

    @Test
    void cancelTrainingShouldReturnCanceledTraining() throws IOException {
        // Given
        String trainingId = "trainingToCancel";
        String expectedPath = "/v1/trainings/" + trainingId + "/cancel";
        ReplicateTraining expectedTraining = new ReplicateTraining();

        when(httpClientWrapper.post(
                eq(expectedPath),
                eq(null),
                eq(ReplicateTraining.class))
        ).thenReturn(expectedTraining);

        // When
        ReplicateTraining actualTraining =
                replicateTrainingService.cancelTraining(trainingId);

        // Then
        assertEquals(expectedTraining, actualTraining);
        verify(httpClientWrapper).post(
                eq(expectedPath),
                eq(null),
                eq(ReplicateTraining.class)
        );
    }
}