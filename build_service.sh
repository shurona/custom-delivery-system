#!/bin/bash

# 스크립트 실행 시 에러 발생 시 즉시 종료
set -e

# eureka 빌드
echo "Building eureka service..."
./gradlew :eureka:clean :eureka:build --no-daemon -x test
echo "Eureka service build completed!"

# gateway 빌드
echo "Building gateway service..."
./gradlew :gateway:clean :gateway:build --no-daemon -x test
echo "Gateway service build completed"

# user 빌드
echo "Building user service..."
./gradlew :user:clean :user:build --no-daemon -x test
echo "User service build completed!"

# auth 빌드
echo "Building auth service..."
./gradlew :auth:clean :auth:build --no-daemon -x test
echo "Auth service build completed!"

# coupon 빌드
echo "Building coupon service..."
./gradlew :coupon:clean :coupon:build --no-daemon -x test
echo "Coupon service build completed!"

# store 빌드
echo "Building store service..."
./gradlew :store:clean :store:build --no-daemon -x test
echo "store service build completed!"

# order 빌드
echo "Building order service..."
./gradlew :order:clean :order:build --no-daemon -x test
echo "order service build completed!"

# delivery 빌드
echo "Building delivery service..."
./gradlew :delivery:clean :delivery:build --no-daemon -x test
echo "delivery service build completed!"

# rider 빌드
echo "Building rider service..."
./gradlew :rider:clean :rider:build --no-daemon -x test
echo "rider service build completed!"