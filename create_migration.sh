#!/bin/bash

MIGRATION_DIR="app-infrastructure/src/main/java/org/example/infrastructure/migration"

LATEST_VERSION=$(ls "$MIGRATION_DIR" | grep -Eo 'V1_[0-9]+' | sed 's/V1_//' | sort -n | tail -1)

if [[ -z "$LATEST_VERSION" ]]; then
    NEW_VERSION="00"
else
    NEW_VERSION=$(printf "%02d" $((LATEST_VERSION + 1)))
fi

TIMESTAMP=$(date +"%Y%m%d%H%M%S")
FILENAME="$MIGRATION_DIR/V1_${NEW_VERSION}_$1_${TIMESTAMP}.java"

# Tạo file
touch "$FILENAME"

echo "✅ The migration file is created: $FILENAME"
