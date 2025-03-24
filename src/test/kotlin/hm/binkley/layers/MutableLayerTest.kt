package hm.binkley.layers

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class MutableLayerTest {
    @Test
    fun `should return self for edits`() {
        val layer = MutableLayer("TEST")
        layer.edit(EditMap()) shouldBe layer
    }
}
