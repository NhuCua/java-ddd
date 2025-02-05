#!/bin/bash

TIMESTAMP=$(date +"%Y%m%d%H%M%S")

FILENAME="app-infrastructure/src/main/java/org/example/infrastructure/migration/$1_${TIMESTAMP}.java"

touch "$FILENAME"

echo "✅ The migration file is created: $FILENAME"
