# Customer API

Simple Spring Boot + Kotlin REST API for customer management in a hotel booking context.

## Features
- Create customers (`POST /customers`)
- List customers (`GET /customers`)
- Delete customers (`DELETE /customers/{id}`)
- Request validation with clear API errors

## Requirements
- Java 11+
- MySQL (for local runtime)

## Configuration
Configuration is environment-aware and can be overridden using environment variables:

- `DB_URL` (default: `jdbc:mysql://localhost:3306/my_db`)
- `DB_USERNAME` (default: `root`)
- `DB_PASSWORD` (default: `root1234`)
- `SERVER_PORT` (default: `8090`)

## Run
```bash
./gradlew bootRun
```

## Test
```bash
./gradlew test
```

## Example requests
Create:
```bash
curl -X POST http://localhost:8090/customers \
  -H 'Content-Type: application/json' \
  -d '{"name":"Alice","phno":"1234567890","city":"Pune"}'
```

List:
```bash
curl http://localhost:8090/customers
```

Delete:
```bash
curl -X DELETE http://localhost:8090/customers/1
```
