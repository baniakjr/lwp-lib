package baniakjr.lwp.model.command

import baniakjr.lwp.definition.LWPByteValue.Companion.wrap
import baniakjr.lwp.definition.value.Command
import baniakjr.lwp.definition.value.Port
import baniakjr.lwp.definition.value.PortInformationType
import baniakjr.lwp.model.LWPCommand
import baniakjr.lwp.model.LWPCommand.Companion.createCommand
import baniakjr.lwp.model.LWPCommand.Companion.isSpecificCommand
import baniakjr.lwp.model.Wrapper

class PortInformationRequestCommand internal constructor(
    val port: Wrapper<Port>,
    val informationType: Wrapper<PortInformationType>
) : LWPCommand {

    override val command: Wrapper<Command> = Command.PORT_INFORMATION_REQUEST.wrap()

    override val byteValue: ByteArray
        get() {
            return byteArrayOf(command.value, port.value, informationType.value).createCommand()
        }

    companion object {
        internal fun fromByteArray(byteArray: ByteArray): LWPCommand {
            if(byteArray.size != 5 || !byteArray.isSpecificCommand(Command.PORT_INFORMATION_REQUEST)) {
                return MalformedCommand(byteArray)
            }
            return PortInformationRequestCommand(Wrapper.wrap(Port::class.java, byteArray[Port.IN_INFORMATION_MESSAGE_INDEX]),Wrapper.wrap(PortInformationType::class.java, byteArray[PortInformationType.IN_MESSAGE_INDEX]))
        }

        @JvmStatic
        fun build(port: Port, informationType: PortInformationType): PortInformationRequestCommand {
            return PortInformationRequestCommand(port.wrap(), informationType.wrap())
        }

        @JvmStatic
        fun getValue(port: Port): PortInformationRequestCommand {
            return build(port, PortInformationType.VALUE)
        }
    }

    override fun toString(): String {
        return "PortInformationRequestCommand(port=$port, informationType=$informationType)"
    }


}