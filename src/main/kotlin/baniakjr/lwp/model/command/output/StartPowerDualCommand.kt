package baniakjr.lwp.model.command.output

import baniakjr.lwp.*
import baniakjr.lwp.LWPByteValue.Companion.wrap
import baniakjr.lwp.model.LWPCommand
import baniakjr.lwp.model.LWPCommand.Companion.isSpecificCommand
import baniakjr.lwp.model.Wrapper
import baniakjr.lwp.model.command.MalformedCommand
import baniakjr.lwp.model.command.PortOutputCommand

class StartPowerDualCommand internal constructor(
    port: Wrapper<Port>,
    action: Wrapper<StartupCompletion>,
    val powerA: Byte,
    val powerB: Byte
) : PortOutputCommand(port, action, PortOutputSubCommand.START_POWER_DUAL.wrap()) {

    override val payload: ByteArray = byteArrayOf(powerA, powerB)

    companion object {
        internal fun fromByteArray(byteArray: ByteArray): LWPCommand {
            if (!byteArray.isSpecificCommand(Command.PORT_OUTPUT) || !byteArray.isSpecificSubCommand(PortOutputSubCommand.START_POWER_DUAL)) {
                return MalformedCommand(byteArray)
            }
            val port = Wrapper.wrap(Port::class.java, byteArray[Port.IN_INFORMATION_MESSAGE_INDEX])
            val action = Wrapper.wrap(StartupCompletion::class.java, byteArray[StartupCompletion.IN_MESSAGE_INDEX])
            val powerA = byteArray[6]
            val powerB = byteArray[7]
            return StartPowerDualCommand(port, action, powerA, powerB)
        }

        /**
         * Build a StartPowerDualCommand.
         */
        @JvmStatic
        fun build(
            port: Port,
            action: StartupCompletion,
            powerA: Byte,
            powerB: Byte
        ): StartPowerDualCommand {
            return StartPowerDualCommand(port.wrap(), action.wrap(), powerA, powerB)
        }

        /**
         * Build a StartPowerDualCommand with powerA and powerB as Int.
         */
        @JvmStatic
        fun build(
            port: Port,
            action: StartupCompletion,
            powerA: Int,
            powerB: Int
        ): StartPowerDualCommand {
            return StartPowerDualCommand(port.wrap(), action.wrap(), powerA.toByte(), powerB.toByte())
        }

        /**
         * Build a StartPowerDualCommand with HOLD value.
         */
        @JvmStatic
        fun buildHold(
            port: Port,
            action: StartupCompletion
        ): StartPowerDualCommand {
            return StartPowerDualCommand(port.wrap(), action.wrap(), LWP.HOLD, LWP.HOLD)
        }

        /**
         * Build a StartPowerDualCommand with BREAK value.
         */
        @JvmStatic
        fun buildBreak(
            port: Port,
            action: StartupCompletion
        ): StartPowerDualCommand {
            return StartPowerDualCommand(port.wrap(), action.wrap(), LWP.BREAK, LWP.BREAK)
        }
    }

    override fun toString(): String {
        return "StartPowerDualCommand(port=$port, sc=$action, powerA=$powerA H[${LWP.convertToHexString(byteArrayOf(powerA))}], powerB=$powerB H[${LWP.convertToHexString(byteArrayOf(powerB))}])"
    }
}