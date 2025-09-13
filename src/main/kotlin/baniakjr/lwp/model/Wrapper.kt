package baniakjr.lwp.model

import baniakjr.lwp.LWP
import baniakjr.lwp.definition.LWPByteValue

/**
 * Wrapper for any [LWPByteValue enum][LWPByteValue] that can hold unknown byte value (not yet implemented in this library)
 */
class Wrapper<T> internal constructor(val enum: T? = null, private val byte: Byte? = null) : LWPByteValue
        where T : Enum<T>, T : LWPByteValue {

    companion object {

        @JvmStatic
        fun <C> wrap(lwpClass: Class<C>, value: Byte): Wrapper<C> where C : Enum<C>, C : LWPByteValue {
            val result = LWPByteValue.fromByte(lwpClass, value)
            return Wrapper(result, value)
        }

        @JvmStatic
        fun <C> wrap(enum: C): Wrapper<C> where C : Enum<C>, C : LWPByteValue {
            return Wrapper(enum, enum.value)
        }

        @JvmStatic
        fun <C> empty(): Wrapper<C> where C : Enum<C>, C : LWPByteValue {
            return Wrapper()
        }
    }

    fun isEmpty(): Boolean {
        return enum == null && byte == null
    }

    override val value: Byte
        get() = enum?.value?: byte?: 0x00

    override fun toString(): String {
        return enum?.name?: byteToHex()
    }

    private fun byteToHex(): String {
        if(byte!=null) {
            return "$byte H[${LWP.convertToHexString(byte)}]"
        }
        return ""
    }

}