FROM azul/zulu-openjdk-alpine:21-latest AS build
RUN apk add --no-cache \
      nodejs \
      npm \
 && rm -rf /var/cache/* \
 && mkdir /var/cache/apk

ENV GRADLE_OPTS="-Dorg.gradle.daemon=false -Dkotlin.incremental=false -Dorg.gradle.jvmargs=-Xmx2048m -XX:MaxMetaspaceSize=512m"

WORKDIR /app

COPY gradlew settings.gradle.kts build.gradle.kts ./
COPY gradle ./gradle
RUN ./gradlew --version

COPY build-logic ./build-logic
COPY cli ./cli
COPY client ./client
COPY datamodifier ./datamodifier
COPY datastore ./datastore
COPY models ./models
COPY repos ./repos
COPY server ./server
COPY shared ./shared

RUN ./gradlew -Plender.downloadNode=false :cli:app:installDist
RUN ./gradlew --scan -Plender.serverBuildsWeb=true -Plender.downloadNode=false :server:app:installDist

FROM eclipse-temurin:21-jre-alpine AS runtime
RUN apk add --no-cache \
      bash \
      curl \
      tini \
 && rm -rf /var/cache/* \
 && mkdir /var/cache/apk

ENV LENDER_DATA_DIR=/data
WORKDIR /app
COPY --from=build /app/server/app/build/install/app ./server
COPY --from=build /app/cli/app/build/install/lender-cli ./cli
ENTRYPOINT ["/sbin/tini", "--"]
CMD ["/app/server/bin/app"]
