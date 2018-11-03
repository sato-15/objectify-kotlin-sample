package jp.gr.java_conf.satok.objectify.sample.repository

import com.google.cloud.datastore.Cursor
import com.googlecode.objectify.Key
import com.googlecode.objectify.Result

interface BaseRepository<T> {

    fun find(key: Key<T>): T?

    fun list(cursor: Cursor?, limit: Int?): Pair<List<T>, Cursor>

    fun insert(key: Key<T>, entity: T): T

    fun update(key: Key<T>, entity: T): T

    fun put(entity: T): T

    fun batchPut(entities: List<T>): Result<MutableMap<Key<T>, T>>?

    fun delete(key: Key<T>)

    fun batchDeleteByKeys(keys: List<Key<T>>)

    fun batchDeleteByEntities(entities: List<T>)

    fun <E> searchByAncestorKey(ancestorKey: Key<E>): List<T>

}