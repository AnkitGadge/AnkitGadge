#!/usr/bin/env bash
set -euo pipefail

PORT="${1:-8000}"
SCRIPT_DIR="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd)"

if ! command -v python3 >/dev/null 2>&1; then
  echo "python3 is required to launch the game." >&2
  exit 1
fi

cd "${SCRIPT_DIR}"

echo "Serving Snake game at: http://localhost:${PORT}/snake-game.html"
echo "Press Ctrl+C to stop."
python3 -m http.server "${PORT}"
