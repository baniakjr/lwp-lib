package baniakjr.lwp

enum class InformationType(override val value: Byte) : LWPByteValue {
    NAME(0x00),
    RAW(0x01),
    PCT(0x02),
    SI(0x03),
    SYMBOL(0x04),
    MAPPING(0x05),
    BIAS(0x07),
    CAPABILITY(0x08),
    FORMAT(0x80.toByte());

    companion object {
        const val IN_MESSAGE_INDEX: Int = 5
    }
}
