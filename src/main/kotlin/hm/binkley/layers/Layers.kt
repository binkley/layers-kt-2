package hm.binkley.layers

typealias Key = String // TODO: Reconsider moving back to generic type K

open class Layers(
    val name: String,
    delegate: MutableList<Layer> = mutableListOf(),
    firstLayer: Layer = Layer("Default first layer"),
) : AbstractMutableMap<Key, Any>() {
    val history: MutableList<Layer> = delegate

    init {
        history.add(firstLayer)
    }

    override fun put(
        key: Key,
        value: Any,
    ): Any? = TODO("Not yet implemented: put")

    override val entries: MutableSet<MutableMap.MutableEntry<Key, Any>>
        get() = TODO("Not yet implemented: entries")
}
