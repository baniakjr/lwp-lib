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

class StartSpeedForDegDualCommand internal constructor(
    port: Wrapper<Port>,
    action: Wrapper<StartupCompletion>,
    val degrees: Int,
    val speedA: Byte,
    val speedB: Byte,
    val maxPower: Byte,
    val endState: Byte,
    val profile: Byte
) : PortOutputCommand(port, action, PortOutputSubCommand.START_SPEED_FOR_DEG_DUAL.wrap()) {

    override val payload: ByteArray = byteArrayOf(
        (degrees and 0xFF).toByte(),
        ((degrees shr 8) and 0xFF).toByte(),
        ((degrees shr 16) and 0xFF).toByte(),
        ((degrees shr 24) and 0xFF).toByte(),
        speedA,
        speedB,
        maxPower,
        endState,
        profile
    )

    companion object {
        internal fun fromByteArray(byteArray: ByteArray): LWPCommand {
            if (!byteArray.isSpecificCommand(Command.PORT_OUTPUT) || !byteArray.isSpecificSubCommand(PortOutputSubCommand.START_SPEED_FOR_DEG_DUAL)) {
                return MalformedCommand(byteArray)
            }
            val port = Wrapper.wrap(Port::class.java, byteArray[Port.IN_INFORMATION_MESSAGE_INDEX])
            val action = Wrapper.wrap(StartupCompletion::class.java, byteArray[StartupCompletion.IN_MESSAGE_INDEX])
            val degrees = (byteArray[9].toInt() shl 24) or
                          ((byteArray[8].toInt() and 0xFF) shl 16) or
                          ((byteArray[7].toInt() and 0xFF) shl 8) or
                          (byteArray[6].toInt() and 0xFF)
            val speedA = byteArray[10]
            val speedB = byteArray[11]
            val maxPower = byteArray[12]
            val endState = byteArray[13]
            val profile = byteArray[14]
            return StartSpeedForDegDualCommand(port, action, degrees, speedA, speedB, maxPower, endState, profile)
        }

        /**
         * Build a StartSpeedForDegDualCommand.
         */
        @JvmStatic
        fun build(
            port: Port,
            action: StartupCompletion,
            degrees: Int,
            speedA: Byte,
            speedB: Byte,
            maxPower: Byte,
            endState: Byte,
            profile: Byte
        ): StartSpeedForDegDualCommand {
            return StartSpeedForDegDualCommand(port.wrap(), action.wrap(), degrees, speedA, speedB, maxPower, endState, profile)
        }

        /**
         * Build a StartSpeedForDegDualCommand with speedA, speedB, maxPower, endState, and profile as Int.
         */
        @JvmStatic
        fun build(
            port: Port,
            action: StartupCompletion,
            degrees: Int,
            speedA: Int,
            speedB: Int,
            maxPower: Int,
            endState: Int,
            profile: Int
        ): StartSpeedForDegDualCommand {
            return StartSpeedForDegDualCommand(
                port.wrap(),
                action.wrap(),
                degrees,
                speedA.toByte(),
                speedB.toByte(),
                maxPower.toByte(),
                endState.toByte(),
                profile.toByte()
            )
        }
    }

    override fun toString(): String {
        return "StartSpeedForDegDualCommand(port=$port, sc=$action, degrees=$degrees, speedA=$speedA H[${LWP.convertToHexString(speedA)}], speedB=$speedB H[${LWP.convertToHexString(speedB)}], maxPower=$maxPower H[${LWP.convertToHexString(maxPower)}], endState=$endState H[${LWP.convertToHexString(endState)}], profile=$profile H[${LWP.convertToHexString(profile)}])"
    }
}