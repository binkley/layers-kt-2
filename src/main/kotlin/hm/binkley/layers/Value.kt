package hm.binkley.layers

sealed interface Value<T : Any> {
    val value: T
}

data class IntValue(
    override val value: Int,
) : Value<Int>

data class StringValue(
    override val value: String,
) : Value<String>
