package baniakjr.lwp

enum class AlertOperation(override val value: Byte) : LWPByteValue {

    ENABLE_UPDATES(0x01),
    DISABLE_UPDATES(0x02),
    REQUEST_UPDATE(0x03),
    UPDATE(0x04);

    companion object {
        const val IN_MESSAGE_INDEX: Int = 4
    }

}
