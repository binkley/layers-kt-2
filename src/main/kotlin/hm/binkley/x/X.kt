package hm.binkley.x

typealias Key = String // TODO: Reconsider moving back to generic type K

fun main() = Unit

sealed class Value<T : Any>(
    val value: T,
) {
    override fun toString(): String = "<Value>$value"
}

interface RuleFun<T : Any> : (Key, Sequence<T>, Layers<T, *>) -> T

abstract class Rule<T : Any>(
    val name: String,
    rule: RuleFun<T>,
) : Value<RuleFun<T>>(rule) {
    override fun toString() = "<Rule>$name"
}

interface Layer<T : Any, out L : Layer<T, L>> : Map<Key, Value<T>> {
    val name: String

    @Suppress("UNCHECKED_CAST")
    val self: L get() = this as L
}

interface Layers<T : Any, L : Layer<T, L>> : Map<Key, T> {
    val name: String
    val history: List<Layer<T, L>>
    val current: Layer<T, L>
}
