package com.example.beercompapp.domain.use_cases.user_usecases

data class UserUseCases (
    val updateUserUseCase: UpdateUserUseCase,
    val getUserByNumberUseCase: GetUserByNumberUseCase,
    val deleteUserUseCase: DeleteUserUseCase,
    val addUserUseCase: AddUserUseCase,
    val setCurrentUserUseCase: SetCurrentUserUseCase,
    val getActiveUserUseCase: GetActiveUserUseCase,
)