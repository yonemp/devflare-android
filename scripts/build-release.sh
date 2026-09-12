#!/usr/bin/env bash
set -euo pipefail

root="$(cd "$(dirname "$0")/.." && pwd)"
cd "$root"

./gradlew :app:assembleRelease --no-daemon

apk="$root/releases/DevFlare.apk"
if [[ ! -f "$apk" ]]; then
  echo "expected $apk after assembleRelease" >&2
  exit 1
fi

zip -j -9 "$root/releases/DevFlare.zip" "$apk"

echo
echo "=== sideload artifacts ==="
ls -lh "$root/releases/DevFlare.apk" "$root/releases/DevFlare.zip"
sha256sum "$root/releases/DevFlare.apk" "$root/releases/DevFlare.zip"
unzip -t "$apk"
python3 - <<'PY'
import zipfile, sys
from pathlib import Path
apk = Path("releases/DevFlare.apk")
assert zipfile.is_zipfile(apk), "APK is not a valid zip"
with zipfile.ZipFile(apk) as z:
    bad = z.testzip()
    assert bad is None, f"corrupt zip entry: {bad}"
    names = z.namelist()
    assert "AndroidManifest.xml" in names, "missing AndroidManifest.xml"
    assert any(n.startswith("classes") and n.endswith(".dex") for n in names), "missing classes.dex"
print("APK zip integrity: OK")
PY
