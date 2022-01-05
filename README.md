# SpringBoot Integration-Test sample 🐳

Kotlin(1.6.10) + Spring Boot 2.6.2 + Testcontainers(PostgreSQL) + MyBatis + DbUnit + 
Spring Security + JWT auth + Flyway  
(prototype for myself)

# Features

- I wrote this for myself because there was not enough good sample code for 
testing SpringBoot using Testcontainers.🐳 
- The implementation focuses on how to use TestContainer in SpringBoot tests, 
and the rest is not implemented tightly. (e.g. Mapper called in RestController)
- Postgres container is reused. (Booting once)
- As an added bonus, the user authentication uses JWT (JSON Web Token), 
and we created a sample Spring Security Test.

```kotlin
datasource.apply {
    url = container.jdbcUrl
    username = container.username
    password = container.password
    setDriverClassName(container.driverClassName)
}
```
There are several ways to implement this way of overwriting Properties, but I felt it was too cumbersome, so I decided to implement the datasource generation.
```kotlin
System.setProperty("DB_URL", container.jdbcUrl)
System.setProperty("DB_USERNAME", container.username)
System.setProperty("DB_PASSWORD", container.password)
```
# Requirement

* Java openjdk 17.0.1
* Docker daemon

# Author

* kodacme 
* Software engineer 
* kodac.saito@kodac.me
