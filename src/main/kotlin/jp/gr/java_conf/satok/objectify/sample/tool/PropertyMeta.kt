package jp.gr.java_conf.satok.objectify.sample.tool

data class PropertyMeta(
        var name: String,
        var annotationNames: MutableList<Annotation>,
        var order: Int = 0,
        var document: String?
)