package baniakjr.lwp.model.command

import baniakjr.lwp.LWP
import baniakjr.lwp.definition.LWPByteValue.Companion.wrap
import baniakjr.lwp.definition.value.Command
import baniakjr.lwp.model.LWPCommand
import baniakjr.lwp.model.LWPCommand.Companion.createCommand
import baniakjr.lwp.model.LWPCommand.Companion.isSpecificCommand
import baniakjr.lwp.model.Wrapper

class HubActionCommand internal constructor(val actionType: Byte) : LWPCommand {

    override val command: Wrapper<Command> = Command.HUB_ACTION.wrap()

    override val byteValue: ByteArray
        get() {
            return byteArrayOf(command.value, actionType).createCommand()
        }

    companion object {
        internal fun fromByteArray(byteArray: ByteArray): LWPCommand {
            if(byteArray.size != 4 || !byteArray.isSpecificCommand(Command.HUB_ACTION)) {
                return MalformedCommand(byteArray)
            }
            return HubActionCommand(byteArray[3])
        }

        @JvmStatic
        fun build(actionType: Byte): HubActionCommand {
            return HubActionCommand(actionType)
        }
    }

    override fun toString(): String {
        return "HubActionCommand(actionType=${LWP.convertToHexString(actionType)})"
    }

}