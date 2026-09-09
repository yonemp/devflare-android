package site.devflare.app.data.auth

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import site.devflare.app.data.model.Session
import site.devflare.app.data.model.SessionSource

private val Context.sessionDataStore by preferencesDataStore(name = "devflare_session")

class SessionStore(private val context: Context) {
    private val nameKey = stringPreferencesKey("name")
    private val emailKey = stringPreferencesKey("email")
    private val sourceKey = stringPreferencesKey("source")
    private val userIdKey = stringPreferencesKey("user_id")

    val session: Flow<Session?> = context.sessionDataStore.data.map { prefs ->
        val email = prefs[emailKey] ?: return@map null
        val name = prefs[nameKey] ?: email.substringBefore("@")
        val source = runCatching { SessionSource.valueOf(prefs[sourceKey] ?: SessionSource.LOCAL_DEMO.name) }
            .getOrDefault(SessionSource.LOCAL_DEMO)
        Session(name = name, email = email, source = source, userId = prefs[userIdKey])
    }

    suspend fun save(session: Session) {
        context.sessionDataStore.edit { prefs ->
            prefs[nameKey] = session.name
            prefs[emailKey] = session.email
            prefs[sourceKey] = session.source.name
            if (session.userId != null) prefs[userIdKey] = session.userId else prefs.remove(userIdKey)
        }
    }

    suspend fun clear() {
        context.sessionDataStore.edit { it.clear() }
    }
}
