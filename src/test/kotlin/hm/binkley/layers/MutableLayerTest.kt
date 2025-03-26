package hm.binkley.layers

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class MutableLayerTest {
    @Test
    @Suppress("USELESS_IS_CHECK")
    fun `should behave as a standard map`() {
        (MutableLayer("TEST") is Map<Key, *>) shouldBe true
        (MutableLayer("TEST") !is MutableMap<Key, *>) shouldBe true
    }

    @Test
    fun `should return self for edits`() {
        val layer = MutableLayer("TEST")
        val returned = layer.edit { }
        returned shouldBe layer
    }

    @Test
    fun `should add or update a plain value`() {
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

    @Test
    fun `should add or update a rule`() {
        val layer = MutableLayer("TEST")
        // Cache value for comparison -- each call makes a new lambda object
        val mostRecentRule = mostRecentRule<Int>()
        layer.edit {
            this["FOO"] = mostRecentRule
        }
        layer["FOO"] shouldBe mostRecentRule
    }
}
