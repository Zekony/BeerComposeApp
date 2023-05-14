package com.example.beercompapp.domain.use_cases.user_use_cases

data class UserUseCases (
    val updateUserUseCase: UpdateUserUseCase,
    val getUserByIdUseCase: GetUserByIdUseCase,
    val deleteUserUseCase: DeleteUserUseCase,
    val addUserUseCase: AddUserUseCase,
)