package baniakjr.lwp.definition.mode

import baniakjr.lwp.definition.Mode

/**
 * Enum class representing the different modes of the LED on the hub.
 *
 * @property value Byte value of the LED mode.
 */
enum class HubLedMode(override val value: Byte) : Mode {
    /** Discreet color [baniakjr.lwp.definition.value.HubLedColor]. Value: 0x00 */
    COLOR(0x00),
    /** RGB color. Color set as 3 byte value. Value: 0x01 */
    RGB(0x01);

    fun toPortMode(): PortMode {
        return when (this) {
            COLOR -> PortMode.MODE_0
            RGB -> PortMode.MODE_1
        }
    }
}
