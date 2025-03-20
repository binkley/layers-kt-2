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

interface RuleFun<T : Any> : (Key, Sequence<T>, Layers<T, *>) -> T

abstract class Rule<T : Any>(
    val name: String,
    override val value: RuleFun<T>,
) : Value<RuleFun<T>> {
    override fun toString() = "<Rule>$name"
}

abstract class Layer<T : Any, out L : Layer<T, L>>(
    val name: String,
) : Map<Key, Value<T>> {
    @Suppress("UNCHECKED_CAST")
    val self: L get() = this as L
}

abstract class Layers<T : Any, L : Layer<T, L>>(
    val name: String,
) : Map<Key, T> {
    abstract val history: List<Layer<T, L>>
    abstract val current: Layer<T, L>
}
