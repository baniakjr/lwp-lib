package baniakjr.lwp

enum class HubLedColor(override val value: Byte) : LWPByteValue {
    NONE(0x00),
    PINK(0x01),
    MAGENTA(0x02),
    BLUE(0x03),
    LIGHT_BLUE(0x04),
    CYAN(0x05),
    GREEN(0x06),
    YELLOW(0x07),
    ORANGE(0x08),
    RED(0x09),
    WHITE(0xA);
}
