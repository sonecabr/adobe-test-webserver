# adobe-test-webserver
adobe-test-webserver

# dependencies
 - Gradle 5.4.1
 - OpenJdk8
 - Docker (if you want to run on Docker)
 - Kubernetes Cluster + Kubectl + Helm (If you want to run on K8s)



# Build
To build jar artifact
```
gradle clean compileJava fatJar
```
To build docker container
```
docker built -t [yourepository] .
```

# Run
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

# Third party software credits
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
      