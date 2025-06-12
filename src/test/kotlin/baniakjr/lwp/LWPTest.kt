package baniakjr.lwp

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.provider.MethodSource

class LWPTest {

    @Test
    fun intFrom2ByteArray() {
        val inputArray = byteArrayOf(0x02, 0x01)

        val result = LWP.convertToInt16(inputArray)

        assertThat(result).isEqualTo(258)
    }

    @Test
    fun intFrom4ByteArray() {
        val inputArray = byteArrayOf(0x20,0x00, 0x00,0x00)

        val result = LWP.convertToInt32(inputArray)

        assertThat(result).isEqualTo(32)
    }

    @Test
    fun processHubName() {
        val inputArray = byteArrayOf(
            0x11,
            0x00,
            0x01,
            0x01,
            0x06,
            0x54,
            0x65,
            0x63,
            0x68,
            0x6e,
            0x69,
            0x63,
            0x20,
            0x4d,
            0x6f,
            0x76,
            0x65
        )

        val result = LWP.processHubName(inputArray)

        assertThat(result).isEqualTo("Technic Move")
    }

    @ParameterizedTest
    @MethodSource("getIntData")
    fun processBatterPercentage(expected: Int) {
        val inputArray = byteArrayOf(0x06, 0x00, 0x01, 0x06, 0x06, expected.toByte())

        val result = LWP.processBatterPercentage(inputArray)

        assertThat(result).isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("getFloatData")
    fun processTemperature(input: Float) {
        val expected = (input / 10f)
        val inputArray =
            byteArrayOf(0x0a, 0x00, 0x45, 0x37, (input.toInt() shr 0).toByte(), (input.toInt() shr 8).toByte())

        val result = LWP.processTemperature(inputArray)

        assertThat(result).isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("getFloatData")
    fun processBatteryVoltage(input: Float) {
        val expected = (input / 1000f)
        val inputArray =
            byteArrayOf(0x0a, 0x00, 0x45, 0x3C, (input.toInt() shr 0).toByte(), (input.toInt() shr 8).toByte())
        val result = LWP.processBatterVoltage(inputArray)

        assertThat(result).isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("getLedData")
    fun shouldReturnProperLEDCommand(mask: LWPMask<SixLed>, power: Byte) {
        val expectedArray = byteArrayOf(
            0x09,
            LWP.MESSAGE_HEADER,
            Command.PORT_OUTPUT.value,
            Port.SIX_LED.value,
            StartupCompletion.IMMEDIATE_NO_FEEDBACK.value,
            PortOutputSubCommand.WRITE_DIRECT_MODE.value,
            PortMode.MODE_0.value,
            mask.value,
            power
        )

        val result = LWP.setLedValuesCommand(mask, power)

        assertThat(result).isEqualTo(expectedArray)
    }

    @ParameterizedTest
    @EnumSource(HubLedColor::class)
    fun setHubLightColorCommand(color: HubLedColor) {
        val expectedArray = byteArrayOf(
            0x08,
            LWP.MESSAGE_HEADER,
            Command.PORT_OUTPUT.value,
            Port.HUB_LED.value,
            StartupCompletion.IMMEDIATE_NO_FEEDBACK.value,
            PortOutputSubCommand.WRITE_DIRECT_MODE.value,
            PortMode.MODE_0.value,
            color.value
        )

        val result = LWP.setHubLightColorCommand(color)

        assertThat(result).isEqualTo(expectedArray)
    }

    @ParameterizedTest
    @EnumSource(HubProperty::class)
    fun getHubPropertyCommand(hubProperty: HubProperty) {
        val expectedArray = byteArrayOf(
            0x05,
            LWP.MESSAGE_HEADER,
            Command.HUB_PROPERTY.value,
            hubProperty.value,
            HubPropertyOperation.REQUEST_UPDATE.value
        )

        val result = LWP.getHubPropertyCommand(hubProperty)

        assertThat(result).isEqualTo(expectedArray)
    }

    @ParameterizedTest
    @MethodSource("getSetPowerData")
    fun setPortPower(port: Port, power: Byte) {
        val expectedArray = byteArrayOf(
            0x08,
            LWP.MESSAGE_HEADER,
            Command.PORT_OUTPUT.value,
            port.value,
            StartupCompletion.IMMEDIATE_NO_FEEDBACK.value,
            PortOutputSubCommand.WRITE_DIRECT_MODE.value,
            PortMode.MODE_0.value,
            power
        )

        val result = LWP.setPortPower(port, power)

        assertThat(result).isEqualTo(expectedArray)
    }

    @Test
    fun initializePlayVM() {
        val expectedArray = byteArrayOf(
            0x0D,
            LWP.MESSAGE_HEADER,
            Command.PORT_OUTPUT.value,
            Port.PLAYVM.value,
            StartupCompletion.IMMEDIATE_NO_FEEDBACK.value,
            PortOutputSubCommand.WRITE_DIRECT_MODE.value,
            0x00, 0x03, 0x00, 0x00, 0x00,
            PlayVmOperation.INIT.value,
            0x00
        )

        val result = LWP.playVmInit()

        assertThat(result).isEqualTo(expectedArray)
    }

    @ParameterizedTest
    @MethodSource("getPlayVMData")
    fun setPlayVM(speed: Byte, steer: Byte, mask: LWPMask<PlayVmOperation>) {
        val expectedArray = byteArrayOf(
            0x0D,
            LWP.MESSAGE_HEADER,
            Command.PORT_OUTPUT.value,
            Port.PLAYVM.value,
            StartupCompletion.IMMEDIATE_NO_FEEDBACK.value,
            PortOutputSubCommand.WRITE_DIRECT_MODE.value,
            0x00, 0x03, 0x00,
            speed,
            steer,
            mask.value,
            0x00
        )

        val result = LWP.setPlayVM(speed, steer, mask)

        assertThat(result).isEqualTo(expectedArray)
    }

    @Test
    fun calibratePlayVM() {
        val expectedArray = byteArrayOf(
            0x0D,
            LWP.MESSAGE_HEADER,
            Command.PORT_OUTPUT.value,
            Port.PLAYVM.value,
            StartupCompletion.IMMEDIATE_NO_FEEDBACK.value,
            PortOutputSubCommand.WRITE_DIRECT_MODE.value,
            0x00, 0x03, 0x00, 0x00, 0x00,
            PlayVmOperation.CALIBRATE.value,
            0x00
        )

        val result = LWP.playVmAutoCalibrate()

        assertThat(result).isEqualTo(expectedArray)
    }

    @ParameterizedTest
    @MethodSource("getPortInformationData")
    fun portValueInformationRequest(port: Port, mode: PortInformationType) {
        val expectedArray = byteArrayOf(
            0x05,
            LWP.MESSAGE_HEADER,
            Command.PORT_INFORMATION_REQUEST.value,
            port.value,
            mode.value
        )

        val result = LWP.portValueInformationCommand(port, mode)

        assertThat(result).isEqualTo(expectedArray)
    }

    @ParameterizedTest
    @MethodSource("getVersionData")
    fun processVersionNumber(expectedVersion: String, inputArray: ByteArray) {
        val input = byteArrayOf(
            0x09,
            LWP.MESSAGE_HEADER,
            Command.HUB_PROPERTY.value,
            HubProperty.HW.value,
            HubPropertyOperation.UPDATE.value,
        ) + inputArray
        val result = LWP.processVersionNumber(input)

        assertThat(result).isEqualTo(expectedVersion)
    }

    @ParameterizedTest
    @MethodSource("getVersionData")
    fun convertToVersionNumber(expectedVersion: String, inputArray: ByteArray) {
        val result = LWP.convertToVersionNumber(inputArray)

        assertThat(result).isEqualTo(expectedVersion)
    }

    @ParameterizedTest
    @MethodSource("getSetPowerModeData")
    fun setPortPower(port: Port, power: Byte, mode : PortMode) {
        val expectedArray = byteArrayOf(
            0x08,
            LWP.MESSAGE_HEADER,
            Command.PORT_OUTPUT.value,
            port.value,
            StartupCompletion.IMMEDIATE_NO_FEEDBACK.value,
            PortOutputSubCommand.WRITE_DIRECT_MODE.value,
            mode.value,
            power
        )

        val result = LWP.setPortPowerMode(port, mode, power)

        assertThat(result).isEqualTo(expectedArray)
    }

    @ParameterizedTest
    @MethodSource("getOutputData")
    fun setOutputSubcommand(port: Port, subCommand: PortOutputSubCommand, data: ByteArray) {
        val size = 6 + data.size

        val expectedArray = byteArrayOf(
            size.toByte(),
            LWP.MESSAGE_HEADER,
            Command.PORT_OUTPUT.value,
            port.value,
            StartupCompletion.IMMEDIATE_NO_FEEDBACK.value,
            subCommand.value,
        ) + data

        val result = LWP.setOutputSubcommand(port, subCommand, data.toList())

        assertThat(result).isEqualTo(expectedArray)
    }

    @ParameterizedTest
    @MethodSource("getLWPVersionData")
    fun processLWPVersion(expectedVersion: String, inputArray: ByteArray) {
        val input = byteArrayOf(
            0x07,
            LWP.MESSAGE_HEADER,
            Command.HUB_PROPERTY.value,
            HubProperty.LWP.value,
            HubPropertyOperation.UPDATE.value,
        ) + inputArray
        val result = LWP.processLWPVersion(input)

        assertThat(result).isEqualTo(expectedVersion)
    }

    companion object {
        @JvmStatic
        fun getSetPowerData(): List<Arguments> {
            return listOf(
                Arguments.of(Port.DRIVE_MOTOR_1, 50.toByte()),
                Arguments.of(Port.DRIVE_MOTOR_2, 100.toByte()),
                Arguments.of(Port.STEERING_MOTOR, 0.toByte())
            )
        }

        @JvmStatic
        fun getSetPowerModeData(): List<Arguments> {
            return listOf(
                Arguments.of(Port.DRIVE_MOTOR_1, 23.toByte(), PortMode.MODE_0),
                Arguments.of(Port.DRIVE_MOTOR_2, 75.toByte(), PortMode.MODE_1),
                Arguments.of(Port.STEERING_MOTOR, 12.toByte(), PortMode.MODE_2)
            )
        }

        @JvmStatic
        fun getOutputData(): List<Arguments> {
            return listOf(
                Arguments.of(Port.DRIVE_MOTOR_1, PortOutputSubCommand.WRITE_DIRECT_MODE, byteArrayOf(0x00, 0x12, 0x56, 0x1F)),
                Arguments.of(Port.DRIVE_MOTOR_2, PortOutputSubCommand.GOTO_ABS_POS_SINGLE, byteArrayOf(0x20, 0x17, 0xF6.toByte())),
                Arguments.of(Port.STEERING_MOTOR, PortOutputSubCommand.WRITE_DIRECT_MODE, byteArrayOf(0x0A, 0x42))
            )
        }

        @JvmStatic
        fun getLedData(): List<Arguments> {
            return listOf(
                Arguments.of(LWPMask(SixLed.FRONT_INNER_RIGHT, SixLed.FRONT_INNER_LEFT), 50.toByte()),
                Arguments.of(LWPMask(SixLed.BACK_RIGHT), 100.toByte()),
                Arguments.of(LWPMask(SixLed.BACK_RIGHT, SixLed.FRONT_OUTER_LEFT, SixLed.FRONT_OUTER_RIGHT), 0.toByte())
            )
        }

        @JvmStatic
        fun getPlayVMData(): List<Arguments> {
            return listOf(
                Arguments.of(100.toByte(), 0.toByte(), LWPMask(PlayVmOperation.POWER_LIMIT, PlayVmOperation.LIGHTS_OFF)),
                Arguments.of(50.toByte(), 25.toByte(), LWPMask<PlayVmOperation>()),
                Arguments.of(34.toByte(), 14.toByte(), LWPMask(PlayVmOperation.BREAKS))
            )
        }

        @JvmStatic
        fun getPortInformationData(): List<Arguments> {
            return listOf(
                Arguments.of(Port.DRIVE_MOTOR_1, PortInformationType.VALUE),
                Arguments.of(Port.DRIVE_MOTOR_2, PortInformationType.MODE),
                Arguments.of(Port.STEERING_MOTOR, PortInformationType.VALUE)
            )
        }

        @JvmStatic
        fun getVersionData(): List<Arguments> {
            return listOf(
                Arguments.of("1.6.5.0", byteArrayOf(0x00, 0x00, 0x05, 0x16)),
                Arguments.of("1.7.37.1510", byteArrayOf(0x10, 0x15, 0x37, 0x17)),
                Arguments.of("1.0.23.1000", byteArrayOf(0x00, 0x10, 0x23, 0x10))
            )
        }

        @JvmStatic
        fun getLWPVersionData(): List<Arguments> {
            return listOf(
                Arguments.of("1.0", byteArrayOf(0x00, 0x01)),
                Arguments.of("16.21", byteArrayOf(0x15, 0x10)),
                Arguments.of("0.16", byteArrayOf(0x10, 0x00))
            )
        }

        @JvmStatic
        fun getFloatData(): List<Float> {
            return listOf(20f, 52f, 0f, 312f, 900f)
        }

        @JvmStatic
        fun getIntData(): List<Int> {
            return listOf(23, 57, 0, 100, 90)
        }
    }
}