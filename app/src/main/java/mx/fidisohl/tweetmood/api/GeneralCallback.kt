package mx.fidisohl.tweetmood.api


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class GeneralCallback<T> : Callback<T> {

    abstract fun success(data: T)

    abstract fun controlNoSuccessful(responseCode: Int)

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful && response.body() != null) {
            success(response.body()!!)
        } else {
            controlNoSuccessful(response.code())
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        t.printStackTrace()
        if (call.isCanceled) {
            return
        }
    }
}