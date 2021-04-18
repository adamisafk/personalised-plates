# License Plate Management System

A full-stack application to allow users to manage personalised plates.

## Prerequisites

- Docker (with Docker Compose)

## Installation

When in root directory with `docker-compose.yml` Use docker compose to build containers:

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
