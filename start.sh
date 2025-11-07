#!/usr/bin/env bash
set -e

echo "🚀 Starting GestorLDS Backend..."

cd backend
java -Dspring.profiles.active=prod \
     -Dserver.port=${PORT:-8080} \
     -Xmx512m \
     -Xms256m \
     -jar target/backend-1.0.0.jar