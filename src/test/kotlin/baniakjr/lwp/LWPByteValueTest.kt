package baniakjr.lwp

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LWPByteValueTest {

    @Test
    fun shouldReturnProperEnum() {
        LWPByteValue.fromByte(Port::class.java, Port.DRIVE_MOTOR_1.value)?.let {
            assertThat(it).isEqualTo(Port.DRIVE_MOTOR_1)
        }
    }

    @Test
    fun shouldReturnNullIfNoEnum() {
        LWPByteValue.fromByte(Port::class.java, 0)?.let {
            assertThat(it).isNull()
        }
    }

}
