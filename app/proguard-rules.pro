# R8 / ProGuard rules for the sideload release APK.
# Compose and AndroidX keep rules ship with those libraries.

# OkHttp / Okio optional platform adapters
-dontwarn okhttp3.internal.platform.**
-dontwarn org.conscrypt.**
-dontwarn org.bouncycastle.**
-dontwarn org.openjsse.**
-dontwarn okhttp3.internal.publicsuffix.**

# Keep the application and launcher activity names used by the manifest.
-keep class site.devflare.app.DevFlareApplication { <init>(); }
-keep class site.devflare.app.MainActivity { <init>(); }

# JSONObject / org.json is in the Android SDK; no extra keep needed.
# DataStore and Compose are consumed without reflection on app models.
