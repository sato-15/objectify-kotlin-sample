package jp.gr.java_conf.satok.objectify.sample.repository

import jp.gr.java_conf.satok.objectify.sample.entity.Article
import org.springframework.stereotype.Repository

@Repository
@Suppress("unused")
class DatastoreArticleRepository(override val entityClass: Class<Article>) : BaseDatastoreRepository<Article>() {

    fun searchByTitle(title: String): MutableList<Article>? {
        return objectify.load().type(entityClass).filter("title =", title).list()
    }

}