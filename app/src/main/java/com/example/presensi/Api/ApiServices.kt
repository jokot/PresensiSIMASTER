package com.example.presensi.Api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiServices{

    @Headers("Content-Type:application/json",
        "X-Requested-With:XMLHttpRequest")
    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("verify_token")
    fun scanQrCode(@Header("token") token:String, @Body request: AbsenRequest): Call<AbsenResponse>

}

data class AbsenRequest(
    @SerializedName("meeting_id")
    val meetingId: Int,
    @SerializedName("token")
    val token: String
)

data class AbsenResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("status")
    val status: String
)

data class LoginRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)
data class LoginResponse(
    @SerializedName("access_token")
    val token: String
)