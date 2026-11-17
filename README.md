# SOA Microservices Containerization

This folder contains the organization’s complete Spring microservices project.
The Docker Compose setup currently containerizes the selected services:

- Eureka Server
- API Gateway
- Auth Service

The source for the other available services, `account-service` and
`book-service`, is also kept in this folder for the complete project.

Each service runs in its own container on the `soa-network` Docker bridge
network. The gateway and auth service use the Docker service name
`eureka-server` instead of `localhost` for service discovery.

## Required project layout

The source and Maven files are already included in this folder. Package the
three containerized services from their service directories:

```text
SOA_End/
├── docker-compose.yml
├── README.md
├── account-service/
├── book-service/
├── eureka-server/
│   ├── Dockerfile
│   ├── pom.xml
│   └── target/<eureka-server-jar>.jar
├── api-gateway/
│   ├── Dockerfile
│   ├── pom.xml
│   └── target/<api-gateway-jar>.jar
└── auth-service/
    ├── Dockerfile
    ├── pom.xml
    └── target/<auth-service-jar>.jar
```

Run this command inside each of `eureka-server`, `api-gateway`, and
`auth-service`:

```powershell
mvn clean package -DskipTests
```

The Maven command creates the JAR in the matching `target` directory.

## Build and run

From this directory, build all images and start the services:

```powershell
docker compose up --build -d
```

The published endpoints are:

| Service | URL |
| --- | --- |
| Eureka Server | http://localhost:8761 |
| Auth Service | http://localhost:8081 |
| API Gateway | http://localhost:8085 |

View logs or stop the environment with:

```powershell
docker compose logs -f
docker compose down
```

The Eureka health check ensures that the gateway and auth service start only
after the discovery server is reachable.
