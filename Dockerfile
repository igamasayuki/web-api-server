# 1. ビルド用のJava 21環境を用意
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app
COPY . .
# Windowsで作られた改行コード等のズレを吸収してビルド権限を付与
RUN sed -i 's/\r$//' gradlew
RUN chmod +x ./gradlew
# テストをスキップしてビルド実行
RUN ./gradlew clean build -x test

# 2. 実行用の軽量なJava 21環境を用意
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# ビルドしたjarファイルをコピー
COPY --from=builder /app/build/libs/*.jar app.jar
# Renderが使うポート(8080)を公開
EXPOSE 8080
# 起動コマンド
ENTRYPOINT ["java", "-jar", "app.jar"]
