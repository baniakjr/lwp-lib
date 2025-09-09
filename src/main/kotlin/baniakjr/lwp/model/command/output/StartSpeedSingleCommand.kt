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

class StartSpeedSingleCommand internal constructor(
    port: Wrapper<Port>,
    action: Wrapper<StartupCompletion>,
    val speed: Byte,
    val maxPower: Byte,
    val profile: Byte
) : PortOutputCommand(port, action, PortOutputSubCommand.START_SPEED_SINGLE.wrap()) {

    override val payload: ByteArray = byteArrayOf(speed, maxPower, profile)

    companion object {
        internal fun fromByteArray(byteArray: ByteArray): LWPCommand {
            if (!byteArray.isSpecificCommand(Command.PORT_OUTPUT) || !byteArray.isSpecificSubCommand(PortOutputSubCommand.START_SPEED_SINGLE)) {
                return MalformedCommand(byteArray)
            }
            val port = Wrapper.wrap(Port::class.java, byteArray[Port.IN_INFORMATION_MESSAGE_INDEX])
            val action = Wrapper.wrap(StartupCompletion::class.java, byteArray[StartupCompletion.IN_MESSAGE_INDEX])
            val speed = byteArray[6]
            val maxPower = byteArray[7]
            val profile = byteArray[8]
            return StartSpeedSingleCommand(port, action, speed, maxPower, profile)
        }

        /**
         * Build a StartSpeedSingleCommand.
         */
        @JvmStatic
        fun build(
            port: Port,
            action: StartupCompletion,
            speed: Byte,
            maxPower: Byte,
            profile: Byte
        ): StartSpeedSingleCommand {
            return StartSpeedSingleCommand(port.wrap(), action.wrap(), speed, maxPower, profile)
        }

        /**
         * Build a StartSpeedSingleCommand with speed, maxPower, and profile as Int.
         */
        @JvmStatic
        fun build(
            port: Port,
            action: StartupCompletion,
            speed: Int,
            maxPower: Int,
            profile: Int
        ): StartSpeedSingleCommand {
            return StartSpeedSingleCommand(port.wrap(), action.wrap(), speed.toByte(), maxPower.toByte(), profile.toByte())
        }
    }

    override fun toString(): String {
        return "StartSpeedSingleCommand(port=$port, sc=$action, speed=$speed H[${LWP.convertToHexString(speed)}], maxPower=$maxPower H[${LWP.convertToHexString(maxPower)}], profile=$profile H[${LWP.convertToHexString(profile)}])"
    }
}
