package com.adobe.test.webserver.http.spec;

import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Builder
@Value
public class ClientFormDataUrlEncoded implements ClientFormData {

    private Map<String, String> data;

    private String raw;
}
