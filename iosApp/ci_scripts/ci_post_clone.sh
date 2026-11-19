#!/bin/bash
set -eu

# Xcode Cloud runners do not ship with Java. The zulu-jdk21 cask installs via
# a .pkg, which needs sudo (unavailable on Xcode Cloud). Instead, let brew
# download and checksum the DMG, then expand the .pkg payload into a
# user-writable directory and point JAVA_HOME at it.

CASK=mdogan/zulu/zulu-jdk21
brew tap mdogan/zulu
brew trust mdogan/zulu
brew fetch --cask "$CASK"
DMG="$(brew --cache --cask "$CASK")"

JDK_DIR="$HOME/zulu-jdk21"
MOUNT_DIR="$(mktemp -d)"
hdiutil attach "$DMG" -nobrowse -readonly -mountpoint "$MOUNT_DIR"
rm -rf "$JDK_DIR"
pkgutil --expand-full "$MOUNT_DIR"/*.pkg "$JDK_DIR"
hdiutil detach "$MOUNT_DIR"

JAVA_HOME="$(find "$JDK_DIR" -type d -path '*/Contents/Home' | head -n 1)"
export JAVA_HOME
export PATH="$JAVA_HOME/bin:$PATH"
echo "JAVA_HOME=$JAVA_HOME"
java -version

# ci_post_clone.sh runs from ci_scripts/; the Gradle files live at the repo root.
cd "$CI_PRIMARY_REPOSITORY_PATH"

# Mirror the GitHub Actions setup: use the CI Gradle properties (which force
# IPv4 so Gradle can reach dl.google.com from Xcode Cloud runners).
mkdir -p "$HOME/.gradle"
cp .github/ci-gradle.properties "$HOME/.gradle/gradle.properties"
./gradlew projects --no-configuration-cache -Djava.net.preferIPv4Stack=true
