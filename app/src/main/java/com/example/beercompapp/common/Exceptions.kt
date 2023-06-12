package com.example.beercompapp.common

sealed class PasswordException(message: String): Exception(message) {
    class OldPasswordException(message: String): PasswordException(message)
    class NewPasswordException(message: String): PasswordException(message)
    class RepeatPasswordException(message: String): PasswordException(message)
}

class UserDoesntExistException(message: String): Exception(message)
class NumberAlreadyExistsException(message: String): Exception(message)

class InvalidNumberException(message: String): Exception(message)
class InvalidLoginException(message: String): Exception(message)