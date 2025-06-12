package baniakjr.lwp.model.command

import baniakjr.lwp.Command
import baniakjr.lwp.LWP
import baniakjr.lwp.model.LWPCommand
import baniakjr.lwp.model.Wrapper

class MalformedCommand internal constructor(byteValue: ByteArray): LWPCommand {

    override val byteValue: ByteArray = byteValue
        get() = field.clone()

    override val command: Wrapper<Command>
        get() = Wrapper.wrap(Command::class.java, byteValue[Command.IN_MESSAGE_INDEX])

    override fun toString(): String {
        return "MalformedCommand(command=$command, byteValue=${LWP.convertToHexString(byteValue)})"
    }


}