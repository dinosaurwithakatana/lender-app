FROM azul/zulu-openjdk-alpine:21-latest AS build
RUN apk add --no-cache \
      nodejs \
      npm \
 && rm -rf /var/cache/* \
 && mkdir /var/cache/apk

ENV GRADLE_OPTS="-Dorg.gradle.daemon=false -Dkotlin.incremental=false"
WORKDIR /app

COPY gradlew settings.gradle.kts build.gradle.kts ./
COPY gradle ./gradle
RUN ./gradlew --version

COPY build-logic ./build-logic
COPY cli ./cli
COPY client ./client
COPY database ./database
COPY datamodifier ./datamodifier
COPY datastore ./datastore
COPY models ./models
COPY repos ./repos
COPY server ./server
COPY shared ./shared

RUN ./gradlew -Pkotlin.daemon.jvmargs=-Xmx6144M :cli:app:installDist
RUN ./gradlew --scan -Pkotlin.daemon.jvmargs=-Xmx6144M :server:app:installDist

FROM eclipse-temurin:21-jre-alpine AS runtime
RUN apk add --no-cache \
      bash \
      curl \
      tini \
 && rm -rf /var/cache/* \
 && mkdir /var/cache/apk

WORKDIR /app
COPY --from=build /app/server/app/build/install/app ./server
COPY --from=build /app/cli/app/build/install/lender-cli ./cli
ENTRYPOINT ["/sbin/tini", "--"]
CMD ["/app/server/bin/app"]
