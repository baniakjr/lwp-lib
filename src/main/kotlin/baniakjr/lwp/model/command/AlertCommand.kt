package baniakjr.lwp.model.command

import baniakjr.lwp.AlertOperation
import baniakjr.lwp.AlertType
import baniakjr.lwp.Command
import baniakjr.lwp.LWP
import baniakjr.lwp.LWPByteValue.Companion.wrap
import baniakjr.lwp.model.LWPCommand
import baniakjr.lwp.model.LWPCommand.Companion.createCommand
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
                byteArrayOf(command.value, alertType.value, alertOperation.value).createCommand()
            } else {
                byteArrayOf(command.value, alertType.value, alertOperation.value, payload).createCommand()
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

        @JvmStatic
        fun enableUpdate(alertType: AlertType): AlertCommand {
            return build(alertType, AlertOperation.ENABLE_UPDATES)
        }

        @JvmStatic
        fun disableUpdate(alertType: AlertType): AlertCommand {
            return build(alertType, AlertOperation.DISABLE_UPDATES)
        }

        @JvmStatic
        fun request(alertType: AlertType): AlertCommand {
            return build(alertType, AlertOperation.REQUEST_UPDATE)
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