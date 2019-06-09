package com.adobe.test.webserver.http.handler.httpNotAllowed;

import com.adobe.test.webserver.http.handler.HttpOPTIONSHandler;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
public class HttpNotAllowedOPTIONSHandler extends BaseHttpNotAllowedHandler implements HttpOPTIONSHandler {}