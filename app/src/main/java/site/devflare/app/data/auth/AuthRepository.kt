package site.devflare.app.data.auth

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.FormBody
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import site.devflare.app.data.model.Session
import site.devflare.app.data.model.SessionSource
import java.io.IOException
import java.util.concurrent.TimeUnit

sealed class AuthResult {
    data class Success(val session: Session) : AuthResult()
    data class Error(val message: String) : AuthResult()
}

class MemoryCookieJar : CookieJar {
    private val store = mutableListOf<Cookie>()

    @Synchronized
    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cookies.forEach { incoming ->
            store.removeAll { it.name == incoming.name && it.matches(url) }
            store += incoming
        }
    }

    @Synchronized
    override fun loadForRequest(url: HttpUrl): List<Cookie> = store.filter { it.matches(url) }
}

class AuthRepository(
    private val client: OkHttpClient = defaultClient(),
) {
    suspend fun signIn(email: String, password: String): AuthResult = withContext(Dispatchers.IO) {
        val trimmedEmail = email.trim()
        if (!trimmedEmail.contains("@") || password.length < 6) {
            return@withContext AuthResult.Error("Use a valid email and a password of at least 6 characters.")
        }
        try {
            remoteSignIn(trimmedEmail, password)
        } catch (_: IOException) {
            AuthResult.Success(
                Session(
                    name = displayName(trimmedEmail),
                    email = trimmedEmail,
                    source = SessionSource.LOCAL_DEMO,
                ),
            )
        } catch (_: Exception) {
            AuthResult.Success(
                Session(
                    name = displayName(trimmedEmail),
                    email = trimmedEmail,
                    source = SessionSource.LOCAL_DEMO,
                ),
            )
        }
    }

    private fun remoteSignIn(email: String, password: String): AuthResult {
        val csrfRequest = Request.Builder()
            .url("$BASE/api/auth/csrf")
            .header("Accept", "application/json")
            .build()
        val csrfBody = client.newCall(csrfRequest).execute().use { response ->
            if (!response.isSuccessful) throw IOException("csrf ${response.code}")
            response.body?.string().orEmpty()
        }
        val csrfToken = JSONObject(csrfBody).optString("csrfToken")
        if (csrfToken.isBlank()) throw IOException("missing csrf")

        val form = FormBody.Builder()
            .add("csrfToken", csrfToken)
            .add("email", email)
            .add("password", password)
            .add("redirect", "false")
            .add("json", "true")
            .add("callbackUrl", "$BASE/dashboard")
            .build()
        val loginRequest = Request.Builder()
            .url("$BASE/api/auth/callback/credentials")
            .header("Accept", "application/json")
            .post(form)
            .build()

        val location = client.newCall(loginRequest).execute().use { response ->
            response.header("Location").orEmpty()
        }

        if (location.contains("CredentialsSignin") || location.contains("error=")) {
            return AuthResult.Error("Those credentials were rejected. Check the email and password.")
        }

        val sessionRequest = Request.Builder()
            .url("$BASE/api/auth/session")
            .header("Accept", "application/json")
            .build()
        val sessionBody = client.newCall(sessionRequest).execute().use { response ->
            if (!response.isSuccessful) throw IOException("session ${response.code}")
            response.body?.string().orEmpty()
        }
        if (sessionBody.isBlank() || sessionBody == "null") {
            return AuthResult.Error("Those credentials were rejected. Check the email and password.")
        }
        val root = JSONObject(sessionBody)
        val user = root.optJSONObject("user") ?: return AuthResult.Error("Signed in, but the session payload was empty.")
        return AuthResult.Success(
            Session(
                name = user.optString("name").ifBlank { displayName(email) },
                email = user.optString("email").ifBlank { email },
                source = SessionSource.REMOTE,
                userId = user.optString("id").ifBlank { null },
            ),
        )
    }

    companion object {
        const val BASE = "https://www.devflare.site"

        fun defaultClient(): OkHttpClient = OkHttpClient.Builder()
            .cookieJar(MemoryCookieJar())
            .followRedirects(false)
            .followSslRedirects(false)
            .connectTimeout(12, TimeUnit.SECONDS)
            .readTimeout(12, TimeUnit.SECONDS)
            .writeTimeout(12, TimeUnit.SECONDS)
            .build()

        fun displayName(email: String): String {
            val local = email.substringBefore("@")
            return local.split('.', '_', '-', '+')
                .filter { it.isNotBlank() }
                .joinToString(" ") { part -> part.replaceFirstChar { it.uppercase() } }
                .ifBlank { "Studio" }
        }
    }
}
