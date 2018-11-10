package jp.gr.java_conf.satok.objectify.sample.tool

data class ClassMeta (
    val name: String,
    val annotationNames: MutableList<Annotation>,
    val propertyMetaMap: MutableMap<String, PropertyMeta>
)