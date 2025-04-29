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

## Configuration

### Required Environment Variables

The service requires the following environment variables to be set:

```bash
# MongoDB Configuration
spring.data.mongodb.uri=mongodb://banku:secret@localhost:27017/banku-engine?authSource=admin

# Kafka Configuration
spring.kafka.bootstrap-servers=localhost:9092

# JWT Configuration
jwt.secret=2a1cf8399b4951d738e9b62c63b11c867f7c4e471cb108c1e7b4a4377e5d7a4f

# OpenAI Configuration
openai.api-key=your-openai-api-key
openai.model=gpt-4
openai.prompts.alert=Analyze this transaction for potential fraud or unusual activity
openai.prompts.recommendation=Provide personalized financial recommendations based on spending patterns
```

### Local Development Setup

1. Create a `application-local.properties` file in the project root with the required environment variables
2. Start the required services using Docker Compose:
   ```bash
   docker-compose up -d
   ```
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

The service will be available at `http://localhost:8083`

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

## API Documentation

The service provides Swagger UI for API documentation at:
- Swagger UI: `http://localhost:8083/api/v1/engine/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8083/api/v1/engine/v3/api-docs`

## Development

### Requirements

- Java 17
- Docker
- Docker Compose
- OpenAI API key

### Local Execution

1. Clone the repository
2. Configure OpenAI API credentials in `application-local.properties`
3. Run `docker-compose up -d` to start MongoDB and Kafka
4. Run the application with `./mvnw spring-boot:run`

### Tests

Run tests with:
```bash
./mvnw test
```

## License

This project is private and confidential.
