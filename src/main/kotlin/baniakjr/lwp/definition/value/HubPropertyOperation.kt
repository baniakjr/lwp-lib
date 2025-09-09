package baniakjr.lwp.definition.value

import baniakjr.lwp.definition.LWPByteValue

/**
 * Enum class representing the different operations that can be performed on a hub property.
 *
 * Reference: [https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#hub-property-operation](https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#hub-property-operation)
 * Reference: [https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#hub-property-and-operation](https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#hub-property-and-operation)
 *
 * @property value The byte value of the operation.
 */
enum class HubPropertyOperation(override val value: Byte) : LWPByteValue {
    /** Set property. Value: 0x01 */
    SET(0x01),
    /** Enable updates. Value: 0x02 */
    ENABLE_UPDATES(0x02),
    /** Disable updates. Value: 0x03 */
    DISABLE_UPDATES(0x03),
    /** Reset property. Value: 0x04 */
    RESET(0x04),
    /** Request update. Value: 0x05 */
    REQUEST_UPDATE(0x05),
    /** Update property. Value: 0x06 */
    UPDATE(0x06);

    companion object {
        const val IN_MESSAGE_INDEX: Int = 4
    }
}
