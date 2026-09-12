# DevFlare

Native Android workspace for the DevFlare studio. Kotlin, Jetpack Compose, Material 3 dark-only. This is not a WebView of [devflare.site](https://www.devflare.site).

Package: `site.devflare.app`  
minSdk 26 · targetSdk 35 · version **0.1.2**

## Screens

- **Splash** — slash + spark mark on black
- **Sign in** — email / password against Auth.js on `www.devflare.site`
- **Home** — studio pulse, active projects, this week’s meetings, activity
- **Inbox** — client / agent / deal threads
- **Tasks** — board grouped by To do, In progress, Review, Done
- **More** — overflow to People, Notes, Agents, Reports, Automations, Meetings, Projects, plus sign out

The workspace starts empty. Screens show empty states until live data is wired in.

## Auth

Sign-in calls Auth.js credentials:

1. `GET /api/auth/csrf`
2. `POST /api/auth/callback/credentials`
3. `GET /api/auth/session`

Workspace credentials from the desktop portal work here. If the device cannot reach the site, an **offline session** unlocks the shell when the email contains `@` and the password is at least 6 characters. Full NextAuth cookie persistence across app launches is a follow-up; the app stores name / email / source locally after a successful sign-in.

## Open in Android Studio

1. Install Android Studio (Ladybug / 2024.2 or newer).
2. **File → Open** and select this folder.
3. Let Gradle sync. The IDE will install the Android SDK if needed.
4. Run the `app` configuration on a device or emulator (API 26+).

If you open the project outside this environment, create `local.properties` with:

```
sdk.dir=/absolute/path/to/Android/sdk
```

## Build the sideload APK

Release builds are **minified and resource-shrunk** with R8, then signed with the checked-in sideload keystore (see [keystore/README.md](keystore/README.md)).

```bash
export ANDROID_HOME=/path/to/Android/sdk   # or rely on local.properties
./gradlew assembleRelease
```

Gradle writes:

- `app/build/outputs/apk/release/app-release.apk`
- **`releases/DevFlare.apk`** (copy, same bytes)

Optional zip for browsers that mishandle `.apk`:

```bash
zip -j releases/DevFlare.zip releases/DevFlare.apk
```

Keystore (sideload / upload only — not for Play Store):

| Field | Value |
| --- | --- |
| File | `keystore/devflare-upload.p12` |
| Alias | `upload` |
| Passwords | `devflare-sideload` |

## Install with adb

```bash
adb install -r releases/DevFlare.apk
```

No Play Store listing is required.

## Download

GitHub release **v0.1.2** (R8 minified, ~1.2 MB):

- https://github.com/yonemp/devflare-android/releases/download/v0.1.2/DevFlare.apk
- https://github.com/yonemp/devflare-android/releases/download/v0.1.2/DevFlare.zip

SHA-256 (`DevFlare.apk`): `0e5972fe861ada47e1d52285fcfe7e6f1e054dbe7e5e6ae5336121d7d7e1b421`

If a browser stalls on the `.apk`, use the `.zip` and unpack `DevFlare.apk` on the device or computer. The repo must be **public** for those URLs to work without GitHub login.

## Project shape

Single-activity Compose Navigation. Lean dependencies: Compose BOM, Navigation, DataStore, OkHttp. No second UI kit, no database.
