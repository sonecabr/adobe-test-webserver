package com.adobe.test.webserver.http.handler.httpNotAllowed;

import com.adobe.test.webserver.http.handler.HttpGETHandler;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

/**
 * Http client not supported (fallback)
 * <ul>
 *     <li>manage requests for GET method</li>
 *     <li>threat all headers from client side</li>
 *     <li>write headers and body (html)</li>
 *     <li>configure response headers and code</li>
 * </ul>
 *
 *
 * @author Andre Rocha <devel.andrerocha@gmail.com>
 * @since 2019-06-09
 */
@Slf4j
@Builder
public class HttpNotAllowedGETHandler extends BaseHttpNotAllowedHandler implements HttpGETHandler  {}
