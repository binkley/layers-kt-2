package hm.binkley.layers

class MutableLayer(
    name: String,
    delegate: MutableMap<Key, ValueOrRule<*>> = mutableMapOf(),
) : Layer(name, delegate) {
    fun edit(block: EditMap.() -> Unit): MutableLayer {
        EditMap(delegate).block()
        return this
    }
}

class EditMap(
    private val delegate: MutableMap<Key, ValueOrRule<*>>,
) : MutableMap<Key, ValueOrRule<*>> by delegate {
    fun put(
        key: Key,
        value: Any?,
    ) {
        when (value) {
            null -> delegate.remove(key)
            is ValueOrRule<*> -> delegate[key] = value
            else -> delegate[key] = value.toValue()
        }
    }

    operator fun set(
        key: String,
        value: Any?,
    ) = put(key, value)
}
