#!/usr/bin/env bash
#
# Copyright © 2025 Devin B. Royal. All Rights Reserved.
# Setup script to install and configure SDKMAN! and jEnv for dynamic Java version switching.

set -e

echo "📦 Installing SDKMAN..."
curl -s "https://get.sdkman.io" | bash

echo "🔁 Reloading SDKMAN..."
export SDKMAN_DIR="$HOME/.sdkman"
source "$HOME/.sdkman/bin/sdkman-init.sh"

echo "✅ SDKMAN installed."

echo "📦 Installing Java versions via SDKMAN..."
sdk install java 17.0.9-tem
sdk install java 21.0.2-tem
sdk install java 8.0.392-tem

echo "📦 Installing jEnv..."
git clone https://github.com/jenv/jenv.git ~/.jenv
export PATH="$HOME/.jenv/bin:$PATH"
eval "$(jenv init -)"

echo "✅ jEnv installed and configured."

echo "🔧 Setting up shells..."
if [ -n "$ZSH_VERSION" ]; then
    echo 'export PATH="$HOME/.jenv/bin:$PATH"' >> ~/.zshrc
    echo 'eval "$(jenv init -)"' >> ~/.zshrc
elif [ -n "$BASH_VERSION" ]; then
    echo 'export PATH="$HOME/.jenv/bin:$PATH"' >> ~/.bashrc
    echo 'eval "$(jenv init -)"' >> ~/.bashrc
else
    echo "⚠️ Unsupported shell. Add jEnv manually to your shell profile."
fi

echo "🔗 Registering Java SDKs with jEnv..."
for j in $(ls -1 "$SDKMAN_DIR/candidates/java"); do
  JDK_PATH="$SDKMAN_DIR/candidates/java/$j"
  if [ -d "$JDK_PATH" ]; then
    jenv add "$JDK_PATH"
  fi
done

echo "🔧 Enabling jEnv plugins..."
jenv enable-plugin export
jenv enable-plugin maven
jenv enable-plugin gradle

echo "🧹 Cleaning system JAVA_HOME conflicts..."
unset JAVA_HOME
sudo rm -f /usr/libexec/java_home || true

echo "✅ Java SDK switching environment fully configured!"

echo "👉 Use \`jenv global 17.0.9\`, \`jenv shell 21.0.2\`, or create a .java-version file."
echo "📂 Example: echo '21.0.2' > .java-version"
