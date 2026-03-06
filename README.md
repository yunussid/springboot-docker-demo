# Docker Deployment: Spring Boot + MySQL (Two Servers)

## Docker Flags Explained

```
-d    # Run container in detached mode (background)
-t    # Tag the image with a name
-p    # Map host port to container port
-e    # Set environment variable
--name    # Assign a name to the container
--network    # Connect container to a network
```

## Step 0: Create Docker Network (Run on both servers or single server)

```
Step 1: docker network create spring-mysql-network   # Create a bridge network

Step 2: docker network ls                            # List all networks
```

## Server 1: MySQL Server

```
Step 1: docker pull mysql:8.0                  # Pull MySQL image

Step 2: docker images                          # List all images

Step 3: docker run -d --name mysql-container --network spring-mysql-network -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=demodb -e MYSQL_USER=admin -e MYSQL_PASSWORD=admin123 mysql:8.0   # Run MySQL container

Step 4: docker ps                              # Check MySQL is running

Step 5: docker logs -f mysql-container         # View MySQL logs

Step 6: docker exec -it mysql-container mysql -u root -p   # Connect to MySQL (enter password: root)
```

## MySQL Commands (Inside MySQL Shell)

```
SHOW DATABASES;                    # List all databases
USE demodb;                        # Switch to demodb database
SHOW TABLES;                       # List all tables
SELECT * FROM <table_name>;        # View table data
EXIT;                              # Exit MySQL shell
```

## MySQL Credentials

```
Root User: root
Root Password: root
Database: demodb
App User: admin
App Password: admin123
```

## Server 2: Spring Boot Application Server

```
Step 1: mvn clean package -DskipTests          # Build the JAR file

Step 2: docker build -t springboot-docker-demo .   # Create Docker image

Step 3: docker images                          # List all images

Step 4: docker run -d --name springboot-app --network spring-mysql-network -p 8080:8080 -e SPRING_DATASOURCE_URL=jdbc:mysql://mysql-container:3306/demodb -e SPRING_DATASOURCE_USERNAME=admin -e SPRING_DATASOURCE_PASSWORD=admin123 springboot-docker-demo   # Run Spring Boot container

Step 5: docker ps                              # Check container is running

Step 6: docker logs -f springboot-app          # View application logs
```

## application.properties (Spring Boot)

```
spring.datasource.url=jdbc:mysql://mysql-container:3306/demodb
spring.datasource.username=admin
spring.datasource.password=admin123
spring.jpa.hibernate.ddl-auto=update
```

## Connection Flow

```
[Spring Boot App] ---> spring-mysql-network ---> [MySQL Container]
   (Port 8080)           (Bridge Network)           (Port 3306)
```

## Docker Network Commands

```
docker network create spring-mysql-network    # Create network
docker network ls                             # List all networks
docker network inspect spring-mysql-network   # View network details
docker network rm spring-mysql-network        # Remove network
```

## Push to Docker Hub

```
Step 1: docker login                           # Login to Docker Hub

Step 2: docker tag springboot-docker-demo <dockerhub_username>/springboot-docker-demo   # Tag the image

Step 3: docker push <dockerhub_username>/springboot-docker-demo   # Push to Docker Hub

Step 4: docker pull <dockerhub_username>/springboot-docker-demo   # Pull from Docker Hub
```

## Important Notes

```
1. Both containers must be on same network (spring-mysql-network) to communicate
2. Use container name (mysql-container) instead of IP when on same network
3. Replace <MYSQL_SERVER_IP> with actual IP if containers are on different servers
4. Ensure port 3306 is open on MySQL server firewall
5. Ensure port 8080 is open on Spring Boot server firewall
```

