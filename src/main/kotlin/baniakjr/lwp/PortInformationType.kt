package baniakjr.lwp

enum class PortInformationType(override val value: Byte) : LWPByteValue {
    VALUE(0x00),
    MODE(0x01),
    COMBINATIONS(0x02);

    companion object {
        const val IN_MESSAGE_INDEX: Int = 4
    }
}
