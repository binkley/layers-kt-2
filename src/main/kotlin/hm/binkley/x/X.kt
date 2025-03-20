package hm.binkley.x

import java.util.*

fun main() = Unit

sealed class ValueOrRule<V : Any> {
    abstract override fun toString(): String
}

data class Value<T : Any>(
    val value: T,
) : ValueOrRule<T>() {
    override fun toString() = "<Value>$value"
}

fun <T : Any> T.toValue() = Value(this)

abstract class Rule<K : Any, T : Any>(
    val name: String,
) : ValueOrRule<T>(),
    (K, Sequence<T>, Layers<K, T, *>) -> T {
    override fun toString() = "<Rule>$name"
}

interface Layer<K : Any, V : Any, out L : Layer<K, V, L>> :
    Map<K, ValueOrRule<V>> {
    val name: String

    @Suppress("UNCHECKED_CAST")
    val self: L get() = this as L
}

interface Layers<K : Any, V : Any, L : Layer<K, V, L>> : Map<K, V> {
    val name: String
    val history: Stack<Layer<K, V, L>>

    val current: Layer<K, V, L>
}
