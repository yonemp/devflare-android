package site.devflare.app

import android.app.Application
import site.devflare.app.data.auth.AuthRepository
import site.devflare.app.data.auth.SessionStore

class DevFlareApplication : Application() {
    lateinit var sessionStore: SessionStore
        private set
    lateinit var authRepository: AuthRepository
        private set

    override fun onCreate() {
        super.onCreate()
        sessionStore = SessionStore(this)
        authRepository = AuthRepository()
    }
}
