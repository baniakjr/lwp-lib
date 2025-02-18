package baniakjr.lwp

import java.nio.charset.StandardCharsets
import java.util.*

/**
 * LWP constants and helper functions
 * @see [LWP](https://lego.github.io/lego-ble-wireless-protocol-docs/index.html)
 */
object LWP {


    const val CHARACTERISTIC_UUID: String = "00001624-1212-efde-1623-785feabcd123"

    const val SERVICE_UUID: String = "00001623-1212-efde-1623-785feabcd123"

    const val MESSAGE_HEADER: Byte = 0x00

    const val MINIMAL_MSG_LENGTH: Int = 4

    const val MAX_POWER: Int = 100
    const val MIN_POWER: Int = 0

    const val FLOAT: Byte = 0
    const val HOLD: Byte = 126
    const val BREAK: Byte = 127

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
        return portOutputWriteDirectCommand(
            Port.SIX_LED,
            startupCompletion,
            PortMode.MODE_0,
            listOf(mask.value, value)
        )
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
        return portOutputWriteDirectCommand(
            Port.HUB_LED,
            startupCompletion,
            HubLedMode.COLOR,
            listOf(color.value)
        )
    }

    /**
     * Creates command to request the hub property
     * @param property HubProperty - Property to request
     * @return ByteArray LWP Command
     */
    @JvmStatic
    fun getHubPropertyCommand(property: HubProperty): ByteArray {
        return createCommand(
            listOf(
                Command.HUB_PROPERTY.value,
                property.value,
                HubPropertyOperation.REQUEST_UPDATE.value
            )
        )
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
        return portOutputWriteDirectCommand(
            port,
            startupCompletion,
            PortMode.MODE_0,
            listOf(value)
        )
    }

    /**
     * Creates the Play VM command to initialize Play VM
     * @return ByteArray LWP Command
     */
    @JvmStatic
    fun playVmInit(): ByteArray {
        return setPlayVM(0.toByte(), 0.toByte(), LWPMask(PlayVmOperation.INIT))
    }

    /**
     * Creates the Play VM command to auto calibrate steering servo
     * Needs to be sent after the INIT command
     * @return ByteArray LWP Command
     */
    @JvmStatic
    fun playVmAutoCalibrate(): ByteArray {
        return setPlayVM(0.toByte(), 0.toByte(), LWPMask(PlayVmOperation.CALIBRATE))
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
        return portOutputWriteDirectCommand(
            Port.PLAYVM,
            startupCompletion,
            PortMode.MODE_0,
            listOf(0x03.toByte(), 0x00.toByte(), speedRaw, steer, vmCommand.value, 0x00.toByte())
        )
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

        return String.format(Locale.getDefault(), "%d.%d.%d.%d", major, minor, bugfix, build)
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
        return createCommand(
            listOf(
                Command.PORT_INFORMATION_REQUEST.value,
                port.value,
                mode.value
            )
        )
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
        return portOutputWriteDirectCommand(
            port,
            startupCompletion,
            mode,
            listOf(value)
        )
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
        return portOutputCommand(port, startupCompletion, subCommand, args)
    }

    /**
     * Returns LWP version decoded from message
     * @param message ByteArray - Message to process
     * @return String - LWP Version
     */
    @JvmStatic
    fun processLWPVersion(message: ByteArray): String? {
        if (message.size < HubProperty.MSG_WO_DATA_LENGTH + 2) {
            return null
        }
        return String.format(Locale.getDefault(), "%d.%d", message[HubProperty.DATA_START_INDEX], message[HubProperty.DATA_START_INDEX + 1])
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
        return int16From2ByteArray(versionBytes) / 1000f
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
        return int16From2ByteArray(versionBytes) / 10f
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
    fun processHubName(message: ByteArray): String? {
        if (message.size < HubProperty.MSG_WO_DATA_LENGTH + 1) {
            return null
        }
        val nameBytes = Arrays.copyOfRange(message, HubProperty.DATA_START_INDEX, message.size)
        return stringFromByteArray(nameBytes)
    }

    private fun stringFromByteArray(byteArray: ByteArray): String {
        return String(byteArray, StandardCharsets.UTF_8)
    }

    /**
     * Convert 4 bytes to int32
     * @param bytes ByteArray - Bytes to convert, should be at least 4 bytes
     * @return Int - Bytes converted int32
     */
    @JvmStatic
    fun int32From4ByteArray(bytes: ByteArray): Int {
        return (bytes[0].toInt() shl 24) or
                (bytes[1].toInt() shl 16) or
                (bytes[2].toInt() shl 8) or
                bytes[3].toInt()
    }

    /**
     * Convert 2 bytes to int16, little endian
     * @param bytes ByteArray - Bytes to convert, should be at least 2 bytes
     * @return Int - Bytes converted int16
     */
    @JvmStatic
    fun int16From2ByteArray(bytes: ByteArray): Int {
        return (bytes[1].toInt() and 0xff shl 8) or (bytes[0].toInt() and 0xff)
    }

    private fun createCommand(args: List<Byte>): ByteArray {
        val size = (args.size + 2.toByte()).toByte()
        val command = ByteArray(size.toInt())
        command[0] = size
        command[1] = MESSAGE_HEADER
        for (i in args.indices) {
            command[i + 2] = args[i]
        }
        return command
    }

    private fun portOutputCommand(
        port: Port,
        action: StartupCompletion,
        subCommand: PortOutputSubCommand,
        args: List<Byte>
    ): ByteArray {
        val commandArguments: MutableList<Byte> = ArrayList()
        commandArguments.add(Command.PORT_OUTPUT.value)
        commandArguments.add(port.value)
        commandArguments.add(action.value)
        commandArguments.add(subCommand.value)
        commandArguments.addAll(args)
        return createCommand(commandArguments)
    }

    private fun portOutputWriteDirectCommand(
        port: Port,
        action: StartupCompletion,
        portMode: Mode,
        args: List<Byte>
    ): ByteArray {
        val commandArguments: MutableList<Byte> = ArrayList()
        commandArguments.add(portMode.value)
        commandArguments.addAll(args)
        return portOutputCommand(port, action, PortOutputSubCommand.WRITE_DIRECT_MODE, commandArguments)
    }
}
