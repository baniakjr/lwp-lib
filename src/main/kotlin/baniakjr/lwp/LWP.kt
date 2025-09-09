package baniakjr.lwp

import baniakjr.lwp.LWPByteValue.Companion.wrap
import baniakjr.lwp.model.LWPCommand.Companion.createCommand
import baniakjr.lwp.model.command.AlertCommand
import baniakjr.lwp.model.command.GenericCommand
import baniakjr.lwp.model.command.HubPropertyCommand
import baniakjr.lwp.model.command.PortInformationRequestCommand
import baniakjr.lwp.model.command.output.GenericPortOutputCommand
import baniakjr.lwp.model.command.output.HubLedCommand
import baniakjr.lwp.model.command.output.PlayVMCommand
import baniakjr.lwp.model.command.output.SixLedCommand
import baniakjr.lwp.model.command.output.WriteDirectModeCommand
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.charset.StandardCharsets
import java.util.Arrays
import java.util.HexFormat
import java.util.Locale

/**
 * LWP constants and helper functions
 * @see [LWP](https://lego.github.io/lego-ble-wireless-protocol-docs/index.html)
 */
object LWP {

    /** String value of UUID of LEGO Hub Characteristic
     *
     * Reference: [https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#lego-specific-gatt-service](https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#lego-specific-gatt-service)
     *
     * Value: 00001624-1212-efde-1623-785feabcd123
     */
    const val CHARACTERISTIC_UUID: String = "00001624-1212-efde-1623-785feabcd123"

    /** String value of UUID of LEGO Hub Service
     *
     * Reference: [https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#lego-specific-gatt-service](https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#lego-specific-gatt-service)
     *
     * Value: 00001623-1212-efde-1623-785feabcd123
     */
    const val SERVICE_UUID: String = "00001623-1212-efde-1623-785feabcd123"

    /** Constant part of common message header
     *
     * Reference: [https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#common-message-header](https://lego.github.io/lego-ble-wireless-protocol-docs/index.html#common-message-header)
     *
     * Value: 0x00
     */
    const val MESSAGE_HEADER: Byte = 0x00

    const val MINIMAL_MSG_LENGTH: Int = 3

    const val MAX_POWER: Int = 100
    const val MIN_POWER: Int = 0

    const val MAX_POWER_BYTES: Byte = MAX_POWER.toByte()
    const val MIN_POWER_BYTES: Byte = MIN_POWER.toByte()

    const val FLOAT: Byte = 0
    const val HOLD: Byte = 126
    const val BREAK: Byte = 127

    private val hexFormatter: HexFormat = HexFormat.of().withUpperCase().withDelimiter(":")

    /**
     * Creates command to set the 6 leds light brightness
     * @param value Byte - Brightness value
     * @param mask LWPMask<SixLed> - Mask for the leds to set
     * @param startupCompletion StartupCompletion - Startup completion type for command. Default Immediate no feedback
     * @return ByteArray LWP Command
     */
    @JvmOverloads
    @JvmStatic
    fun setLedValuesCommand(
        mask: LWPMask<SixLed>,
        value: Byte,
        startupCompletion: StartupCompletion = StartupCompletion.IMMEDIATE_NO_FEEDBACK
    ): ByteArray {
        return SixLedCommand.setBrightness(mask, value, startupCompletion).byteValue
    }

    /**
     * Creates command to set the hub light color
     * @param color HubLedColor - Color to set
     * @param startupCompletion StartupCompletion - Startup completion type for command. Default Immediate no feedback
     * @return ByteArray LWP Command
     */
    @JvmOverloads
    @JvmStatic
    fun setHubLightColorCommand(
        color: HubLedColor,
        startupCompletion: StartupCompletion = StartupCompletion.IMMEDIATE_NO_FEEDBACK
    ): ByteArray {
        return HubLedCommand.color(color, startupCompletion).byteValue
    }

    /**
     * Creates command to request the hub property
     * @param property HubProperty - Property to request
     * @return ByteArray LWP Command
     */
    @JvmStatic
    fun getHubPropertyCommand(property: HubProperty): ByteArray {
        return HubPropertyCommand.getValue(property).byteValue
    }

