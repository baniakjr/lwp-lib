package baniakjr.lwp

/**
 * Enum representing value format for [PortModeInformation][Command.PORT_MODE_INFORMATION]
 *
 * * Reference: [https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#val-fmt](https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#val-fmt)
 *
 * @property value Byte value of the format.
 */
enum class ValueFormat(override val value: Byte) : LWPByteValue {
    /** Single byte. Value: 0x00 */
    SINGLE_BYTE(0x00),
    /** Two byte int. Value: 0x01 */
    INT_16(0x01),
    /** Four byte int. Value: 0x02 */
    INT_32(0x02),
    /** Float. Value: 0x03 */
    FLOAT(0x03),;
}