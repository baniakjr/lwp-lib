package baniakjr.lwp.definition.value

import baniakjr.lwp.definition.LWPByteValue

/**
 * Enum representing mode of [virtual port setup command][Command.VIRTUAL_PORT_SETUP]
 *
 * * Reference: [https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#virtual-port-setup](https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#virtual-port-setup)
 *
 * @property value Byte value of the LED color.
 */
enum class VirtualPortSetupMode(override val value: Byte) : LWPByteValue {
    /** Disconnect virtual port. Value: 0x00 */
    DISCONNECT(0x00),
    /** Connect/create virtual port. Value: 0x01 */
    CONNECT(0x01);

    companion object {
        /** Index of mode byte in [virtual port setup command][Command.VIRTUAL_PORT_SETUP] */
        const val IN_MESSAGE_INDEX: Int = 3

        const val DISCONNECT_MSG_LENGTH: Int = 5
        const val CONNECT_MSG_LENGTH: Int = 6
    }
}