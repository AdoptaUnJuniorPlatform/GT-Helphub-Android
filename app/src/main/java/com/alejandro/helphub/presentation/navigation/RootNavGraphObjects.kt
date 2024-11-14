package com.alejandro.helphub.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class RootNavGraphObjects(
    val route: String
) {
    object MainScreen : RootNavGraphObjects(
        route = "MainScreen"
    )

    object Home : RootNavGraphObjects(
        route = "Home"
    )

    object SplashScreen : RootNavGraphObjects(
        route = "SplashScreen"
    )

    object LoginScreen : RootNavGraphObjects(
        route = "LoginScreen"
    )

    object SignUpCredsScreen : RootNavGraphObjects(
        route = "SignUpCredsScreen"
    )

    object PrivacyScreen : RootNavGraphObjects(
        route = "PrivacyScreen"
    )

    object AuthScreen : RootNavGraphObjects(
        route = "AuthScreen"
    )

    object ForgotPasswordScreen : RootNavGraphObjects(
        route = "ForgotPasswordScreen"
    )

    object ResetPasswordScreen : RootNavGraphObjects(
        route = "ResetPasswordScreen"
    )

    object TwofaRegisterScreen : RootNavGraphObjects(
        route = "TwofaRegisterScreen"
    )

    object TwofaLoginScreen : RootNavGraphObjects(
        route = "TwofaLoginScreen"
    )


}