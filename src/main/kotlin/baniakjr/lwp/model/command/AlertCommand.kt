package baniakjr.lwp.model.command

import baniakjr.lwp.*
import baniakjr.lwp.LWPByteValue.Companion.wrap
import baniakjr.lwp.model.LWPCommand
import baniakjr.lwp.model.LWPCommand.Companion.isSpecificCommand
import baniakjr.lwp.model.Wrapper

class AlertCommand internal constructor(
    private val alertType: Wrapper<AlertType>,
    private val alertOperation: Wrapper<AlertOperation>,
    private val payload: Byte? = null
) : LWPCommand {

    override val command: Wrapper<Command> = Command.ALERT.wrap()

    override val byteValue: ByteArray
        get() {
            return if(payload == null) {
                LWP.createCommand(byteArrayOf(command.value, alertType.value, alertOperation.value))
            } else {
                LWP.createCommand(byteArrayOf(command.value, alertType.value, alertOperation.value, payload))
            }
        }

    companion object {
        internal fun fromByteArray(byteArray: ByteArray): LWPCommand {
            if(byteArray.size < AlertType.MSG_WO_DATA_LENGTH ||
                byteArray.size > AlertType.MAX_MSG_LENGTH ||
                !byteArray.isSpecificCommand(Command.ALERT)) {
                return MalformedCommand(byteArray)
            }
            val alertType = Wrapper.wrap(AlertType::class.java, byteArray[AlertType.IN_MESSAGE_INDEX])
            val alertOperation = Wrapper.wrap(AlertOperation::class.java, byteArray[AlertOperation.IN_MESSAGE_INDEX])
            if(byteArray.size == AlertType.MAX_MSG_LENGTH) {
                return AlertCommand(alertType, alertOperation, byteArray[AlertType.DATA_INDEX])
            }
            return AlertCommand(alertType, alertOperation)
        }

        @JvmStatic
        fun build(alertType: AlertType, alertOperation: AlertOperation): AlertCommand {
            return AlertCommand(alertType.wrap(), alertOperation.wrap())
        }
    }

    override fun toString(): String {
        return when(payload) {
            AlertType.STATUS_OK -> "AlertCommand(alertType=$alertType, alertOperation=$alertOperation, status=OK)"
            AlertType.STATUS_ERROR -> "AlertCommand(alertType=$alertType, alertOperation=$alertOperation, status=ERROR)"
            null -> "AlertCommand(alertType=$alertType, alertOperation=$alertOperation)"
            else -> "AlertCommand(alertType=$alertType, alertOperation=$alertOperation, status=${LWP.convertToHexString(payload)})"
        }
    }


}