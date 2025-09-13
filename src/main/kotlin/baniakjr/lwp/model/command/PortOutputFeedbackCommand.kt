package baniakjr.lwp.model.command

import baniakjr.lwp.definition.LWPByteValue.Companion.wrap
import baniakjr.lwp.definition.LWPMask
import baniakjr.lwp.definition.mask.PortFeedback
import baniakjr.lwp.definition.value.Command
import baniakjr.lwp.definition.value.Port
import baniakjr.lwp.model.LWPCommand
import baniakjr.lwp.model.LWPCommand.Companion.createCommand
import baniakjr.lwp.model.LWPCommand.Companion.isSpecificCommand
import baniakjr.lwp.model.Wrapper

class PortOutputFeedbackCommand internal constructor(
    val feedback: List<PortFeedbackData>
) : LWPCommand {

    override val command: Wrapper<Command> = Command.PORT_OUTPUT_COMMAND_FEEDBACK.wrap()

    override val byteValue: ByteArray
        get() {
            val fbArray = feedback.map { it.asArray() }.fold(byteArrayOf()){ acc, next -> acc + next }
            return (byteArrayOf(command.value) + fbArray).createCommand()
        }

    companion object {
        internal fun fromByteArray(byteArray: ByteArray): LWPCommand {
            if (byteArray.size < 5 ||
                byteArray.size % 2 == 0 ||
                !byteArray.isSpecificCommand(Command.PORT_OUTPUT_COMMAND_FEEDBACK)) {
                return MalformedCommand(byteArray)
            }
            val feedback: MutableList<PortFeedbackData> = mutableListOf()
            val payload = byteArray.copyOfRange(3, byteArray.size)
            for(index in payload.indices step 2) {
                feedback.add(PortFeedbackData(Wrapper.wrap(Port::class.java, payload[index]), LWPMask.fromByte(PortFeedback::class.java, payload[index + 1])))
            }
            return PortOutputFeedbackCommand(feedback)
        }
    }

    override fun toString(): String {
        return "PortOutputFeedbackCommand(feedback=$feedback)"
    }

    class PortFeedbackData(val port: Wrapper<Port>, val feedback: LWPMask<PortFeedback>) {

        override fun toString(): String {
            return "port=$port, feedback=$feedback"
        }

        fun asArray(): ByteArray {
            return byteArrayOf(port.value, feedback.value)
        }
    }
}