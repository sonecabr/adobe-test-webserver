package com.adobe.test.webserver.http.spec;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ClientHeader {

    private HttpMethod method;
    private String url;
    private String protocolVersion;
    private String accept;
    private String userAgent;
    private String host;
    private String contentLength;
    private String contentType;
    private ClientFormData formData;
    private String connection;
    private String acceptLanguage;
    private String cacheControl;



}
