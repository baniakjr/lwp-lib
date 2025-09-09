package baniakjr.lwp.definition.value

import baniakjr.lwp.definition.LWPByteValue

/**
 * Enum class representing Alert Operations for [Hub Alert Command][Command.ALERT]
 *
 * Reference: [https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#alert-operation](https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#alert-operation)
 *
 * @property value Byte value of the alert operation.
 */
enum class AlertOperation(override val value: Byte) : LWPByteValue {

    /** Enable updates. Value: 0x01 */
    ENABLE_UPDATES(0x01),
    /** Disable updates. Value: 0x02 */
    DISABLE_UPDATES(0x02),
    /** Request update. Value: 0x03 */
    REQUEST_UPDATE(0x03),
    /** Update. Value: 0x04 */
    UPDATE(0x04);

    companion object {
        /** Index of alert operation byte in Alert Command message */
        const val IN_MESSAGE_INDEX: Int = 4
    }

}
