# Sideload upload keystore

This is a **sideload / upload key**, not a Play App Signing key. It is checked in so CI and local machines can produce the same installable APK.

| Field | Value |
| --- | --- |
| File | `keystore/devflare-upload.p12` |
| Type | PKCS12 |
| Alias | `upload` |
| Store password | `devflare-sideload` |
| Key password | `devflare-sideload` |
| Validity | 10000 days |
| DN | `CN=DevFlare Sideload, OU=DevFlare, O=DevFlare, L=Internet, ST=NA, C=US` |
| Cert SHA-256 | `52:AE:81:13:1E:C2:A5:A0:8A:A1:75:15:6E:B8:7C:CD:DD:CC:5B:6E:69:90:BC:FE:4E:AF:BB:DA:F8:72:A0:FF` |

Do not reuse this keystore for Play Store uploads. Generate a private Play key outside this repository if the app is published there.

Recreate (destroys the current sideload signature — users would need to uninstall first):

```bash
keytool -genkeypair -keystore keystore/devflare-upload.p12 -storetype PKCS12 \
  -keyalg RSA -keysize 2048 -validity 10000 -alias upload \
  -storepass devflare-sideload -keypass devflare-sideload \
  -dname "CN=DevFlare Sideload, OU=DevFlare, O=DevFlare, L=Internet, ST=NA, C=US"
```
