package baniakjr.lwp

/**
 * Enum class representing the ports on the LWP hub.
 */
enum class Port(override val value: Byte) : LWPByteValue {
    /** Port A. Drive motor Value: 0x32 */
    DRIVE_MOTOR_1(0x32),
    /** Port B. Drive motor Value: 0x33 */
    DRIVE_MOTOR_2(0x33),
    /** Port C. Steering motor Value: 0x34 */
    STEERING_MOTOR(0x34),
    /** Six LED Value: 0x35 */
    SIX_LED(0x35),
    /** PlayVm Virtual Port: 0x36 */
    PLAYVM(0x36),
    /** Temperature Sensor Value: 0x37 */
    TEMPERATURE(0x37),
    /** Accelerometer Sensor: 0x38 */
    ACCELEROMETER(0x38),
    /** Gyroscope Sensor: 0x39 */
    GYRO(0x39),
    /** Tilt Sensor: 0x3A */
    TILT(0x3A),
    /** Orientation Sensor: 0x3B */
    ORIENTATION(0x3B),
    /** Voltage meter: 0x3C */
    VOLTAGE(0x3C),
    /** GEST: 0x3D */
    GEST(0x3E),
    /** Hub LED: 0x3F */
    HUB_LED(0x3F);

    companion object {
        const val IN_INFORMATION_MESSAGE_INDEX: Int = 3
    }
}
