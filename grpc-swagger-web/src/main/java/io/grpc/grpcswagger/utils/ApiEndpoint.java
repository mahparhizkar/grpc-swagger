package io.grpc.grpcswagger.utils;

import io.grpc.grpcswagger.model.GrpcMethod;

public class ApiEndpoint {
    private String httpMethod;
    private String url;
    private GrpcMethod grpcMethod;

    public ApiEndpoint(String httpMethod, String url, GrpcMethod grpcMethod) {
        this.httpMethod = httpMethod;
        this.url = url;
        this.grpcMethod = grpcMethod;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getUrl() {
        return url;
    }

    public GrpcMethod getGrpcMethod() {
        return grpcMethod;
    }
}
