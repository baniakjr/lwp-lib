package baniakjr.lwp

enum class Port(override val value: Byte) : LWPByteValue {
    DRIVE_MOTOR_1(0x32),
    DRIVE_MOTOR_2(0x33),
    STEERING_MOTOR(0x34),
    SIX_LED(0x35),
    PLAYVM(0x36),
    TEMPERATURE(0x37),
    ACCELEROMETER(0x38),
    GYRO(0x39),
    TILT(0x3A),
    ORIENTATION(0x3B),
    VOLTAGE(0x3C),
    GEST(0x3E),
    HUB_LED(0x3F);

    companion object {
        const val IN_INFORMATION_MESSAGE_INDEX: Int = 3
    }
}
