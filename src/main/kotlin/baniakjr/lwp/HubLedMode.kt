package baniakjr.lwp

enum class HubLedMode(override val value: Byte) : Mode {
    COLOR(0x00),
    RGB(0x01);
}
