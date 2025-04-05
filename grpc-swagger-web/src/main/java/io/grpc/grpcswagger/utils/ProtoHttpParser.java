package io.grpc.grpcswagger.utils;

import io.grpc.grpcswagger.model.GrpcMethod;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProtoHttpParser {
    public static Map<String, GrpcMethod> extractHttpMappings(Path protoPath) {
        Map<String, GrpcMethod> httpMappings = new HashMap<>();

        try {
            List<String> lines = Files.readAllLines(protoPath);
            String currentMethod = null;
            String currentRequest = null;
            String currentResponse = null;
            String httpMethod = null;
            String url = null;

            Pattern methodPattern = Pattern.compile("^rpc\\s+(\\w+)\\s*\\(([^)]+)\\)\\s*returns\\s*\\(([^)]+)\\)");
            Pattern httpPattern = Pattern.compile("(get|post|put|patch|delete)\\s*:\\s*\"([^\"]+)\"");

            for (String line : lines) {
                line = line.trim();

                Matcher methodMatcher = methodPattern.matcher(line);
                if (methodMatcher.find()) {
                    currentMethod = methodMatcher.group(1);
                    currentRequest = methodMatcher.group(2).trim();
                    currentResponse = methodMatcher.group(3).trim();
                } else {
                    Matcher httpMatcher = httpPattern.matcher(line);
                    if (httpMatcher.find()) {
                        httpMethod = httpMatcher.group(1).toUpperCase();
                        url = httpMatcher.group(2);

                        if (currentMethod != null) {
                            httpMappings.put(currentMethod, new GrpcMethod(
                                    currentMethod, httpMethod, url, currentRequest, currentResponse
                            ));

                            // Reset variables for the next method
                            currentMethod = null;
                            currentRequest = null;
                            currentResponse = null;
                            httpMethod = null;
                            url = null;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return httpMappings;
    }
}
