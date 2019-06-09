package com.adobe.test.webserver.http.spec;

import lombok.Builder;
import lombok.Value;

/**
 * Http Headers available for this server
 *
 * @author Andre Rocha <devel.andrerocha@gmail.com>
 * @since 2019-06-09
 */
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
