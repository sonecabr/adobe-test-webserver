package com.adobe.test.webserver.http.handler.httpNotAllowed;

import com.adobe.test.webserver.http.handler.HttpGETHandler;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
public class HttpNotAllowedGETHandler extends BaseHttpNotAllowedHandler implements HttpGETHandler  {}
