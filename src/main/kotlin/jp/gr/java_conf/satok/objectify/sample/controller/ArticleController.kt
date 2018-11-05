package jp.gr.java_conf.satok.objectify.sample.controller

import com.google.cloud.datastore.Cursor
import com.googlecode.objectify.Key
import jp.gr.java_conf.satok.objectify.sample.entity.Article
import jp.gr.java_conf.satok.objectify.sample.repository.DatastoreArticleRepository
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/articles")
@Suppress("unused")
class ArticleController(private val articleRepository: DatastoreArticleRepository) {

    companion object {
        val logger = LoggerFactory.getLogger(this::class.java.enclosingClass)!!
    }

    @GetMapping("{id}")
    fun find(@PathVariable("id") id: Long): Article? {
        return articleRepository.find(Key.create(Article::class.java, id))
    }

    @GetMapping("/")
    fun list(@RequestParam(name = "cursor", required = false) cursor: String?): MutableMap<String, Any> {
        val decodedCursor = if (cursor == null) null else Cursor.fromUrlSafe(cursor)
        val result = articleRepository.list(decodedCursor, 10)
        return mutableMapOf("articles" to result.first, "cursor" to (result.second?.toUrlSafe() ?: ""))
    }

}