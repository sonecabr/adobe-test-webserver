package com.adobe.test.webserver.http.spec;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ClientHeaders {

    private HttpMethod method;
    private String url;
    private String protocolVersion;

}
