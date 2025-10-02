package com.pawatr.drawer_navigation.data.local.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class CacheEntry : RealmObject {
    @PrimaryKey
    var key: String = ""
    var cacheData: String = ""
    var updatedAt: Long = 0L
}