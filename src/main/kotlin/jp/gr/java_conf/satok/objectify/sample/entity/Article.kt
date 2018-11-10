package jp.gr.java_conf.satok.objectify.sample.entity

import com.googlecode.objectify.Key
import com.googlecode.objectify.annotation.Entity
import com.googlecode.objectify.annotation.Id
import com.googlecode.objectify.annotation.Index

@Entity
class Article {

    /** Property for Key.name */
    @Id
    lateinit var code: String

    /** Title */
    @Index
    lateinit var title: String

    fun getKey(): Key<Article> {
        return Key.create(Article::class.java, this.code)
    }

}