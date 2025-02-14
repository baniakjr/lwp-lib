package baniakjr.lwp

enum class AlertType(override val value: Byte) : LWPByteValue {
    LOW_VOLTAGE(0x01),
    HIGH_CURRENT(0x02),
    LOW_SIGNAL(0x03),
    OVER_POWER(0x04);

    companion object {
        const val MSG_WO_DATA_LENGTH: Int = 5
        const val MAX_MSG_LENGTH: Int = MSG_WO_DATA_LENGTH + 1
        const val DATA_INDEX: Int = MSG_WO_DATA_LENGTH

        const val STATUS_ERROR: Byte = 0xFF.toByte()

        const val IN_MESSAGE_INDEX: Int = 3
    }
}
