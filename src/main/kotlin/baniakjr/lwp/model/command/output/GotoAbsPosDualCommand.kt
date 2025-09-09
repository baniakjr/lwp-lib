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

class GotoAbsPosDualCommand internal constructor(
    port: Wrapper<Port>,
    action: Wrapper<StartupCompletion>,
    val absPosA: Int,
    val absPosB: Int,
    val speedA: Byte,
    val speedB: Byte,
    val maxPower: Byte,
    val endState: Byte,
    val profile: Byte
) : PortOutputCommand(port, action, PortOutputSubCommand.GOTO_ABS_POS_DUAL.wrap()) {

    override val payload: ByteArray = byteArrayOf(
        (absPosA and 0xFF).toByte(),
        ((absPosA shr 8) and 0xFF).toByte(),
        ((absPosA shr 16) and 0xFF).toByte(),
        ((absPosA shr 24) and 0xFF).toByte(),
        (absPosB and 0xFF).toByte(),
        ((absPosB shr 8) and 0xFF).toByte(),
        ((absPosB shr 16) and 0xFF).toByte(),
        ((absPosB shr 24) and 0xFF).toByte(),
        speedA,
        speedB,
        maxPower,
        endState,
        profile
    )

    companion object {
        internal fun fromByteArray(byteArray: ByteArray): LWPCommand {
            if (!byteArray.isSpecificCommand(Command.PORT_OUTPUT) || !byteArray.isSpecificSubCommand(PortOutputSubCommand.GOTO_ABS_POS_DUAL)) {
                return MalformedCommand(byteArray)
            }
            val port = Wrapper.wrap(Port::class.java, byteArray[Port.IN_INFORMATION_MESSAGE_INDEX])
            val action = Wrapper.wrap(StartupCompletion::class.java, byteArray[StartupCompletion.IN_MESSAGE_INDEX])
            val absPosA = (byteArray[9].toInt() shl 24) or
                          ((byteArray[8].toInt() and 0xFF) shl 16) or
                          ((byteArray[7].toInt() and 0xFF) shl 8) or
                          (byteArray[6].toInt() and 0xFF)
            val absPosB = (byteArray[13].toInt() shl 24) or
                          ((byteArray[12].toInt() and 0xFF) shl 16) or
                          ((byteArray[11].toInt() and 0xFF) shl 8) or
                          (byteArray[10].toInt() and 0xFF)
            val speedA = byteArray[14]
            val speedB = byteArray[15]
            val maxPower = byteArray[16]
            val endState = byteArray[17]
            val profile = byteArray[18]
            return GotoAbsPosDualCommand(port, action, absPosA, absPosB, speedA, speedB, maxPower, endState, profile)
        }

        /**
         * Build a GotoAbsPosDualCommand.
         */
        @JvmStatic
        fun build(
            port: Port,
            action: StartupCompletion,
            absPosA: Int,
            absPosB: Int,
            speedA: Byte,
            speedB: Byte,
            maxPower: Byte,
            endState: Byte,
            profile: Byte
        ): GotoAbsPosDualCommand {
            return GotoAbsPosDualCommand(port.wrap(), action.wrap(), absPosA, absPosB, speedA, speedB, maxPower, endState, profile)
        }

        /**
         * Build a GotoAbsPosDualCommand with speedA, speedB, maxPower, endState, and profile as Int.
         */
        @JvmStatic
        fun build(
            port: Port,
            action: StartupCompletion,
            absPosA: Int,
            absPosB: Int,
            speedA: Int,
            speedB: Int,
            maxPower: Int,
            endState: Int,
            profile: Int
        ): GotoAbsPosDualCommand {
            return GotoAbsPosDualCommand(
                port.wrap(),
                action.wrap(),
                absPosA,
                absPosB,
                speedA.toByte(),
                speedB.toByte(),
                maxPower.toByte(),
                endState.toByte(),
                profile.toByte()
            )
        }
    }

    override fun toString(): String {
        return "GotoAbsPosDualCommand(port=$port, sc=$action, absPosA=$absPosA, absPosB=$absPosB, speedA=$speedA H[${LWP.convertToHexString(speedA)}], speedB=$speedB H[${LWP.convertToHexString(speedB)}], maxPower=$maxPower H[${LWP.convertToHexString(maxPower)}], endState=$endState H[${LWP.convertToHexString(endState)}], profile=$profile H[${LWP.convertToHexString(profile)}])"
    }
}