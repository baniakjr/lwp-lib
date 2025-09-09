package baniakjr.lwp.model.command.output

import baniakjr.lwp.Command
import baniakjr.lwp.LWP
import baniakjr.lwp.LWPByteValue.Companion.wrap
import baniakjr.lwp.Port
import baniakjr.lwp.PortOutputSubCommand
import baniakjr.lwp.StartupCompletion
import baniakjr.lwp.model.LWPCommand
import baniakjr.lwp.model.LWPCommand.Companion.isSpecificCommand
import baniakjr.lwp.model.Wrapper
import baniakjr.lwp.model.command.MalformedCommand
import baniakjr.lwp.model.command.PortOutputCommand

class StartPowerSingleCommand internal constructor(
    port: Wrapper<Port>,
    action: Wrapper<StartupCompletion>,
    val power: Byte
) : PortOutputCommand(port, action, PortOutputSubCommand.START_POWER_SINGLE.wrap()) {

    override val payload: ByteArray = byteArrayOf(power)

    companion object {
        internal fun fromByteArray(byteArray: ByteArray): LWPCommand {
            if (!byteArray.isSpecificCommand(Command.PORT_OUTPUT) || !byteArray.isSpecificSubCommand(PortOutputSubCommand.START_POWER_SINGLE)) {
                return MalformedCommand(byteArray)
            }
            val port = Wrapper.wrap(Port::class.java, byteArray[Port.IN_INFORMATION_MESSAGE_INDEX])
            val action = Wrapper.wrap(StartupCompletion::class.java, byteArray[StartupCompletion.IN_MESSAGE_INDEX])
            val power = byteArray[6]
            return StartPowerSingleCommand(port, action, power)
        }

        /**
         * Build a StartPowerSingleCommand.
         */
        @JvmStatic
        fun build(
            port: Port,
            action: StartupCompletion,
            power: Byte
        ): StartPowerSingleCommand {
            return StartPowerSingleCommand(port.wrap(), action.wrap(), power)
        }

        /**
         * Build a StartPowerSingleCommand with power as Int.
         */
        @JvmStatic
        fun setPower(
            port: Port,
            action: StartupCompletion,
            power: Int
        ): StartPowerSingleCommand {
            return StartPowerSingleCommand(port.wrap(), action.wrap(), power.toByte())
        }

        /**
         * Build a StartPowerSingleCommand with HOLD value.
         */
        @JvmStatic
        fun setHold(
            port: Port,
            action: StartupCompletion
        ): StartPowerSingleCommand {
            return StartPowerSingleCommand(port.wrap(), action.wrap(), LWP.HOLD)
        }

        /**
         * Build a StartPowerSingleCommand with BREAK value.
         */
        @JvmStatic
        fun setBreak(
            port: Port,
            action: StartupCompletion
        ): StartPowerSingleCommand {
            return StartPowerSingleCommand(port.wrap(), action.wrap(), LWP.BREAK)
        }
    }

    override fun toString(): String {
        return "StartPowerSingleCommand(port=$port, sc=$action, power=$power H[${LWP.convertToHexString(power)}])"
    }
}