package baniakjr.lwp.model.command

import baniakjr.lwp.Capabilities
import baniakjr.lwp.Command
import baniakjr.lwp.LWP
import baniakjr.lwp.LWPByteValue.Companion.wrap
import baniakjr.lwp.LWPMask
import baniakjr.lwp.Port
import baniakjr.lwp.PortInformationType
import baniakjr.lwp.model.LWPCommand
import baniakjr.lwp.model.LWPCommand.Companion.createCommand
import baniakjr.lwp.model.LWPCommand.Companion.isSpecificCommand
import baniakjr.lwp.model.Wrapper

class ModePortInformationCommand internal constructor(
    port: Wrapper<Port>,
    val capabilities: LWPMask<Capabilities>,
    val modeCount: Byte,
    inputModes: ByteArray,
    outputModes: ByteArray
) : PortInformationCommand(port) {

    val inputModes: ByteArray = inputModes
        get() = field.clone()

    val outputModes: ByteArray = outputModes
        get() = field.clone()

    override val command: Wrapper<Command> = Command.PORT_INFORMATION.wrap()

    override val byteValue: ByteArray
        get() = (byteArrayOf(command.value, port.value, PortInformationType.MODE.value, capabilities.value, modeCount) + inputModes + outputModes).createCommand()

    companion object {
        internal fun fromByteArray(byteArray: ByteArray): LWPCommand {
            if(byteArray.size != 11 || !byteArray.isSpecificCommand(Command.PORT_INFORMATION)) {
                return MalformedCommand(byteArray)
            }
            val mode = Wrapper.wrap(PortInformationType::class.java, byteArray[PortInformationType.IN_MESSAGE_INDEX])
            if(mode.enum != PortInformationType.MODE) {
                return MalformedCommand(byteArray)
            }
            val port = Wrapper.wrap(Port::class.java, byteArray[Port.IN_INFORMATION_MESSAGE_INDEX])
            val capabilities = byteArray[5]
            val modeCount = byteArray[6]
            val inputModes = byteArray.copyOfRange(7,9)
            val outputModes = byteArray.copyOfRange(9,11)
            return ModePortInformationCommand(port, LWPMask.fromByte(Capabilities::class.java, capabilities), modeCount, inputModes, outputModes)
        }
    }

    override fun toString(): String {
        return "PortInformationCommand(port=$port, type=${PortInformationType.MODE}, capabilities=$capabilities, modeCount=$modeCount, inputModes=${LWP.convertToBinaryString(inputModes)}, outputModes=${LWP.convertToBinaryString(outputModes)})"
    }


}