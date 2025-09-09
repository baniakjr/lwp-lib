package baniakjr.lwp.model.command.output

import baniakjr.lwp.LWP
import baniakjr.lwp.definition.LWPByteValue.Companion.wrap
import baniakjr.lwp.definition.value.Command
import baniakjr.lwp.definition.value.Port
import baniakjr.lwp.definition.value.PortOutputSubCommand
import baniakjr.lwp.definition.value.StartupCompletion
import baniakjr.lwp.model.LWPCommand
import baniakjr.lwp.model.LWPCommand.Companion.isSpecificCommand
import baniakjr.lwp.model.Wrapper
import baniakjr.lwp.model.command.MalformedCommand
import baniakjr.lwp.model.command.PortOutputCommand

class StartSpeedForDegSingleCommand internal constructor(
    port: Wrapper<Port>,
    action: Wrapper<StartupCompletion>,
    val degrees: Int,
    val speed: Byte,
    val maxPower: Byte,
    val endState: Byte,
    val profile: Byte
) : PortOutputCommand(port, action, PortOutputSubCommand.START_SPEED_FOR_DEG_SINGLE.wrap()) {

    override val payload: ByteArray = byteArrayOf(
        (degrees and 0xFF).toByte(),
        ((degrees shr 8) and 0xFF).toByte(),
        ((degrees shr 16) and 0xFF).toByte(),
        ((degrees shr 24) and 0xFF).toByte(),
        speed,
        maxPower,
        endState,
        profile
    )

    companion object {
        internal fun fromByteArray(byteArray: ByteArray): LWPCommand {
            if (!byteArray.isSpecificCommand(Command.PORT_OUTPUT) || !byteArray.isSpecificSubCommand(PortOutputSubCommand.START_SPEED_FOR_DEG_SINGLE)) {
                return MalformedCommand(byteArray)
            }
            val port = Wrapper.wrap(Port::class.java, byteArray[Port.IN_INFORMATION_MESSAGE_INDEX])
            val action = Wrapper.wrap(StartupCompletion::class.java, byteArray[StartupCompletion.IN_MESSAGE_INDEX])
            val degrees = (byteArray[9].toInt() shl 24) or
                          ((byteArray[8].toInt() and 0xFF) shl 16) or
                          ((byteArray[7].toInt() and 0xFF) shl 8) or
                          (byteArray[6].toInt() and 0xFF)
            val speed = byteArray[10]
            val maxPower = byteArray[11]
            val endState = byteArray[12]
            val profile = byteArray[13]
            return StartSpeedForDegSingleCommand(port, action, degrees, speed, maxPower, endState, profile)
        }

        /**
         * Build a StartSpeedForDegSingleCommand.
         */
        @JvmStatic
        fun build(
            port: Port,
            action: StartupCompletion,
            degrees: Int,
            speed: Byte,
            maxPower: Byte,
            endState: Byte,
            profile: Byte
        ): StartSpeedForDegSingleCommand {
            return StartSpeedForDegSingleCommand(port.wrap(), action.wrap(), degrees, speed, maxPower, endState, profile)
        }

        /**
         * Build a StartSpeedForDegSingleCommand with speed, maxPower, endState, and profile as Int.
         */
        @JvmStatic
        fun build(
            port: Port,
            action: StartupCompletion,
            degrees: Int,
            speed: Int,
            maxPower: Int,
            endState: Int,
            profile: Int
        ): StartSpeedForDegSingleCommand {
            return StartSpeedForDegSingleCommand(
                port.wrap(),
                action.wrap(),
                degrees,
                speed.toByte(),
                maxPower.toByte(),
                endState.toByte(),
                profile.toByte()
            )
        }
    }

    override fun toString(): String {
        return "StartSpeedForDegSingleCommand(port=$port, sc=$action, degrees=$degrees, speed=$speed H[${LWP.convertToHexString(speed)}], maxPower=$maxPower H[${LWP.convertToHexString(maxPower)}], endState=$endState H[${LWP.convertToHexString(endState)}], profile=$profile H[${LWP.convertToHexString(profile)}])"
    }
}