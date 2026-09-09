package site.devflare.app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import site.devflare.app.data.auth.AuthRepository
import site.devflare.app.data.auth.AuthResult
import site.devflare.app.data.auth.SessionStore
import site.devflare.app.data.model.Session

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val loading: Boolean = false,
    val error: String? = null,
)

class SessionViewModel(
    private val sessionStore: SessionStore,
    private val authRepository: AuthRepository,
) : ViewModel() {

    val session: StateFlow<Session?> = sessionStore.session.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        null,
    )

    private val _ready = MutableStateFlow(false)
    val ready: StateFlow<Boolean> = _ready.asStateFlow()

    private val _login = MutableStateFlow(LoginUiState())
    val login: StateFlow<LoginUiState> = _login.asStateFlow()

    init {
        viewModelScope.launch {
            sessionStore.session.collect {
                _ready.value = true
            }
        }
    }

    fun updateEmail(value: String) {
        _login.value = _login.value.copy(email = value, error = null)
    }

    fun updatePassword(value: String) {
        _login.value = _login.value.copy(password = value, error = null)
    }

    fun signIn(onSuccess: () -> Unit) {
        val current = _login.value
        if (current.loading) return
        viewModelScope.launch {
            _login.value = current.copy(loading = true, error = null)
            when (val result = authRepository.signIn(current.email, current.password)) {
                is AuthResult.Success -> {
                    sessionStore.save(result.session)
                    _login.value = LoginUiState(email = result.session.email)
                    onSuccess()
                }
                is AuthResult.Error -> {
                    _login.value = current.copy(loading = false, error = result.message)
                }
            }
        }
    }

    fun signOut(onDone: () -> Unit) {
        viewModelScope.launch {
            sessionStore.clear()
            _login.value = LoginUiState()
            onDone()
        }
    }

    companion object {
        fun factory(store: SessionStore, repo: AuthRepository) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SessionViewModel(store, repo) as T
            }
        }
    }
}
