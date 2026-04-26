@echo off
setlocal

set PORT=%1
if "%PORT%"=="" set PORT=8000

echo [snake] Starting local server on port %PORT%...
echo [snake] Open: http://localhost:%PORT%/snake-game.html
echo [snake] Press Ctrl+C to stop.

where py >nul 2>nul
if %ERRORLEVEL% EQU 0 (
  py -3 -m http.server %PORT%
  goto :end
)

where python >nul 2>nul
if %ERRORLEVEL% EQU 0 (
  python -m http.server %PORT%
  goto :end
)

echo [snake] ERROR: Python was not found.
echo [snake] Install Python 3, then run launch-snake.cmd again.
exit /b 1

:end
