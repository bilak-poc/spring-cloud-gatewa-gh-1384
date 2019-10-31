# sample application for [spring-cloud-gateway #1384](https://github.com/spring-cloud/spring-cloud-gateway/issues/1384)


### prerequisites
* jdk 8
* docker
* docker-compose

### prepare
execute `./mvnw clean package -DskipTests` in project root to build maven projects  
execute `docker-compose build` in project root to build docker images  
execute `docker-compose up -d` in project root to start docker containers  

#### ribbon retry simulation
wait while eureka is properly initialized (TODO configure better)  
execute `curl http://localhost:8080/backend-service/fast` you should see current date + `zone:left`  
execute `docker-compose stop backendleft` and it should stop the backend service in left zone  
execute `curl http://localhost:8080/backend-service/fast` currently it fails and it's expected to return current date + `zone:right` immediately   

#### graceful shutdown simulation
wait while eureka is properly initialized  
execute `curl http://localhost:8080/backend-service/fast` you should see current date + `zone:left`
execute `curl http://localhost:8080/backend-service/slow/30` now go to console and execute `docker-compose stop gatewayleft`. It's expected to stop container gracefully and wait while backend service call ends. If possible then also stop accepting new requests while gateway is shutting down. 
