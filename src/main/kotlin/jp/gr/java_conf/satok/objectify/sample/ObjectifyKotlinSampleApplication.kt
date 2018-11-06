package jp.gr.java_conf.satok.objectify.sample

import com.googlecode.objectify.ObjectifyService
import jp.gr.java_conf.satok.objectify.sample.entity.Article
import jp.gr.java_conf.satok.objectify.sample.tool.ClassDiagramGenerator
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@RestController
class ObjectifyKotlinSampleApplication {

    // FIXME: Initialize with Cass here
/*
    @Bean
    @Suppress("unused")
    fun articleRepository(): DatastoreArticleRepository {
        return DatastoreArticleRepository(Class<Article>)
    }
*/

}

fun main(args: Array<String>) {
    ObjectifyService.init()
    ObjectifyService.begin()
    ObjectifyService.register(Article::class.java)

    if (args.isNotEmpty() && args[0] == "generate_uml"){
        val gen = ClassDiagramGenerator()
        gen.generateUmlFile()
        return
    }

    runApplication<ObjectifyKotlinSampleApplication>(*args)
}
