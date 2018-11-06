package jp.gr.java_conf.satok.objectify.sample.tool

import org.slf4j.LoggerFactory
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaField

class ClassDiagramGenerator {

    companion object {
        val logger = LoggerFactory.getLogger(this::class.java.enclosingClass)!!
        const val ENTITY_PACKAGE = "jp.gr.java_conf.satok.objectify.sample.entity"
        const val ENTITY_PACKAGE_PATH = "src/main/kotlin/jp/gr/java_conf/satok/objectify/sample/entity/"
    }

    fun generateUmlFile(){
        val files = Files.list(Paths.get(ENTITY_PACKAGE_PATH))

        files.forEach{
            val kClass = Class.forName("$ENTITY_PACKAGE.${it.fileName.toString().split(".")[0]}").kotlin
            var entityClassName: String? = null
            kClass.annotations.filter { entityClassName == null }.forEach {
                if (it.annotationClass.simpleName == "Entity") {
                    entityClassName = kClass.simpleName
                    logger.debug("class $entityClassName {")
                }
            }

            kClass.memberProperties.forEach {
                it.javaField?.declaredAnnotations?.forEach {
                    logger.debug("    /'${it.annotationClass.simpleName!!}'/")
                }
                logger.debug("    ${it.name}: ${it.returnType.toString().removePrefix("kotlin.")}")
            }
            logger.debug("}")
        }
    }
}