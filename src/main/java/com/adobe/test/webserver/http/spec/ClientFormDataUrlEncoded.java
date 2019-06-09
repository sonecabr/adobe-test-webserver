package com.adobe.test.webserver.http.spec;

import lombok.Builder;
import lombok.Value;

import java.util.Map;

/**
 * Url encoded type definition
 *
 * @author Andre Rocha
 * @since 2019-06-09
 */
@Builder
@Value
public class ClientFormDataUrlEncoded implements ClientFormData {

    private Map<String, String> data;

    private String raw;
}
