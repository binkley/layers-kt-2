package hm.binkley.layers

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class ValuesTest {
    @Test
    fun `should make an int value`() {
        IntValue(1).value shouldBe 1
    }

    @Test
    fun `should make a string value`() {
        StringValue("TEST").value shouldBe "TEST"
    }
}
