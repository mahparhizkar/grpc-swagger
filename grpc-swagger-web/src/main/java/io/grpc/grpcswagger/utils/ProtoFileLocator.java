package io.grpc.grpcswagger.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class ProtoFileLocator {
    private static final String PROTO_BASE_PATH = "grpc-swagger-web/src/main/proto/protobuf/insurflex";

    public static Optional<Path> findProtoForService(String serviceName) {
        try (Stream<Path> paths = Files.walk(Paths.get(PROTO_BASE_PATH).toAbsolutePath())) {
            return paths.filter(path -> path.toString().endsWith(".proto"))
                        .filter(path -> containsServiceDefinition(path, serviceName))
                        .findFirst();
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private static boolean containsServiceDefinition(Path protoFile, String serviceName) {
        try {
            List<String> lines = Files.readAllLines(protoFile);
            return lines.stream().anyMatch(line -> line.trim().matches("service\\s+" + serviceName + "\\s*\\{"));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
