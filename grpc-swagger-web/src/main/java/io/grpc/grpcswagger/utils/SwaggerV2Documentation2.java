package io.grpc.grpcswagger.utils;

import io.grpc.grpcswagger.model.GrpcMethod;

import java.util.ArrayList;
import java.util.List;

public class SwaggerV2Documentation2 {
    private List<ApiEndpoint> endpoints = new ArrayList<>();

    public void addEndpoint(String method, String url, GrpcMethod grpcMethod) {
        endpoints.add(new ApiEndpoint(method, url, grpcMethod));
    }

    public List<ApiEndpoint> getEndpoints() {
        return endpoints;
    }
}

