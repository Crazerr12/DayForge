package com.example.dayforge.di

import com.example.dayforge.presentation.base.Searcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PresentationModule {

    @Provides
    @Singleton
    fun provideSearcher(): Searcher {
        return Searcher
    }
}