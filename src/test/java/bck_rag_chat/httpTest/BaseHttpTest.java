package bck_rag_chat.httpTest;

import bck_rag_chat.BaseContainerIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;

public abstract class BaseHttpTest extends BaseContainerIntegrationTest {
    protected WebClient webClient;
    protected String baseUrl;

    @BeforeEach
    public void setUp() {
        this.baseUrl = "http://" + getContainerHost() + ":" + getContainerPort();
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeaders(this::configureDefaultHeaders)
                .filter(logRequest())
                .build();
    }

    protected <T> Mono<ResponseEntity<T>> getEntity(String path, Class<T> responseType, HttpHeaders headers) {
        return webClient.get()
                .uri(path)
                .headers(httpHeaders -> addAllHeaders(headers, httpHeaders))
                .exchangeToMono(response -> response.toEntity(responseType));
    }

    protected <T, R> Mono<ResponseEntity<R>> postEntity(String path, T body, Class<R> responseType, HttpHeaders headers) {
        return webClient.post()
                .uri(path)
                .headers(httpHeaders -> addAllHeaders(headers, httpHeaders))
                .bodyValue(body)
                .exchangeToMono(response -> response.toEntity(responseType));
    }

    protected <T> Mono<ResponseEntity<Void>> bodilessPostEntity(String path, HttpHeaders headers) {
        return webClient.post()
                .uri(path)
                .headers(httpHeaders -> addAllHeaders(headers, httpHeaders))
                .exchangeToMono(ClientResponse::toBodilessEntity);
    }

    protected <T, R> Mono<ResponseEntity<R>> putEntity(String path, T body, Class<R> responseType, HttpHeaders headers) {
        return webClient.put()
                .uri(path)
                .headers(httpHeaders -> addAllHeaders(headers, httpHeaders))
                .bodyValue(body)
                .exchangeToMono(response -> response.toEntity(responseType));
    }

    protected Mono<ResponseEntity<Void>> deleteEntity(String path, HttpHeaders headers) {
        return webClient.delete()
                .uri(path)
                .headers(httpHeaders -> addAllHeaders(headers, httpHeaders))
                .exchangeToMono(response -> response.toEntity(Void.class));
    }

    protected <T> Mono<T> getMono(String path, Class<T> responseType, HttpHeaders headers) {
        return webClient.get()
                .uri(path)
                .headers(httpHeaders -> addAllHeaders(headers, httpHeaders))
                .retrieve()
                .bodyToMono(responseType);
    }

    protected <T> Flux<T> getFlux(String path, Class<T> responseType, HttpHeaders headers) {
        return webClient.get()
                .uri(path)
                .headers(httpHeaders -> addAllHeaders(headers, httpHeaders))
                .retrieve()
                .bodyToFlux(responseType);
    }

    protected <T, R> Mono<R> postMono(String path, T body, Class<R> responseType, HttpHeaders headers) {
        return webClient.post()
                .uri(path)
                .headers(httpHeaders -> addAllHeaders(headers, httpHeaders))
                .bodyValue(body)
                .retrieve()
                .bodyToMono(responseType);
    }

    protected <T, R> Mono<R> putMono(String path, T body, Class<R> responseType, HttpHeaders headers) {
        return webClient.put()
                .uri(path)
                .headers(httpHeaders -> addAllHeaders(headers, httpHeaders))
                .bodyValue(body)
                .retrieve()
                .bodyToMono(responseType);
    }

    protected <T> T get(String path, Class<T> responseType, HttpHeaders headers) {
        return getMono(path, responseType, headers).block();
    }

    protected <T> ResponseEntity<T> getResponseEntity(String path, Class<T> responseType, HttpHeaders headers) {
        return getEntity(path, responseType, headers).block();
    }

    protected <T> List<T> getAsList(String path, Class<T> responseType, HttpHeaders headers) {
        return getFlux(path, responseType, headers).collectList().block();
    }

    protected <T, R> R post(String path, T body, Class<R> responseType, HttpHeaders headers) {
        return postMono(path, body, responseType, headers).block();
    }

    protected <T, R> ResponseEntity<R> postResponseEntity(String path, T body, Class<R> responseType, HttpHeaders headers) {
        return postEntity(path, body, responseType, headers).block();
    }

    protected <T, R> R put(String path, T body, Class<R> responseType, HttpHeaders headers) {
        return putMono(path, body, responseType, headers).block();
    }

    protected <T, R> ResponseEntity<R> putResponseEntity(String path, T body, Class<R> responseType, HttpHeaders headers) {
        return putEntity(path, body, responseType, headers).block();
    }

    protected ResponseEntity<Void> delete(String path, HttpHeaders headers) {
        return deleteEntity(path, headers).block();
    }

    protected ResponseEntity<Void> bodilessPost(String path, HttpHeaders headers) {
        return bodilessPostEntity(path, headers).block();
    }

    protected HttpHeaders createHeaders(String... headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        for (int i = 0; i < headers.length - 1; i += 2) {
            httpHeaders.add(headers[i], headers[i + 1]);
        }
        return httpHeaders;
    }

    private void configureDefaultHeaders(HttpHeaders headers) {
    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            System.out.println("Request: " + clientRequest.method() + " " + clientRequest.url());
            clientRequest.headers().forEach((name, values) ->
                    values.forEach(value -> System.out.println(name + ": " + value))
            );
            return Mono.just(clientRequest);
        });
    }

    private static void addAllHeaders(HttpHeaders headersToAdd, HttpHeaders httpHeaders) {
        Optional.ofNullable(headersToAdd)
                .ifPresent(httpHeaders::addAll);
    }
}
