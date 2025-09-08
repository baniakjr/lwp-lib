package baniakjr.lwp.model.command.output

import baniakjr.lwp.LWPByteValue.Companion.wrap
import baniakjr.lwp.Port
import baniakjr.lwp.PortOutputSubCommand
import baniakjr.lwp.StartupCompletion
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