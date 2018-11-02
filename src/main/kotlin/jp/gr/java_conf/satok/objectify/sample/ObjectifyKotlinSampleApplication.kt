package jp.gr.java_conf.satok.objectify.sample

import com.googlecode.objectify.ObjectifyService
import jp.gr.java_conf.satok.objectify.sample.entity.Article
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ObjectifyKotlinSampleApplication

fun main(args: Array<String>) {
    ObjectifyService.init()
    ObjectifyService.begin()
    ObjectifyService.register(Article::class.java)

    runApplication<ObjectifyKotlinSampleApplication>(*args)
}