    /**
     * Creates command to set power to given port
     * @param port Port - Port to set power
     * @param value Byte - Power value
     * @param startupCompletion StartupCompletion - Startup completion type for command. Default Immediate no feedback
     * @return ByteArray LWP Command
     */
    @JvmOverloads
    @JvmStatic
    fun setPortPower(
        port: Port,
        value: Byte,
        startupCompletion: StartupCompletion = StartupCompletion.IMMEDIATE_NO_FEEDBACK
    ): ByteArray {
        return WriteDirectModeCommand.setValue(port, byteArrayOf(value), PortMode.MODE_0, startupCompletion).byteValue
    }

    /**
     * Creates the Play VM command to initialize Play VM
     * @return ByteArray LWP Command
     */
    @JvmStatic
    fun playVmInit(): ByteArray {
        return PlayVMCommand.initialize().byteValue
    }

    /**
     * Creates the Play VM command to auto calibrate steering servo
     * Needs to be sent after the INIT command
     * @return ByteArray LWP Command
     */
    @JvmStatic
    fun playVmAutoCalibrate(): ByteArray {
        return PlayVMCommand.calibrate().byteValue
    }

    /**
     * Creates the Play VM command
     * @param speedRaw Byte - Speed value
     * @param steer Byte - Steer value
     * @param vmCommand LWPMask<PlayVmOperation> - VM Command mask
     * @param startupCompletion StartupCompletion - Startup completion type for command. Default Immediate no feedback
     * @return ByteArray LWP Command
     */
    @JvmOverloads
    @JvmStatic
    fun setPlayVM(
        speedRaw: Byte,
        steer: Byte,
        vmCommand: LWPMask<PlayVmOperation>,
        startupCompletion: StartupCompletion = StartupCompletion.IMMEDIATE_NO_FEEDBACK
    ): ByteArray {
        return PlayVMCommand.build(speedRaw.toInt(), steer.toInt(), vmCommand, startupCompletion).byteValue
    }

    /**
     * Process the message and return the Version number
     * Used for FW and HW version
     * @param message ByteArray - Message to process
     * @return String - Version number
     */
    @JvmStatic
    fun processVersionNumber(message: ByteArray): String {
        if (message.size < HubProperty.MSG_WO_DATA_LENGTH + 4) {
            return ""
        }

        val versionByte0 = message[HubProperty.DATA_START_INDEX]
        val versionByte1 = message[HubProperty.DATA_START_INDEX + 1]
        val versionByte2 = message[HubProperty.DATA_START_INDEX + 2]
        val versionByte3 = message[HubProperty.DATA_START_INDEX + 3]

        val major = versionByte3.toInt() shr 4
        val minor = versionByte3.toInt() and 0xf
        val bugfix = ((versionByte2.toInt() shr 4) * 10) + (versionByte2.toInt() and 0xf)
        val build =
            ((versionByte1.toInt() shr 4) * 1000) + ((versionByte1.toInt() and 0xf) * 100) + ((versionByte0.toInt() shr 4) * 10) + (versionByte0.toInt() and 0xf)

        return "$major.$minor.$bugfix.$build"
    }

    /**
     * Convert HubPropertyCommand payload to version number string
     * Used for FW and HW version
     * @param payload ByteArray - payload 4 byte
     * @return String - Version number
     */
    @JvmStatic
    fun convertToVersionNumber(payload: ByteArray): String {
        if (payload.size != 4) {
            return ""
        }

        val major = payload[3].toInt() shr 4
        val minor = payload[3].toInt() and 0xf
        val bugfix = ((payload[2].toInt() shr 4) * 10) + (payload[2].toInt() and 0xf)
        val build =
            ((payload[1].toInt() shr 4) * 1000) + ((payload[1].toInt() and 0xf) * 100) + ((payload[0].toInt() shr 4) * 10) + (payload[0].toInt() and 0xf)

        return "$major.$minor.$bugfix.$build"
    }

