package hm.binkley.layers

open class Layer(
    val name: String,
    protected val delegate: MutableMap<Key, ValueOrRule<*>> = mutableMapOf(),
) : Map<Key, ValueOrRule<*>> by delegate
