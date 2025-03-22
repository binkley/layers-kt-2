package hm.binkley.layers

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class RulesTest {
    @Test
    fun `should compile a rule`() {
        rule("<test>") { _, _, _ -> 1 }
    }

    @Test
    fun `should have a nice string representation`() {
        "${rule("<test>") { _, _, _ -> 1 }}" shouldBe "<Rule><test>"
    }

    @Test
    fun `should find most recent value`() {
        mostRecentRule<Int>()(
            "TEST",
            sequenceOf(0, 1),
            Layers("TEST"),
        ) shouldBe 1
    }
}
