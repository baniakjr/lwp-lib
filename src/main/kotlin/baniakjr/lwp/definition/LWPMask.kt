package baniakjr.lwp.definition

import java.util.stream.Collectors

/**
 * Common interface for all LWP enums that have a byte value that is a bit mask.
 */
class LWPMask<T : LWPMaskValue?> : LWPByteValue {
    private val maskValues: MutableSet<T> = HashSet()

    @SafeVarargs
    constructor(vararg values: T) {
        maskValues.addAll(listOf(*values))
    }

    constructor(values: List<T>) {
        maskValues.addAll(values)
    }

    fun addValue(value: T) {
        maskValues.add(value)
    }

    override val value: Byte
        get() = maskValues.sumOf { it!!.value.toInt() }.toByte()

    override fun toString(): String {
        return maskValues.stream().map { it.toString() }.collect(Collectors.joining(","))
    }

    companion object {

        @JvmStatic
        fun <R> fromByte(lwpMaskClass: Class<R>, byteValue: Byte): LWPMask<R> where R : Enum<R>, R : LWPMaskValue {
            val foundValues: MutableList<R> = ArrayList()
            if (lwpMaskClass.enumConstants != null) {
                for (lwpMaskValue in lwpMaskClass.enumConstants) {
                    if ((byteValue.toInt() and lwpMaskValue!!.value.toInt()) == lwpMaskValue.value.toInt()) {
                        foundValues.add(lwpMaskValue)
                    }
                }
            }
            return LWPMask(foundValues)
        }
    }
}