    /**
     * Returns LWP command to request the port value in given mode
     * @param port Port - Port for which the value is requested
     * @param mode Mode - Mode for which the value is requested, default Mode 0
     * @return ByteArray LWP Command
     */
    @JvmOverloads
    @JvmStatic
    fun portValueInformationCommand(port: Port, mode: PortInformationType = PortInformationType.VALUE): ByteArray {
        return PortInformationRequestCommand.build(port, mode).byteValue
    }

    /**
     * Returns LWP command to perform [Alert][Command.ALERT] Operation
     * @param alertType AlertType - Alert type for which operation should be performed
     * @param operation AlertOperation - Alert Operation used in command, default [Request Update][AlertOperation.REQUEST_UPDATE]
     * @return ByteArray LWP Command
     */
    @JvmOverloads
    @JvmStatic
    fun alertCommand(alertType: AlertType, operation: AlertOperation = AlertOperation.REQUEST_UPDATE): ByteArray {
        return AlertCommand.build(alertType, operation).byteValue
    }

    /**
     * Returns LWP command to set the port value in given mode
     * @param port Port - Port for which the value is set
     * @param mode Mode - Mode in which the value is set
     * @param value Byte - Value to set
     * @param startupCompletion StartupCompletion - Startup completion type for command. Default Immediate no feedback
     * @return ByteArray LWP Command
     */
    @JvmOverloads
    @JvmStatic
    fun setPortPowerMode(
        port: Port,
        mode: PortMode,
        value: Byte,
        startupCompletion: StartupCompletion = StartupCompletion.IMMEDIATE_NO_FEEDBACK
    ): ByteArray {
        return WriteDirectModeCommand.build(port, startupCompletion, mode, byteArrayOf(value)).byteValue
    }

    /**
     * Returns Set Output Command with given subCommandType for given port
     * @param port Port
     * @param subCommand PortOutputSubCommand
     * @param args List<Byte>
     * @param startupCompletion StartupCompletion
     * @return ByteArray LWP Command
     */
    @JvmOverloads
    @JvmStatic
    fun setOutputSubcommand(
        port: Port,
        subCommand: PortOutputSubCommand,
        args: List<Byte>,
        startupCompletion: StartupCompletion = StartupCompletion.IMMEDIATE_NO_FEEDBACK
    ): ByteArray {
        return GenericPortOutputCommand.build(port, startupCompletion, subCommand, args.toByteArray()).byteValue
    }

    /**
     * Returns LWP version decoded from message
     * @param message ByteArray - Message to process
     * @return String - LWP Version
     */
    @JvmStatic
    fun processLWPVersion(message: ByteArray): String {
        if (message.size < HubProperty.MSG_WO_DATA_LENGTH + 2) {
            return ""
        }
        return String.format(Locale.getDefault(), "%d.%d", message[HubProperty.DATA_START_INDEX + 1], message[HubProperty.DATA_START_INDEX])
    }

    /**
     * Returns LWP version decoded from byteArray
     * @param message ByteArray - Bytes to convert, should be at least 2 bytes
     * @return String - LWP Version
     */
    @JvmStatic
    fun convertToLWPVersion(message: ByteArray): String {
        if (message.size < 2) {
            return ""
        }
        return String.format(Locale.getDefault(), "%d.%d", message[1], message[0])
    }

    /**
     * Returns battery voltage decoded from message
     * @param message ByteArray - Message to process
     * @return Float - Battery voltage in Volts
     */
    @JvmStatic
    fun processBatterVoltage(message: ByteArray): Float? {
        if (message.size < 6) {
            return null
        }
        val versionBytes = Arrays.copyOfRange(message, 4, 6)
        return convertToInt16(versionBytes) / 1000f
    }

    /**
     * Returns temperature decoded from message
     * @param message ByteArray - Message to process
     * @return Float - Temperature in Celsius
     */
    @JvmStatic
    fun processTemperature(message: ByteArray): Float? {
        if (message.size < 6) {
            return null
        }
        val versionBytes = Arrays.copyOfRange(message, 4, 6)
        return convertToInt16(versionBytes) / 10f
    }

