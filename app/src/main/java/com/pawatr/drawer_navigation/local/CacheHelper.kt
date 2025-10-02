package com.pawatr.drawer_navigation.data.local

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pawatr.drawer_navigation.data.local.model.CacheEntry
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CacheHelper @Inject constructor(
    private val realm: Realm,
    private val gson: Gson
) {
    suspend fun <T> saveCache(cacheKey: String, value: T) = withContext(Dispatchers.IO) {
        val json = gson.toJson(value)
        realm.write {
            copyToRealm(
                CacheEntry().apply {
                    key = cacheKey
                    cacheData = json
                    updatedAt = System.currentTimeMillis()
                },
                updatePolicy = UpdatePolicy.ALL
            )
        }
    }

    suspend fun <T> getCache(cacheKey: String, clazz: Class<T>): T? = withContext(Dispatchers.IO) {
        val entry = realm.query<CacheEntry>("key == $0", cacheKey).first().find()
        entry?.cacheData?.let { json ->
            runCatching { gson.fromJson(json, clazz) }.getOrNull()
        }
    }

    suspend inline fun <reified T> getCache(cacheKey: String): T? =
        getCacheInternal(cacheKey, object : TypeToken<T>() {}.type)

    @PublishedApi
    internal suspend fun <T> getCacheInternal(cacheKey: String, type: Type): T? =
        withContext(Dispatchers.IO) {
            val entry = realm.query<CacheEntry>("key == $0", cacheKey).first().find()
            entry?.cacheData?.let { json ->
                runCatching { gson.fromJson<T>(json, type) }.getOrNull()
            }
        }

    suspend fun clearCache(cacheKey: String) = withContext(Dispatchers.IO) {
        realm.write {
            query<CacheEntry>("key == $0", cacheKey).first().find()?.let { delete(it) }
        }
    }

    suspend fun clearAll() = withContext(Dispatchers.IO) {
        realm.write { delete(query<CacheEntry>().find()) }
    }
}
