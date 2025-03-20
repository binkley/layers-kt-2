package hm.binkley.x

typealias Key = String // TODO: Reconsider moving back to generic type K

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

abstract class Rule<T : Any>(
    val name: String,
) : ValueOrRule<T>(),
    (String, Sequence<T>, Layers<T, *>) -> T {
    override fun toString() = "<Rule>$name"
}

interface Layer<V : Any, out L : Layer<V, L>> : Map<Key, ValueOrRule<V>> {
    val name: String

    @Suppress("UNCHECKED_CAST")
    val self: L get() = this as L
}

interface Layers<V : Any, L : Layer<V, L>> : Map<String, V> {
    val name: String
    val history: List<Layer<V, L>>
    val current: Layer<V, L>
}
