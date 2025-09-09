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

class StartSpeedForTimeSingleCommand internal constructor(
    port: Wrapper<Port>,
    action: Wrapper<StartupCompletion>,
    val time: Int,
    val speed: Byte,
    val maxPower: Byte,
    val endState: Byte,
    val profile: Byte
) : PortOutputCommand(port, action, PortOutputSubCommand.START_SPEED_FOR_TIME_SINGLE.wrap()) {

    override val payload: ByteArray = byteArrayOf(
        (time and 0xFF).toByte(),
        ((time shr 8) and 0xFF).toByte(),
        speed,
        maxPower,
        endState,
        profile
    )

    companion object {
        internal fun fromByteArray(byteArray: ByteArray): LWPCommand {
            if (!byteArray.isSpecificCommand(Command.PORT_OUTPUT) || !byteArray.isSpecificSubCommand(PortOutputSubCommand.START_SPEED_FOR_TIME_SINGLE)) {
                return MalformedCommand(byteArray)
            }
            val port = Wrapper.wrap(Port::class.java, byteArray[Port.IN_INFORMATION_MESSAGE_INDEX])
            val action = Wrapper.wrap(StartupCompletion::class.java, byteArray[StartupCompletion.IN_MESSAGE_INDEX])
            val time = (byteArray[7].toInt() shl 8) or (byteArray[6].toInt() and 0xFF)
            val speed = byteArray[8]
            val maxPower = byteArray[9]
            val endState = byteArray[10]
            val profile = byteArray[11]
            return StartSpeedForTimeSingleCommand(port, action, time, speed, maxPower, endState, profile)
        }

        /**
         * Build a StartSpeedForTimeSingleCommand.
         */
        @JvmStatic
        fun build(
            port: Port,
            action: StartupCompletion,
            time: Int,
            speed: Byte,
            maxPower: Byte,
            endState: Byte,
            profile: Byte
        ): StartSpeedForTimeSingleCommand {
            return StartSpeedForTimeSingleCommand(port.wrap(), action.wrap(), time, speed, maxPower, endState, profile)
        }

        /**
         * Build a StartSpeedForTimeSingleCommand with speed, maxPower, endState, and profile as Int.
         */
        @JvmStatic
        fun build(
            port: Port,
            action: StartupCompletion,
            time: Int,
            speed: Int,
            maxPower: Int,
            endState: Int,
            profile: Int
        ): StartSpeedForTimeSingleCommand {
            return StartSpeedForTimeSingleCommand(
                port.wrap(),
                action.wrap(),
                time,
                speed.toByte(),
                maxPower.toByte(),
                endState.toByte(),
                profile.toByte()
            )
        }
    }

    override fun toString(): String {
        return "StartSpeedForTimeSingleCommand(port=$port, sc=$action, time=$time, speed=$speed H[${LWP.convertToHexString(speed)}], maxPower=$maxPower H[${LWP.convertToHexString(maxPower)}], endState=$endState H[${LWP.convertToHexString(endState)}], profile=$profile H[${LWP.convertToHexString(profile)}])"
    }
}