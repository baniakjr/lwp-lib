package baniakjr.lwp

/**
 * Enum class representing the ports on the LWP hub.
 */
enum class Port(override val value: Byte) : LWPByteValue {
    /** Port A. Drive motor. Value: 0x32 */
    DRIVE_MOTOR_1(0x32),
    /** Port B. Drive motor. Value: 0x33 */
    DRIVE_MOTOR_2(0x33),
    /** Port C. Steering motor. Value: 0x34 */
    STEERING_MOTOR(0x34),
    /** Six LED. Value: 0x35 */
    SIX_LED(0x35),
    /** PlayVm Virtual Port. Value: 0x36 */
    PLAYVM(0x36),
    /** Temperature Sensor. Value: 0x37 */
    TEMPERATURE(0x37),
    /** Accelerometer Sensor. Value: 0x38 */
    ACCELEROMETER(0x38),
    /** Gyroscope Sensor. Value: 0x39 */
    GYRO(0x39),
    /** Tilt Sensor. Value: 0x3A */
    TILT(0x3A),
    /** Orientation Sensor. Value: 0x3B */
    ORIENTATION(0x3B),
    /** Voltage meter. Value: 0x3C */
    VOLTAGE(0x3C),
    /** Unknown port. Value: 0x3D 
     * 
     * Used by Control+ app with LEGO Porsche 42176 (LEGO Technic Move Hub 88019)
     */
    PORT_3D(0x3D),
    /** GEST. Value: 0x3E */
    GEST(0x3E),
    /** Hub LED. Value: 0x3F */
    HUB_LED(0x3F);

    companion object {
        const val IN_INFORMATION_MESSAGE_INDEX: Int = 3
    }
}
