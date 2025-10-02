package com.pawatr.drawer_navigation.di

import com.google.gson.Gson
import com.pawatr.drawer_navigation.data.local.model.CacheEntry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RealmModule {

    @Provides @Singleton
    fun provideGson(): Gson = Gson()

    @Provides @Singleton
    fun provideRealmConfiguration(): RealmConfiguration =
        RealmConfiguration.Builder(
            schema = setOf(CacheEntry::class)
        )
            .deleteRealmIfMigrationNeeded()
            .build()

    @Provides @Singleton
    fun provideRealm(config: RealmConfiguration): Realm = Realm.open(config)
}
