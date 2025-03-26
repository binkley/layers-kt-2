package hm.binkley.layers

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class LayerTest {
    @Test
    @Suppress("USELESS_IS_CHECK")
    fun `should behave as a standard map`() {
        (Layer("TEST") is Map<Key, *>) shouldBe true
        (MutableLayer("TEST") !is MutableMap<Key, *>) shouldBe true
    }

    @Test
    fun `should make a default layer`() {
        val layer = Layer("TEST")
        layer.containsKey("FOO") shouldBe false
    }
}
