package baniakjr.lwp.model.command

import baniakjr.lwp.LWP
import baniakjr.lwp.definition.value.Command
import baniakjr.lwp.model.LWPCommand
import baniakjr.lwp.model.Wrapper

class NotCommand internal constructor(byteValue: ByteArray): LWPCommand {

    override val byteValue: ByteArray = byteValue
        get() = field.clone()

    override val command: Wrapper<Command>
        get() = Wrapper(null, null)

    override fun toString(): String {
        return "NotCommand(byteValue=${LWP.convertToHexString(byteValue)})"
    }


}