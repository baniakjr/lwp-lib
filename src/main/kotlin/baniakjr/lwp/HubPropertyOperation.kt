package baniakjr.lwp

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
