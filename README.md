# fiap-hc-scheduling-api

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=tech-challenge-fiap-5soat_fiap-hc-scheduling-api&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=tech-challenge-fiap-5soat_fiap-hc-scheduling-api)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=tech-challenge-fiap-5soat_fiap-hc-scheduling-api&metric=coverage)](https://sonarcloud.io/summary/new_code?id=tech-challenge-fiap-5soat_fiap-hc-scheduling-api)

Scheduling API is a system designed to manage doctors' schedules, providing available times for each day and the possibility of creating a schedule with these available slots.

This service is responsible for

- Create doctor Schedules
- List available doctor schedules
- Create a schedule appointment
- Validate endpoints with authorization token with role patient/Doctor


----
### Architecture

![hackathon drawio](https://github.com/user-attachments/assets/4a0aee84-0454-46f7-b77d-5417feb23015)

## Technology stack

This API was built using [Java](https://www.java.com/) and several tools:
- [Spring Boot](https://spring.io/projects/spring-boot) - Framework for creating stand-alone, production-grade Spring-based Applications
- [Maven](https://maven.apache.org/) - Dependency management and build automation tool
- [PostgreSQL](https://www.postgresql.org/) - Open-source relational database
- [Springdoc OpenAPI UI](https://springdoc.org/) - API documentation tool for Spring Boot projects
- [JUnit](https://junit.org/junit5/) - Testing framework for Java
- [Mockito](https://site.mockito.org/) - Mocking framework for unit tests
- [MapStruct](https://mapstruct.org/) - Code generator for bean mappings
- [Jackson](https://github.com/FasterXML/jackson) - JSON parser for Java
- [Amazon Cognito](https://aws.amazon.com/cognito/) - Access user management
- [Feign](https://github.com/OpenFeign/feign) - Java to HTTP client binder
- [Spring Security](https://spring.io/projects/spring-security) - Powerful and highly customizable security framework
- [Lombok](https://projectlombok.org/) - Library to reduce boilerplate code
- [Docker](https://www.docker.com/) - Platform for developing, shipping, and running applications in containers 
------

## Endpoints

### 1. Available doctor schedules

**GET** /doctors/{doctorId}/available-schedules

**Response**: `200 OK` with the available schedules

**Response Body**:

```json
{
    "availableSchedules": [
        {
            "doctorId": 1,
            "doctorScheduleId": 1,
            "scheduleDate": "2024-01-01",
            "scheduleDateStartTime": "09:00",
            "scheduleDateEndTime": "09:30"
        },
        {
            "doctorId": 1,
            "doctorScheduleId": 2,
            "scheduleDate": "2024-01-01",
            "scheduleDateStartTime": "09:30",
            "scheduleDateEndTime": "10:00"
        }
    ]
}
```

### 2. Create a schedule appointment

**POST** /appointments

**Request**

```json
{
    "patientId": 1,
    "doctorId": 1,
    "doctorScheduleId": 1,
    "schedulingDate": "2024-01-01T09:00:00"
}
```

**Response**: `200 OK` with the id of the created appointment

```json
{
    "id": 1
}
```

### 3. Create a doctor schedule

**POST** /doctors/{doctorId}

**Request**

```json
[
    {
        "doctorId": 1,
        "date": "2024-01-01",
        "startTime": "09:00",
        "endTime": "09:30"
    },
    {
        "doctorId": 1,
        "date": "2024-01-01",
        "startTime": "09:00",
        "endTime": "09:30"
    }
]
```

**Response**: `200 OK` without body

