package baniakjr.lwp.definition.value

import baniakjr.lwp.definition.LWPByteValue

/**
 * Enum class representing error codes returned by [Generic Error Command][Command.ERROR]
 *
 * Reference: [https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#error-codes](https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#error-codes)
 *
 * @property value Byte value of the error code.
 */
enum class ErrorCode(override val value: Byte) : LWPByteValue {
    /** ACK. Value: 0x01 */
    ACK(0x01),
    /** MACK. Value: 0x02 */
    MACK(0x02),
    /** Buffer Overflow. Value: 0x03 */
    BUFFER_OVERFLOW(0x03),
    /** Timeout. Value: 0x04 */
    TIMEOUT(0x04),
    /** Command Not Recognized. Value: 0x05 */
    COMMAND_NOT_RECOGNIZED(0x05),
    /** Invalid Use. Value: 0x06 */
    INVALID_USE(0x06),
    /** Overcurrent. Value: 0x07 */
    OVERCURRENT(0x07),
    /** Internal Error. Value: 0x08 */
    INTERNAL_ERROR(0x08);

    companion object {
        const val ERROR_MSG_LENGTH: Int = 5

        /** Index of command byte in [Error Command message][Command.ERROR] */
        const val COMMAND_IN_MESSAGE_INDEX: Int = 3
        /** Index of error code byte in [Error Command message][Command.ERROR] */
        const val IN_MESSAGE_INDEX: Int = 4
    }
}
