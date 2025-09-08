package baniakjr.lwp.model.command.output

import baniakjr.lwp.*
import baniakjr.lwp.LWPByteValue.Companion.wrap
import baniakjr.lwp.model.LWPCommand
import baniakjr.lwp.model.LWPCommand.Companion.isSpecificCommand
import baniakjr.lwp.model.Wrapper
import baniakjr.lwp.model.command.MalformedCommand
import baniakjr.lwp.model.command.PortOutputCommand

class StartSpeedForTimeDualCommand internal constructor(
    port: Wrapper<Port>,
    action: Wrapper<StartupCompletion>,
    val time: Int,
    val speedA: Byte,
    val speedB: Byte,
    val maxPower: Byte,
    val endState: Byte,
    val profile: Byte
) : PortOutputCommand(port, action, PortOutputSubCommand.START_SPEED_FOR_TIME_DUAL.wrap()) {

    override val payload: ByteArray = byteArrayOf(
        (time and 0xFF).toByte(),
        ((time shr 8) and 0xFF).toByte(),
        speedA,
        speedB,
        maxPower,
        endState,
        profile
    )

    companion object {
        internal fun fromByteArray(byteArray: ByteArray): LWPCommand {
            if (!byteArray.isSpecificCommand(Command.PORT_OUTPUT) || !byteArray.isSpecificSubCommand(PortOutputSubCommand.START_SPEED_FOR_TIME_DUAL)) {
                return MalformedCommand(byteArray)
            }
            val port = Wrapper.wrap(Port::class.java, byteArray[Port.IN_INFORMATION_MESSAGE_INDEX])
            val action = Wrapper.wrap(StartupCompletion::class.java, byteArray[StartupCompletion.IN_MESSAGE_INDEX])
            val time = (byteArray[7].toInt() shl 8) or (byteArray[6].toInt() and 0xFF)
            val speedA = byteArray[8]
            val speedB = byteArray[9]
            val maxPower = byteArray[10]
            val endState = byteArray[11]
            val profile = byteArray[12]
            return StartSpeedForTimeDualCommand(port, action, time, speedA, speedB, maxPower, endState, profile)
        }

        /**
         * Build a StartSpeedForTimeDualCommand.
         */
        @JvmStatic
        fun build(
            port: Port,
            action: StartupCompletion,
            time: Int,
            speedA: Byte,
            speedB: Byte,
            maxPower: Byte,
            endState: Byte,
            profile: Byte
        ): StartSpeedForTimeDualCommand {
            return StartSpeedForTimeDualCommand(port.wrap(), action.wrap(), time, speedA, speedB, maxPower, endState, profile)
        }

        /**
         * Build a StartSpeedForTimeDualCommand with speedA, speedB, maxPower, endState, and profile as Int.
         */
        @JvmStatic
        fun build(
            port: Port,
            action: StartupCompletion,
            time: Int,
            speedA: Int,
            speedB: Int,
            maxPower: Int,
            endState: Int,
            profile: Int
        ): StartSpeedForTimeDualCommand {
            return StartSpeedForTimeDualCommand(
                port.wrap(),
                action.wrap(),
                time,
                speedA.toByte(),
                speedB.toByte(),
                maxPower.toByte(),
                endState.toByte(),
                profile.toByte()
            )
        }
    }

    override fun toString(): String {
        return "StartSpeedForTimeDualCommand(port=$port, sc=$action, time=$time, speedA=$speedA H[${LWP.convertToHexString(speedA)}], speedB=$speedB H[${LWP.convertToHexString(speedB)}], maxPower=$maxPower H[${LWP.convertToHexString(maxPower)}], endState=$endState H[${LWP.convertToHexString(endState)}], profile=$profile H[${LWP.convertToHexString(profile)}])"
    }
}