package io.grpc.grpcswagger.utils;

import io.grpc.grpcswagger.model.GrpcMethod;
import io.grpc.grpcswagger.openapi.v2.*;
import lombok.Data;

import java.util.*;

@Data
public class SwaggerV2Documentation2 {
    private String swagger = "2.0";
    private InfoObject info;
    private List<String> produces = Collections.singletonList("application/json");
    private List<String> consumes = Collections.singletonList("application/json");
    private String basePath = "/";
    private String host;
    private List<String> schemes = Collections.singletonList("http");
    private Map<String, PathItem> paths = new HashMap<>();
    private Map<String, DefinitionType> definitions = new HashMap<>();

    public void addEndpoint(String method, String url, GrpcMethod grpcMethod) {
        PathItem pathItem = paths.computeIfAbsent(url, k -> new PathItem());
        Operation operation = new Operation();
        operation.setDescription(grpcMethod.getName());
        operation.setOperationId(grpcMethod.getName());
        
        // Add request parameters
        List<Parameter> parameters = new ArrayList<>();
        if (method.equals("GET")) {
            // For GET requests, add query parameters
            QueryParameter queryParam = new QueryParameter();
            queryParam.setName("request");
            queryParam.setDescription("Request parameters");
            queryParam.setType("string");
            queryParam.setRequired(true);
            parameters.add(queryParam);
        } else {
            // For POST/PUT requests, add body parameter
            Parameter bodyParam = new Parameter();
            bodyParam.setName("body");
            bodyParam.setDescription("Request body");
            bodyParam.setRequired(true);
            ParameterSchema schema = new ParameterSchema();
            schema.setRef("#/definitions/" + grpcMethod.getRequest());
            bodyParam.setSchema(schema);
            parameters.add(bodyParam);
        }
        operation.setParameters(parameters);

        // Add response
        Map<String, ResponseObject> responses = new HashMap<>();
        ResponseObject response = new ResponseObject();
        ParameterSchema responseSchema = new ParameterSchema();
        responseSchema.setRef("#/definitions/" + grpcMethod.getResponse());
        response.setSchema(responseSchema);
        responses.put("200", response);
        operation.setResponses(responses);

        // Set the operation based on HTTP method
        switch (method.toUpperCase()) {
            case "GET":
                pathItem.setGet(operation);
                break;
            case "POST":
                pathItem.setPost(operation);
                break;
            case "PUT":
                pathItem.setPut(operation);
                break;
            case "DELETE":
                pathItem.setDelete(operation);
                break;
        }
    }
}

