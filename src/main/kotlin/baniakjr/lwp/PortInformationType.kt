package baniakjr.lwp

/**
 * Enum class representing the different types of information that can be requested from a port by [Port Information Request][Command.PORT_INFORMATION_REQUEST]
 *
 * Reference: [https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#information-type](https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#information-type)
 *
 * @property value The byte value of the enum.
 */
enum class PortInformationType(override val value: Byte) : LWPByteValue {
    /** Port Value. Value: 0x00 */
    VALUE(0x00),
    /** Mode Information. Value: 0x01 */
    MODE(0x01),
    /** Possible Combinations. Value: 0x02 */
    COMBINATIONS(0x02);

    companion object {
        /** Index of information type byte in [Port Information Request message][Command.PORT_INFORMATION_REQUEST] */
        const val IN_MESSAGE_INDEX: Int = 4
    }
}