package jp.gr.java_conf.satok.objectify.sample.tool

import org.slf4j.LoggerFactory
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
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
        private const val SINGLE_LINE_DOC_REGEX: String = "/\\*\\* (.+) \\*/"
        private const val PARAMETER_REGEX: String = "(var|var)\\s+([a-zA-Z0-9_]+)\\s*:"
    }

    var classMetaList = mutableListOf<ClassMeta>()

    fun generateUmlFile() {
        val files = Files.list(Paths.get(ENTITY_PACKAGE_PATH))

        val df = SimpleDateFormat("yyyyMMdd-HHmmss")

        files.forEach {
            val kClass = Class.forName("$ENTITY_PACKAGE.${it.fileName.toString().split(".")[0]}").kotlin
            var entityClassName: String? = null
            kClass.annotations.filter { entityClassName == null }.forEach {
                if (it.annotationClass.simpleName == "Entity") {
                    entityClassName = kClass.simpleName
                }
            }

            var classMeta = ClassMeta(entityClassName!!, mutableListOf(), mutableMapOf())
            classMetaList.add(classMeta)

            kClass.memberProperties.forEach {
                val simpleClassName = "${it.returnType.toString().removePrefix("kotlin.").removePrefix("$OBJECTIFY_PACKAGE.").replace("$ENTITY_PACKAGE.", "")}"
                var propertyMeta = PropertyMeta(it.name, simpleClassName, mutableListOf(), 0, null)
                classMeta.propertyMetaMap[it.name] = propertyMeta
                it.javaField?.declaredAnnotations?.forEach {
                    propertyMeta.annotationNames.add(it.annotationClass.simpleName!!)
                }
            }
            generatePropertyMetaMap(it, classMeta)
        }

        val classDiagramFile = File("class_diagram_${df.format(Date())}.pu").absoluteFile
        this.classMetaList.forEach {
            logger.debug("class ${it.name} {")
            classDiagramFile.appendText("class ${it.name} {\n")
            it.propertyMetaMap.toList().sortedBy { it.second.order }.forEach {
                if(it.second.document != null) {
                    logger.debug("    /'${it.second.document}'/")
                    classDiagramFile.appendText("    /'${it.second.document}'/\n")
                }
                if(it.second.annotationNames.isNotEmpty()){
                    it.second.annotationNames.forEach {
                        logger.debug("    /'@$it'/")
                        classDiagramFile.appendText("    /'@$it'/\n")
                    }
                }
                logger.debug("    ${it.first}: ${it.second.typedClassName}")
                classDiagramFile.appendText("    ${it.first}: ${it.second.typedClassName}\n")
            }
            logger.debug("}")
            classDiagramFile.appendText("}\n")
            classDiagramFile.appendText("\n")
        }
    }

    fun generatePropertyMetaMap(path: Path, classMeta: ClassMeta): MutableMap<String, PropertyMeta> {
        var propertyMetaMap = mutableMapOf<String, PropertyMeta>()
        var desc: String? = null
        var order = 0
        val file = path.toFile()
        file.bufferedReader().use {
            it.lineSequence().filter(String::isNotBlank).forEach {
                SINGLE_LINE_DOC_REGEX.toRegex().find(it)?.destructured?.let { (description) ->
                    logger.debug(description)
                    desc = description
                }
                PARAMETER_REGEX.toRegex().find(it)?.destructured?.let { (def, name) ->
                    logger.debug("$def: $name")
                    order += 1
                    classMeta.propertyMetaMap[name]?.order = order
                    classMeta.propertyMetaMap[name]?.document = desc
                    desc = null
                }
            }
        }
        return propertyMetaMap
    }
}