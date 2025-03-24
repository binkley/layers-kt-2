/**
 * Design choices are driven by _Clean Code_ principles.
 * Among those choices include goals of:
 * - Idiomatic code
 * - Simple, clear API user experience
 * - Push problems to compile time when possible
 * - Push complexity into the library away from API users
 * - Consistent code and patterns
 * - Extensibility, ie, open-close principle
 * - Type safety
 * - Avoid `null` unless required by a public API in the stdlib
 * - Use design-by-contract patterns
 * - Good naming
 */
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
