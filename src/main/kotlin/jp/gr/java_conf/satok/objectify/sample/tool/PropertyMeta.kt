package jp.gr.java_conf.satok.objectify.sample.tool

data class PropertyMeta(
        var name: String,
        var typedClassName: String?,
        internal var annotationNames: MutableList<String>,
        var order: Int = 0,
        var document: String?
)