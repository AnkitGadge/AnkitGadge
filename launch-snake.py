#!/usr/bin/env python3
from __future__ import annotations

import argparse
import http.server
import socketserver
from pathlib import Path


def main() -> None:
    parser = argparse.ArgumentParser(description="Launch local Snake game server")
    parser.add_argument("port", nargs="?", type=int, default=8000, help="Port to serve on")
    args = parser.parse_args()

    repo_root = Path(__file__).resolve().parent
    index_path = repo_root / "snake-game.html"
    if not index_path.exists():
        raise SystemExit("snake-game.html not found next to launch-snake.py")

    handler = http.server.SimpleHTTPRequestHandler
    with socketserver.TCPServer(("", args.port), handler) as httpd:
        print(f"[snake] Serving from: {repo_root}")
        print(f"[snake] Open: http://localhost:{args.port}/snake-game.html")
        print("[snake] Press Ctrl+C to stop")
        try:
            # Serve files from repo root.
            import os

            os.chdir(repo_root)
            httpd.serve_forever()
        except KeyboardInterrupt:
            print("\n[snake] Server stopped")


if __name__ == "__main__":
    main()
