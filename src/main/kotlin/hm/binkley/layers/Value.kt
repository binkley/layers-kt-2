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
