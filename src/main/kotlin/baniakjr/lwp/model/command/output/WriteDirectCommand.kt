package baniakjr.lwp.model.command.output

import baniakjr.lwp.LWP
import baniakjr.lwp.definition.LWPByteValue.Companion.wrap
import baniakjr.lwp.definition.value.Command
import baniakjr.lwp.definition.value.Port
import baniakjr.lwp.definition.value.PortOutputSubCommand
import baniakjr.lwp.definition.value.StartupCompletion
import baniakjr.lwp.model.LWPCommand
import baniakjr.lwp.model.LWPCommand.Companion.isSpecificCommand
import baniakjr.lwp.model.Wrapper
import baniakjr.lwp.model.command.MalformedCommand
import baniakjr.lwp.model.command.PortOutputCommand

class WriteDirectCommand internal constructor(
    port: Wrapper<Port>,
    action: Wrapper<StartupCompletion>,
    payload: ByteArray
) : PortOutputCommand(port, action, PortOutputSubCommand.WRITE_DIRECT.wrap()) {

    override val payload: ByteArray = payload.clone()

    companion object {
        internal fun fromByteArray(byteArray: ByteArray): LWPCommand {
            if (!byteArray.isSpecificCommand(Command.PORT_OUTPUT) ||
                !byteArray.isSpecificSubCommand(PortOutputSubCommand.WRITE_DIRECT)) {
                return MalformedCommand(byteArray)
            }
            val port = Wrapper.wrap(Port::class.java, byteArray[Port.IN_INFORMATION_MESSAGE_INDEX])
            val action = Wrapper.wrap(StartupCompletion::class.java, byteArray[StartupCompletion.IN_MESSAGE_INDEX])
            val payload = byteArray.copyOfRange(6, byteArray.size)
            return WriteDirectCommand(port, action, payload)
        }

        @JvmStatic
        fun build(
            port: Port,
            action: StartupCompletion,
            payload: ByteArray
        ): WriteDirectCommand {
            return WriteDirectCommand(port.wrap(), action.wrap(), payload)
        }
    }

    override fun toString(): String {
        return "WriteDirectCommand(port=$port, sc=$action, payload=${payload.contentToString()} H[${LWP.convertToHexString(payload)}])"
    }

}