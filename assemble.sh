#!/bin/bash

set -eu

function usageExit() {
    echo "Usage: assemble.sh <nasm-source-file>" >&2
    exit 1
}

if [ "$#" -ne "1" ]
then
    usageExit
fi

SOURCE_FILE="$1"
BASE_NAME=$(basename "$SOURCE_FILE" .asm)
BASE_DIR=$(dirname "$0")
TARGET_DIR="$BASE_DIR/target"

echo "Building $BASE_NAME..." >&2

nasm -f elf64 -o "$TARGET_DIR/$BASE_NAME.o" "$SOURCE_FILE"
ld -o "$TARGET_DIR/$BASE_NAME" "$TARGET_DIR/$BASE_NAME.o"

echo "Built $TARGET_DIR/$BASE_NAME" >&2
