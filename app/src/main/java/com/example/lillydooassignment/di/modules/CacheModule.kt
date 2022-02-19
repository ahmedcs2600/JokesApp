package com.example.lillydooassignment.di.modules

import android.content.Context
import com.example.cache.manager.manager.DataStoreManager
import com.example.cache.manager.manager.DataStoreManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CacheModule {

    @Provides
    @Singleton
    fun providesDataStoreManager(@ApplicationContext context: Context): DataStoreManager {
        return DataStoreManagerImpl(context)
    }

}