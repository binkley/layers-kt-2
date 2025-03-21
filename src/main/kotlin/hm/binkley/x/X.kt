package hm.binkley.x

typealias Key = String // TODO: Reconsider moving back to generic type K

fun main() = Unit

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

fun interface RuleFun<T : Any> : (Key, Sequence<T>, Layers<T, *>) -> T

open class Rule<T : Any>(
    val name: String,
    override val value: RuleFun<T>,
) : Value<RuleFun<T>> {
    override fun toString() = "<Rule>$name"
}

inline fun <T : Any> rule(
    name: String,
    crossinline ruleFun: (Key, Sequence<T>, Layers<T, *>) -> T,
): Rule<T> = Rule(name) { key, values, layers -> ruleFun(key, values, layers) }

fun <T : Any> mostRecentRule() =
    rule<T>("<most recent>") { _, values, _ -> values.last() }

open class Layer<T : Any, out L : Layer<T, L>>(
    val name: String,
    delegate: MutableMap<Key, Value<T>> = mutableMapOf(),
) : Map<Key, Value<T>> by delegate {
    @Suppress("UNCHECKED_CAST")
    val self: L get() = this as L
}

open class Layers<T : Any, L : Layer<T, L>>(
    val name: String,
    delegate: MutableList<Layer<T, L>> = mutableListOf(),
) : AbstractMutableMap<Key, T>() {
    val history: List<Layer<T, L>> = delegate

    override fun put(
        key: Key,
        value: T,
    ): T? {
        TODO("Not yet implemented")
    }

    override val entries: MutableSet<MutableMap.MutableEntry<Key, T>>
        get() = TODO("Not yet implemented")
}
