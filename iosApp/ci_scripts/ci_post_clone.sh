#!/bin/sh
set -eu

# Xcode Cloud runners do not ship with Java. Install asdf and use the
# version pinned in the repo's .tool-versions file.

brew install asdf

# asdf 0.16+ ships as a single Go binary and no longer sources asdf.sh; older
# installs still do. Support both by sourcing when available, otherwise put
# the shims dir on PATH manually.
export ASDF_DATA_DIR="${ASDF_DATA_DIR:-$HOME/.asdf}"
ASDF_SH="$(brew --prefix asdf)/libexec/asdf.sh"
if [ -f "$ASDF_SH" ]; then
  # shellcheck source=/dev/null
  . "$ASDF_SH"
else
  export PATH="$ASDF_DATA_DIR/shims:$PATH"
fi

# .tool-versions lives at the repo root, one level above iosApp/.
cd "$CI_PRIMARY_REPOSITORY_PATH"

asdf plugin add java https://github.com/halcyon/asdf-java.git || true
asdf install

JAVA_HOME="$(asdf where java)"
export JAVA_HOME
echo "JAVA_HOME=$JAVA_HOME"
java -version
