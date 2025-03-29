package bck_instapic.replicate.client;

import bck_instapic.BaseTest;
import bck_instapic.core.HttpClientWrapper;
import bck_instapic.replicate.client.request.CreateFluxDevLoraTrainingRequest;
import bck_instapic.replicate.client.request.CreateFluxDevLoraTrainingRequestBuilder;
import bck_instapic.replicate.client.response.ReplicateTraining;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class FluxDevLoraTrainerServiceTest extends BaseTest {

    @Mock
    private HttpClientWrapper httpClientWrapper;

    private FluxDevLoraTrainerService fluxDevLoraTrainerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fluxDevLoraTrainerService = new FluxDevLoraTrainerService(httpClientWrapper);
    }

    @Test
    void givenCreateFluxDevLoraTrainingRequest_whenCalled_thenReturnsCreatedTraining() throws IOException {
        // Given
        CreateFluxDevLoraTrainingRequest request = mock(CreateFluxDevLoraTrainingRequest.class);
        ReplicateTraining expectedTraining = new ReplicateTraining();

        // The URL it should call internally:
        String expectedUrl = "/v1/models/ostris/flux-dev-lora-trainer/versions/"
                + "d995297071a44dcb72244e6c19462111649ec86a9646c32df56daa7f14801944/trainings";

        when(httpClientWrapper.post(
                eq(expectedUrl),
                eq(request),
                eq(ReplicateTraining.class))
        ).thenReturn(expectedTraining);

        // When
        ReplicateTraining actualTraining = fluxDevLoraTrainerService.createFluxDevLoraTraining(request);

        // Then
        assertEquals(expectedTraining, actualTraining);
        verify(httpClientWrapper).post(
                eq(expectedUrl),
                eq(request),
                eq(ReplicateTraining.class)
        );
    }

    @Test
    void givenMinimalParams_whenCreateFluxDevLoraTrainingCalled_thenBuildsRequestAndReturnsCreatedTraining() throws IOException {
        // Given
        String destination = "some-destination";
        String inputImages = "some-images-url";
        String triggerWord = "some-trigger";
        Integer steps = 200;
        Integer loraRank = 16;

        // We will capture the internal request built by the convenience method
        ReplicateTraining expectedTraining = new ReplicateTraining();
        String expectedUrl = "/v1/models/ostris/flux-dev-lora-trainer/versions/"
                + "d995297071a44dcb72244e6c19462111649ec86a9646c32df56daa7f14801944/trainings";

        // We can't directly check the final request object fields (unless we use ArgumentCaptor),
        // but we can confirm that the same request object is passed along and returns the expected training.
        when(httpClientWrapper.post(
                eq(expectedUrl),
                // We use Mockito.eq(...) because the convenience method builds a new request instance
                // with the user-provided params. If you want to test exact field values, consider an ArgumentCaptor.
                any(), // We'll replace `null` with any() or additional matchers if you check fields
                eq(ReplicateTraining.class))
        ).thenReturn(expectedTraining);

        // Because we can't match that newly created request by eq(...) (it’s a new object each time),
        // we can do something like this, if you prefer:
        /*
         when(httpClientWrapper.post(eq(expectedUrl), any(CreateFluxDevLoraTrainingRequest.class), eq(ReplicateTraining.class)))
             .thenReturn(expectedTraining);
        */

        // When
        ReplicateTraining actualTraining = fluxDevLoraTrainerService.createFluxDevLoraTraining(
                destination,
                inputImages,
                triggerWord,
                steps,
                loraRank
        );

        // Then
        assertEquals(expectedTraining, actualTraining);
        verify(httpClientWrapper).post(
                eq(expectedUrl),

                any(),
                eq(ReplicateTraining.class)
        );
    }

    @Test
    void givenTrainingId_whenGetFluxDevLoraTrainingCalled_thenReturnsTraining() throws IOException {
        // Given
        String trainingId = "flux-training-123";
        String expectedPath = "/v1/trainings/" + trainingId;
        ReplicateTraining expectedTraining = new ReplicateTraining();

        when(httpClientWrapper.get(
                eq(expectedPath),
                eq(ReplicateTraining.class))
        ).thenReturn(expectedTraining);

        // When
        ReplicateTraining actualTraining = fluxDevLoraTrainerService.getFluxDevLoraTraining(trainingId);

        // Then
        assertEquals(expectedTraining, actualTraining);
        verify(httpClientWrapper).get(
                eq(expectedPath),
                eq(ReplicateTraining.class)
        );
    }

    @Test
    void givenTrainingId_whenCancelFluxDevLoraTrainingCalled_thenReturnsCanceledTraining() throws IOException {
        // Given
        String trainingId = "flux-training-cancel-456";
        String expectedPath = "/v1/trainings/" + trainingId + "/cancel";
        ReplicateTraining expectedTraining = new ReplicateTraining();

        when(httpClientWrapper.post(
                eq(expectedPath),
                eq(null),
                eq(ReplicateTraining.class))
        ).thenReturn(expectedTraining);

        // When
        ReplicateTraining actualTraining = fluxDevLoraTrainerService.cancelFluxDevLoraTraining(trainingId);

        // Then
        assertEquals(expectedTraining, actualTraining);
        verify(httpClientWrapper).post(
                eq(expectedPath),
                eq(null),
                eq(ReplicateTraining.class)
        );
    }
}