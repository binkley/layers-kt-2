package hm.binkley.x

typealias Key = String // TODO: Reconsider moving back to generic type K

fun main() = Unit

sealed class ValueOrRule<T : Any> {
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

interface Layer<T : Any, out L : Layer<T, L>> : Map<Key, ValueOrRule<T>> {
    val name: String

    @Suppress("UNCHECKED_CAST")
    val self: L get() = this as L
}

interface Layers<T : Any, L : Layer<T, L>> : Map<String, T> {
    val name: String
    val history: List<Layer<T, L>>
    val current: Layer<T, L>
}
