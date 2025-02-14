package baniakjr.lwp

enum class PortMode(override val value: Byte) : Mode {
    MODE_0(0x00),
    MODE_1(0x01),
    MODE_2(0x02),
    MODE_3(0x03),
    MODE_4(0x04);
}
