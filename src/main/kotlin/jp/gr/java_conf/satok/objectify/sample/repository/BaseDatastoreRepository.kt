package jp.gr.java_conf.satok.objectify.sample.repository

import com.google.cloud.datastore.Cursor
import com.googlecode.objectify.Key
import com.googlecode.objectify.Objectify
import com.googlecode.objectify.ObjectifyService
import com.googlecode.objectify.Result
import org.slf4j.LoggerFactory

abstract class BaseDatastoreRepository<T>: BaseRepository<T> {

    companion object {
        val logger = LoggerFactory.getLogger(this::class.java.enclosingClass)!!
    }

    var objectify: Objectify = ObjectifyService.ofy()!!

    abstract val entityClass: Class<T>

    override fun find(key: Key<T>): T? {
        return objectify.load().key(key).now()
    }

    override fun list(cursor: Cursor, limit: Int): Pair<List<T>, Cursor> {
        val query = objectify.load().type(entityClass).limit(limit)
        if (cursor != null) {
            query.startAt(cursor)
        }
        return Pair(query.list(), query.iterator().cursorAfter)
    }

    override fun insert(key: Key<T>, entity: T): T {
        if (find(key) != null) {
            throw IllegalStateException()
        }
        val key = objectify.save().entity(entity).now()
        return this.find(key)!!
    }

    override fun update(key: Key<T>, entity: T): T {
        find(key) ?: throw IllegalStateException()
        val key = objectify.save().entity(entity).now()
        return this.find(key)!!
    }

    override fun put(entity: T): T {
        val key = objectify.save().entity(entity).now()
        return this.find(key)!!
    }

    override fun batchPut(entities: List<T>): Result<MutableMap<Key<T>, T>>? {
        return objectify.save().entities(entities)
    }

    override fun delete(key: Key<T>) {
        objectify.delete().key(key)
    }

    override fun batchDeleteByKeys(keys: List<Key<T>>) {
        objectify.delete().keys(keys).now()
    }

    override fun batchDeleteByEntities(entities: List<T>) {
        objectify.delete().entities(entities).now()
    }

    override fun <E> searchByAncestorKey(ancestorKey: Key<E>): List<T> {
        return objectify.load().type(entityClass).ancestor(ancestorKey).list()
    }

}