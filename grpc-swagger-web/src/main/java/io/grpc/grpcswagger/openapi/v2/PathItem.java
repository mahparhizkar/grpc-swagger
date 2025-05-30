package io.grpc.grpcswagger.openapi.v2;

import lombok.Data;

/**
 * @author liuzhengyang
 */
@Data
public class PathItem {
    private Operation get;
    private Operation post;
    private Operation put;
    private Operation delete;
}
