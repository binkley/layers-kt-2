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

import kotlin.collections.MutableMap.MutableEntry

typealias Key = String // TODO: Reconsider moving back to generic type K

sealed interface Value<T : Any> {
    val value: T
}

data class IntValue(
    override val value: Int,
) : Value<Int>

data class StringValue(
    override val value: String,
) : Value<String>

fun interface RuleFun<T : Any> : (Key, Sequence<T>, Layers) -> T

open class Rule<T : Any>(
    val name: String,
    override val value: RuleFun<T>,
) : Value<RuleFun<T>> {
    override fun toString() = "<Rule>$name"

    operator fun invoke(
        key: Key,
        values: Sequence<T>,
        layers: Layers,
    ) = value(key, values, layers)
}

inline fun <T : Any> rule(
    name: String,
    crossinline ruleFun: (Key, Sequence<T>, Layers) -> T,
): Rule<T> = Rule(name) { key, values, layers -> ruleFun(key, values, layers) }

/** The default rule unless another is given for a key. */
fun <T : Any> mostRecentRule() =
    rule<T>("<most recent>") { _, values, _ -> values.last() }

/** A sample rule showing use of all three `RuleFun` parameters. */
fun sampleRuleAcrossKeys(vararg otherKeys: Key) =
    rule("<sample across keys>") { key, values, layers ->
        layers[key]!! as Int + otherKeys.sumOf { layers[it] as Int }
    }

open class Layer(
    val name: String,
    delegate: MutableMap<Key, Value<*>> = mutableMapOf(),
) : Map<Key, Value<*>> by delegate

open class Layers(
    val name: String,
    delegate: MutableList<Layer> = mutableListOf(),
) : AbstractMutableMap<Key, Any>() {
    val history: List<Layer> = delegate

    override fun put(
        key: Key,
        value: Any,
    ): Any? = TODO("Not yet implemented: put")

    override val entries: MutableSet<MutableEntry<Key, Any>>
        get() = TODO("Not yet implemented: entries")
}
