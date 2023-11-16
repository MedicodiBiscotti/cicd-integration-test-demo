# Demo project for database integration testing

In particular, this demo focuses on setting up a local development environment with a `dev` and `test` database for a
Spring Boot application. This is a simple REST API.

## Table of contents

- [Usage](#usage)
- [Tech stack](#tech-stack)
- [Rationale](#rationale)
  - [Local test database](#local-test-database)
  - [Dotenv](#dotenv)
  - [CI/CD](#cicd)

## Usage

Setting up the environment variables:

1. Copy `.env.example`.
2. Rename new copy to `.env` (remove `.example`)
3. Fill in the values.

The env vars are:

- `DATABASE_URL`
- `TEST_DATABASE_URL`
- `DATABASE_USER`
- `DATABASE_PASSWORD`

Default values for the database URLs are just to access a localhost database. You can rename the databases if you'd
like.

Running the application:

1. Run `docker-compose up -d` to start the database.
2. Run `./mvnw spring-boot:run` to start the application (or run with your IDE of choice).

The application runs on port 8080.

## Tech stack

- Java 17
- Maven
- Spring Boot
- [spring-dotenv](https://github.com/paulschwarz/spring-dotenv)
- Flyway
- PostgreSQL
- Docker
- Docker Compose
- GitHub Actions

## Rationale

### Local test database

We want to be able to execute integration tests locally as we develop, but we don't want it to use our dev database and
clear data before/after each run. So it should be configured to use a separate database when testing. This is done using
the `application.properties` file in the `test/resources` folder.

Technically, with Spring Boot, we can use `@DataJpaTest` or `@Transactional` to achieve roll back changes, but it's not
foolproof. If we use manual deletion in setup or Flyway to drop the database, it being transactional won't help. It's
also about having the database be in a consistent state for each test run.

The test database is created using an SQL script in `scripts`. This is needed because the database image only allows one
database to be created automatically. Any other initialisation happens through mounted scripts. An alternative is to use
two separate database services in `compose.yml`, but that seems unnecessary when you can just create more
databases/schemas on the same database server.

### Dotenv

Moreover, we want all database credentials to be part of configuration and not hardcoded, and importantly, **secret**.
Following [12 Factor App](https://12factor.net/config) principles, we want to be store this in the environment. However,
configuring env vars in Java is annoying as you have to set it up on the machine. It's much easier to use a `.env` file.

That's why we use [spring-dotenv](https://github.com/paulschwarz/spring-dotenv), a wrapper
around [dotenv-java](https://github.com/cdimascio/dotenv-java) that automatically loads `.env` files and configures a
Spring `PropertySource`. If the `.env` file is not there, no worries, it'll ignore it, and we can use normal env vars to
override the values as well.

An alternative is to run everything inside a Docker container, but that's also very annoying for a compiled language
like Java as you have to rebuild the image every time you make a change. Additionally, tests are usually run before
building the final package and image.

### CI/CD

For the CI/CD pipeline, we provide environment variables from GitHub Secrets to configure the database connection. Even
though they're just test databases, we don't want to leak any credentials or encourage developers to reuse hardcoded
values.

In the pipeline, we
use [service containers](https://docs.github.com/en/actions/using-containerized-services/about-service-containers) to
spin up a database to test against.

Finally, we build a Docker image and deploy to Docker Hub.
