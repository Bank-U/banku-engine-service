# BankU Engine Service

Financial intelligence and analysis service for BankU, implemented with Spring Boot.

## Features

- Financial data analysis and processing
- Transaction categorization
- Spending pattern analysis
- Budget recommendations
- Integration with other BankU services

## Technologies

- Java 17
- Spring Boot 3.4.4
- Spring Data MongoDB
- Spring Kafka
- Docker
- Docker Compose

## Project Structure

```
src/
├── main/
│   └── java/
│       └── com/
│           └── banku/
│               └── engineservice/
│                   ├── config/         # Service configurations
│                   ├── controller/     # REST Controllers
│                   ├── model/          # Data models
│                   ├── repository/     # Data repositories
│                   ├── service/        # Business logic
│                   └── util/           # Utility classes
└── test/                              # Unit and Integration Tests
```

## API Endpoints

### Financial Analysis

- `POST /api/v1/intelligence/analyze`: Analyze financial data
- `GET /api/v1/intelligence/patterns/{userId}`: Get spending patterns
- `GET /api/v1/intelligence/recommendations/{userId}`: Get budget recommendations

## Configuration

### MongoDB

```properties
spring.data.mongodb.uri=mongodb://banku:secret@localhost:27017/banku-engine?authSource=admin
```

### Kafka

```properties
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=banku-engine-group
spring.kafka.consumer.auto-offset-reset=earliest
```

## Development

### Requirements

- Java 17
- Docker
- Docker Compose

### Local Execution

1. Clone the repository
2. Run `docker-compose up -d` to start MongoDB and Kafka
3. Run the application with `./mvnw spring-boot:run`

### Tests

Run tests with:
```bash
./mvnw test
```

## Docker

The service can be run using Docker:

```bash
docker-compose up -d
```

The Docker Compose file includes:
- MongoDB for data storage
- Kafka for event streaming

## API Documentation

The service provides OpenAPI (Swagger) documentation that can be accessed through:

- Direct access: http://localhost:8083/api/v1/engine/swagger-ui/index.html
- Through Gateway: http://localhost:8080/api/v1/engine/swagger-ui/index.html

The API documentation includes detailed information about all endpoints, request/response schemas, and authentication requirements.

## License

This project is private and confidential.
