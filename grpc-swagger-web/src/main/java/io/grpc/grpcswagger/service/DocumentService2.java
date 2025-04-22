package io.grpc.grpcswagger.service;

import io.grpc.grpcswagger.model.GrpcMethod;
import io.grpc.grpcswagger.openapi.v2.DocumentRegistry;
import io.grpc.grpcswagger.openapi.v2.SwaggerV2Documentation;
import io.grpc.grpcswagger.utils.ProtoFileLocator;
import io.grpc.grpcswagger.utils.ProtoHttpParser;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

@Service
public class DocumentService2 {

    public SwaggerV2Documentation getDocumentation(String fullService, String apiHost, String endpoint) {
        String service = fullService.substring(fullService.lastIndexOf('.') + 1);
        Optional<Path> protoFilePath = ProtoFileLocator.findProtoForService(service);
        if (!protoFilePath.isPresent()) {
            throw new RuntimeException("Proto file not found for service: " + service);
        }
        Map<String, GrpcMethod> httpMappings = ProtoHttpParser.extractHttpMappings(protoFilePath.get());
        SwaggerV2Documentation swaggerV2Documentation = DocumentRegistry.getInstance().get(fullService);

        if (swaggerV2Documentation != null && httpMappings != null) {
            swaggerV2Documentation.setHost(apiHost);
            setDescription(swaggerV2Documentation, httpMappings, fullService);
        }
        return swaggerV2Documentation;
    }

    public void setDescription(SwaggerV2Documentation swaggerV2Documentation, Map<String, GrpcMethod> httpMappings, String fullService) {
        for (String path:httpMappings.keySet()) {
            String fullPath = "/"+fullService+"."+path;
            swaggerV2Documentation.getPaths().get(fullPath).getPost().setDescription(httpMappings.get(path).toString());
        }
    }
}
