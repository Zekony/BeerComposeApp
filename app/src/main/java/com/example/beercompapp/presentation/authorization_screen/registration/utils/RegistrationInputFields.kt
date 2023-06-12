package com.example.beercompapp.presentation.authorization_screen.registration.utils

data class RegistrationInputFields(
    val login: String = "",
    val number: String = "",
    val password: CharArray = "".toCharArray(),
    val repeatPassword: CharArray  = "".toCharArray()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RegistrationInputFields

        if (login != other.login) return false
        if (number != other.number) return false
        if (!password.contentEquals(other.password)) return false
        if (!repeatPassword.contentEquals(other.repeatPassword)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = login.hashCode()
        result = 31 * result + number.hashCode()
        result = 31 * result + password.contentHashCode()
        result = 31 * result + repeatPassword.contentHashCode()
        return result
    }
}