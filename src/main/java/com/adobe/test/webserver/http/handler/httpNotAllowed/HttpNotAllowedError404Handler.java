package com.adobe.test.webserver.http.handler.httpNotAllowed;

import com.adobe.test.webserver.http.handler.HttpError404Handler;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Builder
@Slf4j
public class HttpNotAllowedError404Handler extends BaseHttpNotAllowedHandler implements HttpError404Handler {}
