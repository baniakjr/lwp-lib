package baniakjr.lwp.model.command

import baniakjr.lwp.LWP
import baniakjr.lwp.definition.value.Command
import baniakjr.lwp.model.LWPCommand
import baniakjr.lwp.model.Wrapper

class UnknownCommand internal constructor(byteValue: ByteArray) : LWPCommand {

    override val byteValue: ByteArray = byteValue
        get() = field.clone()

    override val command: Wrapper<Command>
        get() = Wrapper.wrap(Command::class.java, byteValue[Command.IN_MESSAGE_INDEX])

    override fun toString(): String {
        return "UnknownCommand(command=$command byteValue=${LWP.convertToHexString(byteValue)})"
    }

}