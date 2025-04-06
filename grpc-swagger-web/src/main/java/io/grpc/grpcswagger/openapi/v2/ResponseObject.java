package io.grpc.grpcswagger.openapi.v2;

import lombok.Data;

/**
 * @author liuzhengyang
 */
@Data
public class ResponseObject {
    private String code;
    private ParameterSchema schema;
}
