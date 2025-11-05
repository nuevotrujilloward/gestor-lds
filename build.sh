#!/usr/bin/env bash
set -e

echo "🔨 Building GestorLDS Backend..."

# Navegar al directorio backend
cd backend

# Limpiar y compilar
mvn clean package -DskipTests

echo "✅ Build completed successfully!"
echo "📦 JAR ubicado en: backend/target/backend-1.0.0.jar"