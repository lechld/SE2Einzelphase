package at.aau.edu.lechl.se.singlephase.util

sealed class Resource<out T>(val data: T?) {
    class Success<T>(data: T? = null) : Resource<T>(data)
    class Failure<T>(val exception: Throwable, data: T? = null) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}
