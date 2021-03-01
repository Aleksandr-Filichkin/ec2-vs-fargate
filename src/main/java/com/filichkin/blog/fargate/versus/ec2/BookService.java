package com.filichkin.blog.fargate.versus.ec2;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.http.nio.netty.NettyNioAsyncHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.util.Map;

@Service
public class BookService {
    private static final String TABLE_NAME = "books";
    private static final String BOOK = "book";
    private static final String ID = "id";
    private DynamoDbAsyncClient client;

    @PostConstruct
    void init() {
        client = DynamoDbAsyncClient
                .builder()
                .httpClient(NettyNioAsyncHttpClient.builder().maxConcurrency(1000).build()) //default max concurrency is 50, which is not enough
                .region(Region.US_EAST_1)
                .build();

    }

    public Mono<String> saveBook(String id, String book) {
        PutItemRequest request = PutItemRequest.builder()
                .item(Map.of(ID, AttributeValue.builder().s(id).build(),
                        BOOK, AttributeValue.builder().s(book).build()))
                .tableName(TABLE_NAME)
                .build();

        return Mono.fromFuture(client.putItem(request).thenApply(ignore -> id));

    }

    public Mono<String> getBook(String id) {
        GetItemRequest request = GetItemRequest.builder()
                .key(Map.of(ID, AttributeValue.builder()
                        .s(id).build()))
                .tableName(TABLE_NAME)
                .build();
        return Mono.fromFuture(client.getItem(request)
                .thenApply(getItemResponse ->
                        getItemResponse.item().getOrDefault(BOOK, AttributeValue.builder().build()).s())
        );

    }
}
