package baniakjr.lwp.definition.value

import baniakjr.lwp.definition.LWPByteValue

/**
 * Enum representing the color of the LED on the hub. [Port HUB_LED][Port.HUB_LED]
 *
 * @property value Byte value of the LED color.
 */
enum class HubLedColor(override val value: Byte) : LWPByteValue {
    /** No color, turned off. Value: 0x00 */
    NONE(0x00),
    /** Pink. Value: 0x01 */
    PINK(0x01),
    /** Magenta. Value: 0x02 */
    MAGENTA(0x02),
    /** Blue. Value: 0x03 */
    BLUE(0x03),
    /** Light Blue. Value: 0x04 */
    LIGHT_BLUE(0x04),
    /** Cyan. Value: 0x05 */
    CYAN(0x05),
    /** Green. Value: 0x06 */
    GREEN(0x06),
    /** Yellow. Value: 0x07 */
    YELLOW(0x07),
    /** Orange. Value: 0x08 */
    ORANGE(0x08),
    /** Red. Value: 0x09 */
    RED(0x09),
    /** White. Value: 0x0A */
    WHITE(0xA);
}
