package baniakjr.lwp.model.command.output

import baniakjr.lwp.*
import baniakjr.lwp.LWPByteValue.Companion.wrap
import baniakjr.lwp.model.LWPCommand
import baniakjr.lwp.model.LWPCommand.Companion.isSpecificCommand
import baniakjr.lwp.model.Wrapper
import baniakjr.lwp.model.command.MalformedCommand
import baniakjr.lwp.model.command.PortOutputCommand

class StartSpeedDualCommand internal constructor(
    port: Wrapper<Port>,
    action: Wrapper<StartupCompletion>,
    val speedA: Byte,
    val speedB: Byte,
    val maxPower: Byte,
    val profile: Byte
) : PortOutputCommand(port, action, PortOutputSubCommand.START_SPEED_DUAL.wrap()) {

    override val payload: ByteArray = byteArrayOf(speedA, speedB, maxPower, profile)

    companion object {
        internal fun fromByteArray(byteArray: ByteArray): LWPCommand {
            if (!byteArray.isSpecificCommand(Command.PORT_OUTPUT) || !byteArray.isSpecificSubCommand(PortOutputSubCommand.START_SPEED_DUAL)) {
                return MalformedCommand(byteArray)
            }
            val port = Wrapper.wrap(Port::class.java, byteArray[Port.IN_INFORMATION_MESSAGE_INDEX])
            val action = Wrapper.wrap(StartupCompletion::class.java, byteArray[StartupCompletion.IN_MESSAGE_INDEX])
            val speedA = byteArray[6]
            val speedB = byteArray[7]
            val maxPower = byteArray[8]
            val profile = byteArray[9]
            return StartSpeedDualCommand(port, action, speedA, speedB, maxPower, profile)
        }

        /**
         * Build a StartSpeedDualCommand.
         */
        @JvmStatic
        fun build(
            port: Port,
            action: StartupCompletion,
            speedA: Byte,
            speedB: Byte,
            maxPower: Byte,
            profile: Byte
        ): StartSpeedDualCommand {
            return StartSpeedDualCommand(port.wrap(), action.wrap(), speedA, speedB, maxPower, profile)
        }

        /**
         * Build a StartSpeedDualCommand with speedA, speedB, maxPower, and profile as Int.
         */
        @JvmStatic
        fun build(
            port: Port,
            action: StartupCompletion,
            speedA: Int,
            speedB: Int,
            maxPower: Int,
            profile: Int
        ): StartSpeedDualCommand {
            return StartSpeedDualCommand(
                port.wrap(),
                action.wrap(),
                speedA.toByte(),
                speedB.toByte(),
                maxPower.toByte(),
                profile.toByte()
            )
        }
    }

    override fun toString(): String {
        return "StartSpeedDualCommand(port=$port, sc=$action, speedA=$speedA H[${LWP.convertToHexString(speedA)}], speedB=$speedB H[${LWP.convertToHexString(speedB)}], maxPower=$maxPower H[${LWP.convertToHexString(maxPower)}], profile=$profile H[${LWP.convertToHexString(profile)}])"
    }
}