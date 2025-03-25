package hm.binkley.layers

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class MutableLayerTest {
    @Test
    fun `should return self for edits`() {
        val layer = MutableLayer("TEST")
        val returned = layer.edit { }
        returned shouldBe layer
    }

    @Test
    fun `should add or update key values`() {
        val layer = MutableLayer("TEST")
        layer.edit {
            this["FOO"] = "BAR"
        }
        layer["FOO"] shouldBe "BAR".toValue()
    }

    @Test
    fun `should delete keys`() {
        val layer = MutableLayer("TEST", mutableMapOf("FOO" to "BAR".toValue()))
        layer.edit {
            this["FOO"] = null
        }
        layer.containsKey("FOO") shouldBe false
    }
}
