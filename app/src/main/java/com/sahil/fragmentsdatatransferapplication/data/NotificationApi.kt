package com.sahil.fragmentsdatatransferapplication.data

import com.sahil.fragmentsdatatransferapplication.utils.AppConstants.Companion.CONTENT_TYPE
import com.sahil.fragmentsdatatransferapplication.utils.AppConstants.Companion.SERVER_KEY
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationApi {
    @Headers("Authorization: key=$SERVER_KEY", "Content-Type:$CONTENT_TYPE")
    @POST("fcm/send")
    suspend fun postNotificaion(
        @Body notification: PushNotification
    ):Response<ResponseBody>
}