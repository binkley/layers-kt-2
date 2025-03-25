package hm.binkley.layers

class MutableLayer(
    name: String,
    private val delegate: MutableMap<Key, ValueOrRule<*>> = mutableMapOf(),
) : Layer(name, delegate) {
    fun edit(block: EditMap.() -> Unit): MutableLayer {
        EditMap(delegate).block()
        return this
    }
}

class EditMap(
    private val delegate: MutableMap<Key, ValueOrRule<*>>,
) : MutableMap<Key, ValueOrRule<*>> by delegate {
    operator fun set(
        key: String,
        value: Any?,
    ) {
        if (null == value) {
            delegate.remove(key)
        } else {
            delegate[key] = value.toValue()
        }
    }
}
