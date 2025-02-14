package baniakjr.lwp

enum class ErrorCode(override val value: Byte) : LWPByteValue {
    ACK(0x01),
    MACK(0x02),
    BUFFER_OVERFLOW(0x03),
    TIMEOUT(0x04),
    COMMAND_NOT_RECOGNIZED(0x05),
    INVALID_USE(0x06),
    OVERCURRENT(0x07),
    INTERNAL_ERROR(0x08);

    companion object {
        const val ERROR_MSG_LENGTH: Int = 5

        const val COMMAND_IN_MESSAGE_INDEX: Int = 3
        const val IN_MESSAGE_INDEX: Int = 4
    }
}
