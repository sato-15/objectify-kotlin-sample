package jp.gr.java_conf.satok.objectify.sample.tool

import org.slf4j.LoggerFactory
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.reflect.full.memberProperties

class UmlGenerator {

    companion object {
        val logger = LoggerFactory.getLogger(this::class.java.enclosingClass)!!
    }

    fun generateUmlFile(){
        val files = Files.list(Paths.get("src/main/kotlin/jp/gr/java_conf/satok/objectify/sample/entity/"))
        files.forEach{
            logger.debug(it.fileName.toString())
            val kClass = Class.forName("jp.gr.java_conf.satok.objectify.sample.entity." + it.fileName.toString().split(".")[0]).kotlin
            kClass.annotations.forEach(){
                logger.debug("${it.annotationClass.simpleName}")
            }
            kClass.memberProperties.forEach {
                logger.debug("${it.name}: ${it.returnType} (${it.annotations.size}, ${it.parameters.size})")
                it.getter.annotations.forEach{
                    logger.debug("${it.annotationClass}")
                }
            }
            kClass.typeParameters.forEach {
                logger.debug("${it.name}")
            }
        }
    }
}