    /**
     * Returns battery percentage decoded from message
     * @param message ByteArray - Message to process
     * @return Int - Battery percentage
     */
    @JvmStatic
    fun processBatterPercentage(message: ByteArray): Int? {
        if (message.size < HubProperty.MSG_WO_DATA_LENGTH + 1) {
            return null
        }
        return message[HubProperty.DATA_START_INDEX].toInt()
    }

    /**
     * Returns name of the hub decoded from message
     * @param message ByteArray - Message to process
     * @return String - Hub name
     */
    @JvmStatic
    fun processHubName(message: ByteArray): String {
        if (message.size < HubProperty.MSG_WO_DATA_LENGTH + 1) {
            return ""
        }
        val nameBytes = Arrays.copyOfRange(message, HubProperty.DATA_START_INDEX, message.size)
        return convertToString(nameBytes)
    }

    /**
     * Returns command build from command type and values with proper header
     * @param command Command - Command type
     * @param message ByteArray - Command value
     * @return ByteArray - Command
     */
    @JvmStatic
    fun createCommand(command: Command, message: ByteArray): ByteArray {
        return GenericCommand(command.wrap(), message).byteValue
    }

    /**
     * Convert byteArray to String
     * @param byteArray ByteArray - Bytes to convert
     * @return String - Bytes converted String
     */
    @JvmStatic
    fun convertToString(byteArray: ByteArray): String {
        return String(byteArray, StandardCharsets.UTF_8)
    }

    /**
     * Convert bytes to float, little endian
     * @param bytes ByteArray - Bytes to convert, should be at least 4 bytes
     * @return Int - Bytes converted float
     */
    @JvmStatic
    fun convertToFloat(bytes: ByteArray): Float {
        return ByteBuffer.wrap(bytes.copyOf(4)).order(ByteOrder.LITTLE_ENDIAN).getFloat()
    }

    /**
     * Convert 4 bytes to int32, little endian
     * @param bytes ByteArray - Bytes to convert, should be at least 4 bytes
     * @return Int - Bytes converted int32
     */
    @JvmStatic
    fun convertToInt32(bytes: ByteArray): Int {
        return ByteBuffer.wrap(bytes.copyOf(4)).order(ByteOrder.LITTLE_ENDIAN).getInt()
    }

    /**
     * Convert 2 bytes to int16, little endian
     * @param bytes ByteArray - Bytes to convert, should be at least 2 bytes
     * @return Int - Bytes converted int16
     */
    @JvmStatic
    fun convertToInt16(bytes: ByteArray): Int {
        val paddedBytes = bytes.copyOf(2)
        return (paddedBytes[1].toInt() and 0xff shl 8) or (paddedBytes[0].toInt() and 0xff)
    }

    /**
     * Converts byteArray to Hexadecimal string representation
     * @param bytes ByteArray - Bytes to convert
     * @return String - Hexadecimal representation of byteArray
     */
    @JvmStatic
    fun convertToHexString(bytes: ByteArray): String {
        return hexFormatter.formatHex(bytes)
    }

    /**
     * Converts byte to Hexadecimal string representation
     * @param byte ByteArray - Byte to convert
     * @return String - Hexadecimal representation of byte
     */
    @JvmStatic
    fun convertToHexString(byte: Byte): String {
        return hexFormatter.toHexDigits(byte)
    }

    /**
     * Converts byteArray to Binary string representation
     * @param bytes ByteArray - Bytes to convert
     * @return String - Binary representation of byteArray
     */
    @JvmStatic
    fun convertToBinaryString(bytes: ByteArray): String {
        return bytes.joinToString(separator = " ") { it.toString(2).padStart(8, '0') }
    }

    /**
     * Converts byte to Binary string representation
     * @param byte ByteArray - Byte to convert
     * @return String - Binary representation of byte
     */
    @JvmStatic
    fun convertToBinaryString(byte: Byte): String {
        return byte.toString(2).padStart(8, '0')
    }

    /**
     * Returns command build from args with proper header
     * @param args ByteArray - Command value
     * @return ByteArray - Command
     */
    @JvmStatic
    fun createCommand(args: ByteArray): ByteArray {
        return  args.createCommand()
    }
}
