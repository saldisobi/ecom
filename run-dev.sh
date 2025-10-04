#!/bin/bash

APP_PORT=8080
CONTAINER_NAME="ecom-postgres"
POSTGRES_USER="postgres"
POSTGRES_DB="ecom_db"

echo "==== Starting Dev Environment ===="

# 1️⃣ Check if Postgres container is running
if [ "$(docker ps -q -f name=$CONTAINER_NAME)" ]; then
    echo "Postgres container is already running."
else
    echo "Starting Postgres container..."
    docker compose up -d postgres
fi

# 2️⃣ Wait until Postgres is ready
echo "Waiting for Postgres to be ready..."
until docker exec $CONTAINER_NAME pg_isready -U $POSTGRES_USER > /dev/null 2>&1; do
    sleep 2
done
echo "Postgres is ready!"

# 3️⃣ Kill any previous Spring Boot app running on APP_PORT
if lsof -i tcp:$APP_PORT | grep LISTEN > /dev/null; then
    echo "Killing existing process on port $APP_PORT..."
    lsof -i tcp:$APP_PORT | grep LISTEN | awk '{print $2}' | xargs kill -9
    sleep 2
fi

# 4️⃣ Run Spring Boot application
echo "Running Spring Boot app..."
./gradlew bootRun
