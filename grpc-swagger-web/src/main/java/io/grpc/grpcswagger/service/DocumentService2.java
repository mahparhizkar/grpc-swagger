package io.grpc.grpcswagger.service;

import io.grpc.grpcswagger.model.GrpcMethod;
import io.grpc.grpcswagger.openapi.v2.InfoObject;
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
        
        // Set service info
        InfoObject info = InfoObject.builder()
                .title(service + " Service")
                .version("1.0.0")
                .build();
        documentation.setInfo(info);
        documentation.setHost(apiHost);

        // Add all endpoints
        for (Map.Entry<String, GrpcMethod> entry : httpMappings.entrySet()) {
            GrpcMethod grpcMethod = entry.getValue();
            documentation.addEndpoint(grpcMethod.getType(), grpcMethod.getUrl(), grpcMethod);
        }

        return documentation;
    }
}
