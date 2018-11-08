package jp.gr.java_conf.satok.objectify.sample.tool

import org.slf4j.LoggerFactory
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.text.SimpleDateFormat
import java.util.*
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaField

class ClassDiagramGenerator {

    companion object {
        val logger = LoggerFactory.getLogger(this::class.java.enclosingClass)!!
        const val OBJECTIFY_PACKAGE = "com.googlecode.objectify"
        const val ENTITY_PACKAGE = "jp.gr.java_conf.satok.objectify.sample.entity"
        const val ENTITY_PACKAGE_PATH = "src/main/kotlin/jp/gr/java_conf/satok/objectify/sample/entity/"
    }

    fun generateUmlFile(){
        val files = Files.list(Paths.get(ENTITY_PACKAGE_PATH))

        val df = SimpleDateFormat("yyyyMMdd-HHmmss")
        val classDiagramFile = File("class_diagram_${df.format(Date())}.pu").absoluteFile


        files.forEach{
            val kClass = Class.forName("$ENTITY_PACKAGE.${it.fileName.toString().split(".")[0]}").kotlin
            var entityClassName: String? = null
            kClass.annotations.filter { entityClassName == null }.forEach {
                if (it.annotationClass.simpleName == "Entity") {
                    entityClassName = kClass.simpleName
                    logger.debug("class $entityClassName {")
                    classDiagramFile.appendText("class $entityClassName {\n")
                }
            }

            // TODO: retrieve KDoc of property

            kClass.memberProperties.forEach {
                it.javaField?.declaredAnnotations?.forEach {
                    logger.debug("    /'${it.annotationClass.simpleName!!}'/")
                    classDiagramFile.appendText("    /'${it.annotationClass.simpleName!!}'/\n")
                }
                val simpleClassName = "${it.returnType.toString().removePrefix("kotlin.").removePrefix("$OBJECTIFY_PACKAGE.").replace("$ENTITY_PACKAGE.","")}"
                logger.debug("    ${it.name}: $simpleClassName")
                classDiagramFile.appendText("    ${it.name}: $simpleClassName\n")
            }
            logger.debug("}")
            classDiagramFile.appendText("}\n")
            classDiagramFile.appendText("\n")
        }
    }
}