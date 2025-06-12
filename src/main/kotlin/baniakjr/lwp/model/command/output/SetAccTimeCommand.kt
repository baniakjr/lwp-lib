package baniakjr.lwp.model.command.output

import baniakjr.lwp.LWPByteValue.Companion.wrap
import baniakjr.lwp.Port
import baniakjr.lwp.PortOutputSubCommand
import baniakjr.lwp.StartupCompletion
import baniakjr.lwp.model.Wrapper

class SetAccTimeCommand internal constructor(
    port: Wrapper<Port>,
    action: Wrapper<StartupCompletion>,
    time: Int,
    profile: Byte
) : SetTimeCommandBase(port, action, time, profile, PortOutputSubCommand.SET_ACC_TIME.wrap()) {

    companion object {

        /**
         * Build a SetAccTimeCommand.
         */
        @JvmStatic
        fun build(
            port: Port,
            action: StartupCompletion,
            time: Int,
            profile: Byte
        ): SetAccTimeCommand {
            return SetAccTimeCommand(port.wrap(), action.wrap(), time, profile)
        }

        /**
         * Build a SetAccTimeCommand with time as Byte.
         */
        @JvmStatic
        fun build(
            port: Port,
            action: StartupCompletion,
            time: Byte,
            profile: Byte
        ): SetAccTimeCommand {
            return SetAccTimeCommand(port.wrap(), action.wrap(), time.toInt(), profile)
        }
    }
}