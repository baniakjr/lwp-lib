package baniakjr.lwp

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LWPMaskTest {

    @Test
    fun shouldConstructProperMaskFromValues() {
        val expectedValue = (PlayVmOperation.BREAKS.value + PlayVmOperation.LIGHTS_OFF.value).toByte()

        val mask = LWPMask(PlayVmOperation.BREAKS, PlayVmOperation.LIGHTS_OFF)

        assertThat(mask.value).isEqualTo(expectedValue)
    }

    @Test
    fun shouldConstructProperMaskFromList() {
        val expectedValue = (PlayVmOperation.BREAKS.value + PlayVmOperation.LIGHTS_OFF.value).toByte()

        val mask = LWPMask(listOf(PlayVmOperation.BREAKS, PlayVmOperation.LIGHTS_OFF))

        assertThat(mask.value).isEqualTo(expectedValue)
    }

    @Test
    fun shouldAddProperValueToMask() {
        val expectedValue =
            (PlayVmOperation.BREAKS.value + PlayVmOperation.LIGHTS_OFF.value + PlayVmOperation.POWER_LIMIT.value).toByte()

        val mask = LWPMask(PlayVmOperation.BREAKS, PlayVmOperation.LIGHTS_OFF)
        mask.addValue(PlayVmOperation.POWER_LIMIT)

        assertThat(mask.value).isEqualTo(expectedValue)
    }

    @Test
    fun shouldCreateProperMaskFromByte() {
        val expectedValue = (PlayVmOperation.BREAKS.value + PlayVmOperation.LIGHTS_OFF.value).toByte()

        val mask = LWPMask.fromByte(PlayVmOperation::class.java, expectedValue)

        assertThat(mask.value).isEqualTo(expectedValue)
    }
}