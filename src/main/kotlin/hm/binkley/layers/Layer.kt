package hm.binkley.layers

open class Layer(
    val name: String,
    delegate: Map<Key, ValueOrRule<*>> = mapOf(),
) : Map<Key, ValueOrRule<*>> by delegate
