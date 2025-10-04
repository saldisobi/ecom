#!/bin/bash

# Check if Postgres container is running
if [ "$(docker ps -q -f name=ecom-postgres)" == "" ]; then
    echo "Postgres container not running. Starting..."
    docker compose up -d postgres
else
    echo "Postgres container already running."
fi

# Wait for Postgres to be ready
echo "Waiting for Postgres to be ready..."
until docker exec ecom-postgres pg_isready -U postgres >/dev/null 2>&1; do
  sleep 1
done

echo "Postgres is ready!"
