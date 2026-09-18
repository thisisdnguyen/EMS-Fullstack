# ===== STAGE 1: BUILD =====
# Dùng image có sẵn Maven + Java 17 để build ra file .jar
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml TRƯỚC, tải dependency TRƯỚC KHI copy code
# -> tận dụng Docker cache: nếu code đổi nhưng pom.xml không đổi,
#    Docker sẽ không phải tải lại dependency mỗi lần build
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Giờ mới copy toàn bộ source code vào
COPY src ./src

# Build ra file .jar, bỏ qua chạy test lúc build image (test đã chạy riêng ở bước trước)
RUN mvn clean package -DskipTests

# ===== STAGE 2: RUN =====
# Dùng image nhỏ gọn, CHỈ có Java runtime (không có Maven, không có source code)
# -> image cuối cùng nhẹ hơn rất nhiều so với dùng chung 1 stage
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy CHỈ file .jar đã build xong từ stage 1 sang, không mang theo rác build
COPY --from=build /app/target/*.jar app.jar

# Báo cho Docker biết container sẽ lắng nghe ở port 8080
EXPOSE 8080

# Lệnh chạy khi container khởi động
ENTRYPOINT ["java", "-jar", "app.jar"]