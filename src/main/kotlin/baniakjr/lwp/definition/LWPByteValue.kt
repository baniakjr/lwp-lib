package baniakjr.lwp.definition

import baniakjr.lwp.model.Wrapper

/**
 * Common interface for all LWP enums that have a byte value.
 */
interface LWPByteValue {

    val value: Byte

    companion object {

        @JvmStatic
        fun <R> fromByte(lwpClass: Class<R>, byteValue: Byte): R? where R : Enum<R>, R : LWPByteValue {
            if (lwpClass.enumConstants != null) {
                for (lwpEnum in lwpClass.enumConstants) {
                    if (byteValue == lwpEnum!!.value) {
                        return lwpEnum
                    }
                }
            }
            return null
        }

        fun <C> C.wrap(): Wrapper<C> where C : Enum<C>, C : LWPByteValue {
            return Wrapper(this, this.value)
        }
    }
}
