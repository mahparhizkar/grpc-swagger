package io.grpc.grpcswagger.openapi.v2;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class SwaggerV2DocumentView2 {

    private final SwaggerV2Documentation2 documentation;
    private final String serviceName;

    public SwaggerV2DocumentView2(String serviceName, SwaggerV2Documentation2 documentation) {
        this.documentation = documentation;
        this.serviceName = serviceName;
    }

    public String getSwagger() {
        return documentation.getSwagger();
    }

    public InfoObject getInfo() {
        return documentation.getInfo();
    }

    public List<String> getProduces() {
        return documentation.getProduces();
    }

    public List<String> getConsumes() {
        return documentation.getConsumes();
    }

    public String getBasePath() {
        return documentation.getBasePath();
    }

    public String getHost() {
        return documentation.getHost();
    }

    public List<String> getSchemes() {
        return documentation.getSchemes();
    }

    public Map<String, DefinitionType> getDefinitions() {
        return documentation.getDefinitions();
    }
    
    public Map<String, PathItem> getPaths() {
        if (StringUtils.isBlank(serviceName)) {
            return documentation.getPaths();
        }
        return documentation.getPaths().entrySet().stream()
                .filter(e -> e.getKey().contains(serviceName + "."))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
