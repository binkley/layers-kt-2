package hm.binkley.layers

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.junit.jupiter.api.Test

internal class LayersTest {
    @Test
    fun `should make a default layers`() {
        Layers("TEST")
    }

    @Test
    fun `should start with a default layer to hold rules`() {
        val layers = Layers("TEST")
        layers.history.size shouldBe 1
        layers.history[0].name shouldBe "Default first layer"
    }

    @Test
    fun `TODO - put is not implemented`() {
        val exception =
            shouldThrow<NotImplementedError> {
                Layers("TEST").put("FOO", "BAR")
            }
        exception.message shouldContain "put"
    }

    @Test
    fun `TODO - entries is not implemented`() {
        val exception =
            shouldThrow<NotImplementedError> {
                Layers("TEST").entries
            }
        exception.message shouldContain "entries"
    }
}
