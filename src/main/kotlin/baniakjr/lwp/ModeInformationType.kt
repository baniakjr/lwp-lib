package baniakjr.lwp

/**
 * Enum class representing the different types of mode information that can be requested by [Port Mode Information Request][Command.PORT_MODE_INFORMATION_REQUEST].
 *
 * Reference: [https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#mode-information-types](https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#mode-information-types)
 * Reference: [https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#port-mode-information-request](https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#port-mode-information-request)
 *
 * @property value The byte value of the information type.
 */
enum class ModeInformationType(override val value: Byte) : LWPByteValue {

    /** Name. Value: 0x00 */
    NAME(0x00),
    /** RAW. Value: 0x01 */
    RAW(0x01),
    /** PCT. Percentage Value: 0x02 */
    PCT(0x02),
    /** SI. System International Value: 0x03 */
    SI(0x03),
    /** SYMBOL. Value: 0x04 */
    SYMBOL(0x04),
    /** MAPPING. Value: 0x05 */
    MAPPING(0x05),
    /** BIAS. Value: 0x06 */
    BIAS(0x07),
    /** CAPABILITY. Value: 0x08 */
    CAPABILITY(0x08),
    /** VALUE FORMAT. Value: 0x80 */
    FORMAT(0x80.toByte());

    companion object {
        /** Index of information type byte in [Port Mode Information Request message][Command.PORT_MODE_INFORMATION_REQUEST] */
        const val IN_MESSAGE_INDEX: Int = 5
        const val LENGTH_WO_DATA: Int = 6
        const val DATA_START_INDEX: Int = 6
    }
}
