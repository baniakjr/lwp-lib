package baniakjr.lwp

enum class HubProperty(override val value: Byte) : LWPByteValue {
    NAME(0x01),
    BUTTON(0x02),
    FW(0x03),
    HW(0x04),
    RSSI(0x05),
    BATTERY_LEVEL(0x06),
    BATTERY_TYPE(0x07),
    MANUFACTURER(0x08),
    RADIO_FW(0x09),
    LWP(0x0A),
    SYSTEM_TYPE(0x0B),
    HW_NETWORK_ID(0x0C),
    MAC_1(0x0D),
    MAC_2(0x0E),
    HW_NETWORK_FAMILY(0x0F);

    companion object {
        const val MSG_WO_DATA_LENGTH: Int = 5
        const val MINIMAL_MSG_LENGTH: Int = MSG_WO_DATA_LENGTH + 1
        const val DATA_START_INDEX: Int = MSG_WO_DATA_LENGTH

        const val IN_MESSAGE_INDEX: Int = 3
    }
}
