package baniakjr.lwp.model.command

import baniakjr.lwp.*
import baniakjr.lwp.LWPByteValue.Companion.wrap
import baniakjr.lwp.model.LWPCommand
import baniakjr.lwp.model.LWPCommand.Companion.isSpecificCommand
import baniakjr.lwp.model.Wrapper

class PortModeInformationRequestCommand internal constructor(
    val port: Wrapper<Port>,
    val mode: Wrapper<PortMode>,
    val informationType: Wrapper<ModeInformationType>
) : LWPCommand {

    override val command: Wrapper<Command> = Command.PORT_MODE_INFORMATION_REQUEST.wrap()

    override val byteValue: ByteArray
        get() {
            return LWP.createCommand(byteArrayOf(command.value, port.value, mode.value, informationType.value))
        }

    companion object {
        internal fun fromByteArray(byteArray: ByteArray): LWPCommand {
            if(byteArray.size != 6 || !byteArray.isSpecificCommand(Command.PORT_MODE_INFORMATION_REQUEST)) {
                return MalformedCommand(byteArray)
            }
            return PortModeInformationRequestCommand(Wrapper.wrap(Port::class.java, byteArray[Port.IN_INFORMATION_MESSAGE_INDEX]),Wrapper.wrap(PortMode::class.java, byteArray[PortMode.IN_INFORMATION_MESSAGE_INDEX]),Wrapper.wrap(ModeInformationType::class.java, byteArray[ModeInformationType.IN_MESSAGE_INDEX]))
        }

        @JvmStatic
        fun build(port: Port, mode: PortMode, informationType: ModeInformationType): PortModeInformationRequestCommand {
            return PortModeInformationRequestCommand(port.wrap(), mode.wrap(), informationType.wrap())
        }
    }

    override fun toString(): String {
        return "PortModeInformationRequestCommand(port=$port, mode=$mode, informationType=$informationType)"
    }


}