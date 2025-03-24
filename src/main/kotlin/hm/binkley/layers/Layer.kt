package hm.binkley.layers

open class Layer(
    val name: String,
    delegate: MutableMap<Key, Value<*>> = mutableMapOf(),
) : Map<Key, Value<*>> by delegate
