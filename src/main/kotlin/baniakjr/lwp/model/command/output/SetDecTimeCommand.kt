package baniakjr.lwp.model.command.output

import baniakjr.lwp.definition.LWPByteValue.Companion.wrap
import baniakjr.lwp.definition.value.Port
import baniakjr.lwp.definition.value.PortOutputSubCommand
import baniakjr.lwp.definition.value.StartupCompletion
import baniakjr.lwp.model.Wrapper

class SetDecTimeCommand internal constructor(
    port: Wrapper<Port>,
    action: Wrapper<StartupCompletion>,
    time: Int,
    profile: Byte
) : SetTimeCommandBase(port, action, time, profile, PortOutputSubCommand.SET_DEC_TIME.wrap()) {

    companion object {

        /**
         * Build a SetDecTimeCommand.
         */
        @JvmStatic
        fun build(
            port: Port,
            action: StartupCompletion,
            time: Int,
            profile: Byte
        ): SetDecTimeCommand {
            return SetDecTimeCommand(port.wrap(), action.wrap(), time, profile)
        }

        /**
         * Build a SetDecTimeCommand with time as Byte.
         */
        @JvmStatic
        fun build(
            port: Port,
            action: StartupCompletion,
            time: Byte,
            profile: Byte
        ): SetDecTimeCommand {
            return SetDecTimeCommand(port.wrap(), action.wrap(), time.toInt(), profile)
        }
    }
}