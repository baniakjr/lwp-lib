package baniakjr.lwp.model.command.output

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

class GenericPortOutputCommand internal constructor(port: Wrapper<Port>,
                                                    action: Wrapper<StartupCompletion>,
                                                    subCommand: Wrapper<PortOutputSubCommand>,
                                                    payload: ByteArray
) : PortOutputCommand(port, action, subCommand) {

    override val payload: ByteArray = payload
        get() = field.clone()

    companion object {
        internal fun fromByteArray(byteArray: ByteArray): LWPCommand {
            if(!byteArray.isSpecificCommand(Command.PORT_OUTPUT)) {
                return MalformedCommand(byteArray)
            }
            val port = Wrapper.wrap(Port::class.java, byteArray[Port.IN_INFORMATION_MESSAGE_INDEX])
            val action = Wrapper.wrap(StartupCompletion::class.java, byteArray[StartupCompletion.IN_MESSAGE_INDEX])
            val subCommand = Wrapper.wrap(PortOutputSubCommand::class.java, byteArray[PortOutputSubCommand.IN_MESSAGE_INDEX])
            val payload = byteArray.copyOfRange(6, byteArray.size)
            return GenericPortOutputCommand(port, action, subCommand, payload)
        }

        @JvmStatic
        fun build(
            port: Port,
            action: StartupCompletion,
            subCommand: PortOutputSubCommand,
            payload: ByteArray
        ): GenericPortOutputCommand {
            return GenericPortOutputCommand(port.wrap(), action.wrap(), subCommand.wrap(), payload)
        }
    }

    override fun toString(): String {
        return "PortOutputCommand(port=$port, sc=$action, subcommand=$subCommand, payload=${payload.contentToString()})"
    }


}