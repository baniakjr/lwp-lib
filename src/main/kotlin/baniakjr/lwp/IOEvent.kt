package baniakjr.lwp

/**
 * Enum class representing event types for [Hub Attached IO Command][Command.ATTACHED_IO]
 *
 * Reference: [https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#io-evt](https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#io-evt)
 *
 * @property value Byte value of the event type.
 */
enum class IOEvent(override val value: Byte) : LWPByteValue {
    /** Detached I/O. Value: 0x00 */
    DETACHED(0x00),
    /** Attached I/O. Value: 0x01 */
    ATTACHED(0x01),
    /** Attached Virtual I/O. Value: 0x03 */
    ATTACHED_VIRTUAL(0x02);

    companion object {
        const val DETACHED_MSG_LENGTH: Int = 5
        const val ATTACHED_MSG_LENGTH: Int = 15
        const val ATTACHED_VIRTUAL_MSG_LENGTH: Int = 9


        /** Index of port byte in [Hub Attached IO Command][Command.ATTACHED_IO] */
        const val PORT_IN_MESSAGE_INDEX: Int = 3
        /** Start index of I/O type bytes (2 bytes) in [Hub Attached IO Command][Command.ATTACHED_IO] */
        const val IO_TYPE_IN_MESSAGE_INDEX: Int = 5
        /** Index of first port byte forming virtual port in [Hub Attached IO Command][Command.ATTACHED_IO] */
        const val V_PORT_A_IN_MESSAGE_INDEX: Int = 7
        /** Index of second port byte forming virtual port in [Hub Attached IO Command][Command.ATTACHED_IO] */
        const val V_PORT_B_IN_MESSAGE_INDEX: Int = 8
        /** Start index of HW revision bytes (4 bytes) in [Hub Attached IO Command][Command.ATTACHED_IO] */
        const val HW_REV_IN_MESSAGE_INDEX: Int = 7
        /** Start index of SW revision bytes (4 bytes) in [Hub Attached IO Command][Command.ATTACHED_IO] */
        const val SW_REV_IN_MESSAGE_INDEX: Int = 11
        /** Index of error code byte in [Hub Attached IO Command][Command.ATTACHED_IO] */
        const val IN_MESSAGE_INDEX: Int = 4
    }
}