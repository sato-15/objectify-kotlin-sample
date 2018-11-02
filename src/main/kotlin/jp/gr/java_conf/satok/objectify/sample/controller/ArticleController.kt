package jp.gr.java_conf.satok.objectify.sample.controller

import com.googlecode.objectify.Key
import jp.gr.java_conf.satok.objectify.sample.entity.Article
import jp.gr.java_conf.satok.objectify.sample.repository.DatastoreArticleRepository
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}