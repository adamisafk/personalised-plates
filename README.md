# License Plate Management System

A full-stack application to allow users to manage personalised plates.

## Prerequisites

- Docker (with Docker Compose)

## Installation

First, navigate to the `service` directory. Then type:

```bash
./mvnw clean package
```

Or if you have Maven installed:

```bash
mvn clean package
```

Then naviagate to the root directory where `docker-compose.yml` is located. Use docker compose to build containers:

```bash
docker compose build
```

Use this command to boot up containers:

```bash
docker compose up
...
docker compose up
```

**IMPORTANT**: You **MUST** run `docker compose up` twice. This is because the Java application loads before the MySQL container can create the database, causing it to exit. Wait until MySQL has fully initialised then re-run.

## Usage

Visit `localhost:1337` in your browser to access the front-end. Create an account by registering under `My Account` and logging in.

## TO DO

- Fix CORS issue on /login route
```
Access to XMLHttpRequest at 'http://localhost:9300/login' from origin 'http://localhost:1337' has been blocked by CORS policy: The 'Access-Control-Allow-Origin' header has a value 'http://localhost:3000' that is not equal to the supplied origin.
```