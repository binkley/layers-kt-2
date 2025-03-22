package hm.binkley.layers

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class LayerTest {
    @Test
    fun `should make a default layer`() {
        val layer = Layer<String>("TEST")
        layer.containsKey("FOO") shouldBe false
    }
}
