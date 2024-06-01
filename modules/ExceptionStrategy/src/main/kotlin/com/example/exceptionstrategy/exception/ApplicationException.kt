package com.example.exceptionstrategy.exception

class ApplicationException(
    val exceptionCode: IException,
    override val message: String,
    originException: Throwable? = null
) : RuntimeException(originException) {

//    constructor(exceptionCode: IException, originException: Throwable? = null, vararg args: Any) :
//            this(exceptionCode, originException, exceptionCode.formatMessage(*args))

    companion object {
        fun wrap(exceptionCode: IException, originException: Throwable): ApplicationException {
            val message = originException.message ?: originException.javaClass.simpleName
            return ApplicationException(exceptionCode, message, originException)
        }

        fun custom(exceptionCode: IException, vararg args: Any): ApplicationException {
            return ApplicationException(exceptionCode, exceptionCode.formatMessage(*args))
        }
    }
}