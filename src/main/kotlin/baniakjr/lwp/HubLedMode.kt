package baniakjr.lwp

/**
 * Enum class representing the different modes of the LED on the hub.
 *
 * @property value Byte value of the LED mode.
 */
enum class HubLedMode(override val value: Byte) : Mode {
    /** Discreet color [HubLedColor]. Value: 0x00 */
    COLOR(0x00),
    /** RGB color. Color set as 3 byte value. Value: 0x01 */
    RGB(0x01);
}
