package hm.binkley.layers

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class ValuesTest {
    @Test
    fun `should make an int value`() {
        1.toValue().value shouldBe 1
    }
}
