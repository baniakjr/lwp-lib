package baniakjr.lwp

/**
 * Enum class representing the different operations that can be performed on a hub property.
 *
 * Reference: [https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#hub-property-operation](https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#hub-property-operation)
 * Reference: [https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#hub-property-and-operation](https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#hub-property-and-operation)
 *
 * @property value The byte value of the operation.
 */
enum class HubPropertyOperation(override val value: Byte) : LWPByteValue {
    SET(0x01),
    ENABLE_UPDATES(0x02),
    DISABLE_UPDATES(0x03),
    RESET(0x04),
    REQUEST_UPDATE(0x05),
    UPDATE(0x06);

    companion object {
        const val IN_MESSAGE_INDEX: Int = 4
    }
}
