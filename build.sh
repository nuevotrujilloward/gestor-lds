#!/usr/bin/env bash
set -e

echo "🔨 Building GestorLDS Backend..."

cd backend
mvn clean package -DskipTests

echo "✅ Build completed successfully!"