package io.grpc.grpcswagger.model;

import lombok.Data;

@Data
public class GrpcMethod {
    private String name;
    private String type;
    private String url;
    private String request;
    private String response;

    public GrpcMethod(String name, String type, String url, String request, String response) {
        this.name = name;
        this.type = type;
        this.url = url;
        this.request = request;
        this.response = response;
    }

    @Override
    public String toString() {
        return "name = " + name + "\n" +
               "type = " + type + "\n" +
               "url = " + url + "\n" +
               "request = " + request + "\n" +
               "response = " + response + "\n";
    }
}

