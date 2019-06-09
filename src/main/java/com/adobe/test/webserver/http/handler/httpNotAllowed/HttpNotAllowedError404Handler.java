package com.adobe.test.webserver.http.handler.httpNotAllowed;

import com.adobe.test.webserver.http.handler.HttpError404Handler;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

/**
 * Http client not supported (fallback)
 * <ul>
 *     <li>manage requests for 404 Page not found method</li>
 *     <li>threat all headers from client side</li>
 *     <li>write headers and body (html)</li>
 *     <li>configure response headers and code</li>
 * </ul>
 *
 *
 * @author Andre Rocha <devel.andrerocha@gmail.com>
 * @since 2019-06-09
 */
@Builder
@Slf4j
public class HttpNotAllowedError404Handler extends BaseHttpNotAllowedHandler implements HttpError404Handler {}
