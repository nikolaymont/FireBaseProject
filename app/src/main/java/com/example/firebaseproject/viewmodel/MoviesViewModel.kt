package com.example.firebaseproject.viewmodel

import com.example.firebaseproject.repository.MoviesDBRepository
import com.example.firebaseproject.repository.MoviesDBRepositoryImpl

class MoviesViewModel {

    private val  mMoviesRepository : MoviesDBRepository = MoviesDBRepositoryImpl()
    fun getMovies(): String {
        return mMoviesRepository.getMovies()
    }
}