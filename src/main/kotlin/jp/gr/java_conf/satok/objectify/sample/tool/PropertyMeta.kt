package jp.gr.java_conf.satok.objectify.sample.tool

data class PropertyMeta(
        val name: String,
        val annotationNames: MutableList<Annotation>,
        val order: Int = 0,
        var document: String
)