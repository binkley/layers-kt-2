package hm.binkley.layers

sealed interface ValueOrRule<T : Any> {
    val value: T
}

data class Value<T : Any>(
    override val value: T,
) : ValueOrRule<T>

fun <T : Any> T.toValue() = Value(this)
