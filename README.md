# DevFlare

Native Android workspace for the DevFlare studio. Kotlin, Jetpack Compose, Material 3 dark-only. This is not a WebView of [devflare.site](https://www.devflare.site).

Package: `site.devflare.app`  
minSdk 26 · targetSdk 35

## Screens

- **Splash** — slash + spark mark on black
- **Sign in** — email / password against Auth.js on `www.devflare.site`
- **Home** — studio pulse, active projects, this week’s meetings, activity
- **Inbox** — client / agent / deal threads
- **Tasks** — board grouped by To do, In progress, Review, Done
- **More** — overflow to People, Notes, Agents, Reports, Automations, Meetings, Projects, plus sign out

Sample content matches the live desktop workspace (Lumen, Atlas, Harbor, Kindred, Veil Pay, Northwind).

## Auth

Sign-in calls Auth.js credentials:

1. `GET /api/auth/csrf`
2. `POST /api/auth/callback/credentials`
3. `GET /api/auth/session`

Workspace credentials from the desktop portal work here. If the device cannot reach the site, a **local demo session** unlocks the shell when the email contains `@` and the password is at least 6 characters. Full NextAuth cookie persistence across app launches is a follow-up; the app stores name / email / source locally after a successful sign-in.

## Open in Android Studio

1. Install Android Studio (Ladybug / 2024.2 or newer).
2. **File → Open** and select this folder.
3. Let Gradle sync. The IDE will install the Android SDK if needed.
4. Run the `app` configuration on a device or emulator (API 26+).

If you open the project outside this environment, create `local.properties` with:

```
sdk.dir=/absolute/path/to/Android/sdk
```

## Build a debug APK

```bash
export ANDROID_HOME=/path/to/Android/sdk   # or rely on local.properties
./gradlew assembleDebug
```

The Gradle output is `app/build/outputs/apk/debug/app-debug.apk`.  
A copy is also written to **`releases/DevFlare.apk`**.

## Install with adb

```bash
adb install -r releases/DevFlare.apk
```

No Play Store listing is required.

## Project shape

Single-activity Compose Navigation. Lean dependencies: Compose BOM, Navigation, DataStore, OkHttp. No second UI kit, no database.
