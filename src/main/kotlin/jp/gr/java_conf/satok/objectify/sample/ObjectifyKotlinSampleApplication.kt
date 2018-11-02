package jp.gr.java_conf.satok.objectify.sample

import com.googlecode.objectify.ObjectifyService
import jp.gr.java_conf.satok.objectify.sample.entity.Article
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@RestController
class ObjectifyKotlinSampleApplication

fun main(args: Array<String>) {
    ObjectifyService.init()
    ObjectifyService.begin()
    ObjectifyService.register(Article::class.java)

    runApplication<ObjectifyKotlinSampleApplication>(*args)
}
