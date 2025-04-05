package io.grpc.grpcswagger.service;

import io.grpc.grpcswagger.model.GrpcMethod;
import io.grpc.grpcswagger.utils.ProtoFileLocator;
import io.grpc.grpcswagger.utils.ProtoHttpParser;
import io.grpc.grpcswagger.utils.SwaggerV2Documentation2;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

@Service
public class DocumentService2 {

    public SwaggerV2Documentation2 getDocumentation(String fullService, String apiHost) {
        String service = fullService.substring(fullService.lastIndexOf('.') + 1);
        Optional<Path> protoFilePath = ProtoFileLocator.findProtoForService(service);
        if (!protoFilePath.isPresent()) {
            throw new RuntimeException("Proto file not found for service: " + service);
        }

        Map<String, GrpcMethod> httpMappings = ProtoHttpParser.extractHttpMappings(protoFilePath.get());
        SwaggerV2Documentation2 documentation = new SwaggerV2Documentation2();
        /*for (GrpcMethod method : httpMappings) {
            String pathMapping = httpMappings.getOrDefault(method.getName(), "POST /" + method.getName());

            // Extract method and URL
            String[] parts = pathMapping.split(" ", 2);
            String httpMethod = parts[0];
            String url = parts[1];

            documentation.addEndpoint(httpMethod, url, method);
        }*/
        return documentation;
    }
}
