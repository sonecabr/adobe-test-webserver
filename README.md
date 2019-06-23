# Problem to solve :: Java basic file based WebServer

## Problem Description:
A multi-threaded (e.g. file-based) web server with thread-pooling implemented in Java.
Extension: Add proper HTTP/1.1 keep-alive behavior to your implementation based on the http-client's
capabilities exposed through its request headers.

## Current version: 
 - 1.0.0

## Features available
 - Http 1.0
 - Http 1.1
 - Serve Html files on ___GET___ requests
 - Serve Html files on ___POST___ requests (limited to application/x-www-form-urlencoded)
 - Check capabilities via ___OPTIONS___ request (limited)
 - Serve ___404___ page in case of invalid route
 - Serve keep-alive behavior for Http 1.1 clients (when Connection=keep-alive header is provided)
 - Serve Unsupported Method fallback for no implemented methods

## Features I planned to add (If I had enough time) 
 - Add dynamic cache to avoid unecessary IO on filesystem
 - Add Methods missed (DELETE, PATH, HEAD, POST(all conten-types))
 - Add suport to all Content-Types
 - Add support to configurable routes (via config file)
 - Add support to configurable Cross Origin Requests
 - Add support to extend the server (similar to SpringBoot + Jetty|Tomcat|undertow approach)
 - Add suport to Rest (based on JAXRS)
 - Add suport to template rendering (like SpringBoot thymeleaf, Jstl, velocity)
 - Add Authentication support (Basic, Bearer Token, Oauth1|2, Digest)
 - Improve connection management (not only based on Threads, but also by implementing Reative pattern)
 - Add support to 3rd party plugins
 - Add suport to define log level
 - Add suport to log patterns (like apache_combined)
 - Add DOS and or QoS implementation

## Dependencies
 - Gradle 5.4.1
 - OpenJdk8
 - Lombok plugin (Idea, Eclipse), if you want to import the project into IDE
 - Docker (if you want to run on Docker) 18+
 - Docker Compose (if you want to run on Docker trough docker-compose command) 1.23+
 - Kubernetes Cluster + Kubectl + Helm (If you want to run on K8s)

## Project structure
- src/main/java: Java implementation
- src/test/java: Java Unit tests
- src/main/resources: Java resources
- src/test/main/resources: Java Unit test resources
- devops/docker: docker resources
- devops/k8s: Kubernetes helm recipes

## Project structure (Java relevant implementation)
- __com.adobe.test.webserver__: runtime classes
  - __WebServerApplication__: main class
  - __WebServerRequestHandler__: Default http dispatcher and spec
- __com.adobe.test.webserver.http.handler__: http1.0 and http1.1 dispatchers and spec
- __com.adobe.test.webserver.io__: IO implementation (file readers) and spec
- __com.adobe.test.webserver.server__: Server configs and spec
   

## Build
To build jar artifact
```
gradle clean compileJava fatJar
```
To build docker container
```
docker built -t [yourepository] .
```

## Test automation
```
gradle test
```
* Its possible to check the test report by acessing the html page bellow after run:
 - file:///__$PROJECT_HOME__/build/reports/tests/test/index.html


## Run
With gradle
```
gradle webserverRun
```

With jar
```
java -jar build/libs/adobe-test-webserver-all-1.0.jar
```

With Docker
```
docker run --rm --name adobe-test-webserver \ 
-e server_threadlimit=4 \
-e server_port=8080 \
-e server_webroot="/var/www" \
-e server_defaultfiles="404.html" \
-e server_backenderrorfile="500.html" \
-e server_charset="UTF-8" \
-e server_servername="Adobe Test WebServer 0.1-SNAPSHOT" \
-e server_keepaliveactive=true \
-p 8080:8080 -d [image-id or yourrepo]
```


With Docker Compose
```
docker-compose up -d
```

With Kubernetes
```
helm install --namespace [yournamespace] --name adobe-test-webserver devops/kubernetes/adobe-test-webserver -f devops/kubernetes/adobe-test-webserver/values.yaml
```

## Usage
- Place your html files in the folder of your preference
- Configure environment `server_webroot` or change the variable `server_webroot` in the file src/main/resources/default.conf to bound to your folder
- Start the server (if you change the config file, you need to start by gradle or compiledJar)
- Access in your browser http://localhost:8080/{file.html}
* in the folder src/main/resources/www you can get example files

## JavaDoc
 - JavaDoc does not support Lombok plugin, so before create docs, you need to run delombok to create proper source reference
  ```
  java -jar lombok.jar delombok src -d src-delomboked
  gradle javadoc
  ```

## Third party software credits
  - Page 404 Design and Style
    Publisher: https://freefrontend.com/html-css-404-page-templates/
    Author: Jon Kantner
    link: https://codepen.io/jkantner/pen/aPLWJm
    
  - Base helm recipe was created with `helm create` tool
  
  - Resource variables was loaded by TypeSafe
    Author: lightbend
    License: Apache-2.0
    url: https://github.com/lightbend/config

  - Logs provided by slf4j:
    Author: Slf4J.org
    License: MIT
    url: https://www.slf4j.org/
  - Code of conduct was based/copy of Adobe Brackets code of conduct 
    https://raw.githubusercontent.com/adobe/brackets/master/CODE_OF_CONDUCT.md
      
## Author
 - Andre Rocha <devel.andrerocha@gmail.com>
 - https://github.com/sonecabr
 - https://github.com/sonecabr/adobe-test-webserver __[private] * ask me for access__
