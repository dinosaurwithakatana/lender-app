#!/bin/bash
set -eu

# Xcode Cloud runners do not ship with Java. Install asdf and use the
# version pinned in the repo's .tool-versions file.

brew tap mdogan/zulu
brew trust mdogan/zulu
brew install zulu-jdk21
export JAVA_HOME=$(/usr/libexec/java_home -v21)
java -version

# Mirror the GitHub Actions setup: use the CI Gradle properties (which force
# IPv4 so Gradle can reach dl.google.com from Xcode Cloud runners).
mkdir -p "$HOME/.gradle"
cp .github/ci-gradle.properties "$HOME/.gradle/gradle.properties"
./gradlew projects --no-configuration-cache -Djava.net.preferIPv4Stack=true
