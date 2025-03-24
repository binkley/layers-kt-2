package hm.binkley.layers

class MutableLayer(
    name: String,
) : Layer(name) {
    fun edit(block: EditMap) = this
}

class EditMap
