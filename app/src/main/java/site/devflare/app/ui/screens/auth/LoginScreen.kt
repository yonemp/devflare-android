package site.devflare.app.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import site.devflare.app.ui.LoginUiState
import site.devflare.app.ui.components.FlareMarkBadge
import site.devflare.app.ui.theme.Black
import site.devflare.app.ui.theme.Danger
import site.devflare.app.ui.theme.Hairline
import site.devflare.app.ui.theme.NearBlack
import site.devflare.app.ui.theme.Surface
import site.devflare.app.ui.theme.TextPrimary
import site.devflare.app.ui.theme.TextSecondary
import site.devflare.app.ui.theme.TextTertiary
import site.devflare.app.ui.theme.White

@Composable
fun LoginScreen(
    state: LoginUiState,
    onEmail: (String) -> Unit,
    onPassword: (String) -> Unit,
    onSubmit: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 28.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            FlareMarkBadge(size = 52.dp, corner = 14.dp)
            Spacer(Modifier.height(18.dp))
            Text(
                "DevFlare",
                color = TextPrimary,
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = (-0.6).sp,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "// CLIENT PORTAL",
                color = TextTertiary,
                fontSize = 10.sp,
                letterSpacing = 2.sp,
                fontFamily = FontFamily.Monospace,
            )
            Spacer(Modifier.height(10.dp))
            Text(
                "Sign in to the studio workspace.",
                color = TextSecondary,
                fontSize = 14.sp,
            )
        }

        Spacer(Modifier.height(36.dp))

        FieldLabel("Email")
        StudioField(
            value = state.email,
            onValueChange = onEmail,
            placeholder = "you@studio.com",
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next,
        )
        Spacer(Modifier.height(16.dp))
        FieldLabel("Password")
        StudioField(
            value = state.password,
            onValueChange = onPassword,
            placeholder = "••••••••",
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            password = true,
            onDone = onSubmit,
        )

        if (state.error != null) {
            Spacer(Modifier.height(14.dp))
            Text(state.error, color = Danger, fontSize = 13.sp, lineHeight = 18.sp)
        }

        Spacer(Modifier.height(24.dp))
        Button(
            onClick = onSubmit,
            enabled = !state.loading,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = White,
                contentColor = NearBlack,
                disabledContainerColor = White.copy(alpha = 0.4f),
                disabledContentColor = NearBlack,
            ),
        ) {
            if (state.loading) {
                CircularProgressIndicator(color = NearBlack, strokeWidth = 2.dp, modifier = Modifier.size(18.dp))
            } else {
                Text("Sign in", fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
            }
        }

        Spacer(Modifier.height(22.dp))
        Text(
            "Connects to Auth.js on www.devflare.site. If the network is unreachable, a local demo session unlocks the shell when the email contains @ and the password is 6+ characters.",
            color = TextTertiary,
            fontSize = 12.sp,
            lineHeight = 17.sp,
        )
    }
}

@Composable
private fun FieldLabel(text: String) {
    Text(
        text = text,
        color = TextSecondary,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(bottom = 8.dp),
    )
}

@Composable
private fun StudioField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    password: Boolean = false,
    onDone: (() -> Unit)? = null,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        placeholder = { Text(placeholder, color = TextTertiary) },
        visualTransformation = if (password) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = KeyboardActions(onDone = { onDone?.invoke() }),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Hairline, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Surface,
            unfocusedContainerColor = Surface,
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary,
            cursorColor = White,
            focusedBorderColor = White.copy(alpha = 0.35f),
            unfocusedBorderColor = Hairline,
        ),
    )
}
