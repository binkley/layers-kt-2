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

sealed interface Value<T : Any> {
    val value: T
}

abstract class ValueBase<T : Any> : Value<T> {
    override fun toString(): String = "<Value>$value"
}

data class IntValue(
    override val value: Int,
) : ValueBase<Int>()

data class StringValue(
    override val value: String,
) : ValueBase<String>()

fun interface RuleFun<T : Any> : (Key, Sequence<T>, Layers<T>) -> T

open class Rule<T : Any>(
    val name: String,
    override val value: RuleFun<T>,
) : Value<RuleFun<T>> {
    override fun toString() = "<Rule>$name"

    operator fun invoke(
        key: Key,
        values: Sequence<T>,
        layers: Layers<T>,
    ) = value(key, values, layers)
}

inline fun <T : Any> rule(
    name: String,
    crossinline ruleFun: (Key, Sequence<T>, Layers<T>) -> T,
): Rule<T> = Rule(name) { key, values, layers -> ruleFun(key, values, layers) }

/** The default rule unless another is given for a key. */
fun <T : Any> mostRecentRule() =
    rule<T>("<most recent>") { _, values, _ -> values.last() }

/** A sample rule showing use of all three `RuleFun` parameters. */
fun sampleRuleAcrossKeys(vararg otherKeys: Key) =
    rule<Int>("<sample across keys>") { key, values, layers ->
        layers[key]!! + otherKeys.mapNotNull { layers[it] }.sum()
    }

open class Layer<T : Any>(
    val name: String,
    delegate: MutableMap<Key, Value<T>> = mutableMapOf(),
) : Map<Key, Value<T>> by delegate

open class Layers<T : Any>(
    val name: String,
    delegate: MutableList<Layer<T>> = mutableListOf(),
) : AbstractMutableMap<Key, T>() {
    val history: List<Layer<T>> = delegate

    override fun put(
        key: Key,
        value: T,
    ): T? {
        TODO("Not yet implemented")
    }

    override val entries: MutableSet<MutableMap.MutableEntry<Key, T>>
        get() = TODO("Not yet implemented")
}
