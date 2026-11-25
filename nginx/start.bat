@echo off
taskkill /F /IM nginx.exe >nul 2>&1
cd /d "%~dp0"
start "" nginx -p "%~dp0." -c nginx.conf