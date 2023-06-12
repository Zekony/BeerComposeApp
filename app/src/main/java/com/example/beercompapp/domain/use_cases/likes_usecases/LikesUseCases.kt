package com.example.beercompapp.domain.use_cases.likes_usecases

data class LikesUseCases(
    val getUserLikesUseCase: GetUserLikesUseCase,
    val addLikeUseCase: AddLikeUseCase,
    val removeLikeUseCase: RemoveLikeUseCase,
    val isItemLikedUseCase: IsItemLikedUseCase,
    val likeOrDislikeUseCase: LikeOrDislikeUseCase,
)