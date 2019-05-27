package com.example.presensi.Api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {

    @Headers(
        "Content-Type:application/json",
        "X-Requested-With:XMLHttpRequest"
    )
    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @Headers(
        "Content-Type:application/json",
        "X-Requested-With:XMLHttpRequest"
    )
    @GET("auth/logout")
    fun logout(@Header("Authorization") token: String): Call<LogoutResponse>

    @Headers(
        "Content-Type:application/json",
        "X-Requested-With:XMLHttpRequest"
    )
    @POST("verify_token")
    fun scanQrCode(@Header("Authorization") token: String, @Body request: AbsenRequest): Call<AbsenResponse>

    @Headers(
        "Content-Type:application/json",
        "X-Requested-With:XMLHttpRequest"
    )
    @GET("auth/user")
    fun getProfile(@Header("Authorization") token: String): Call<ProfileResponse>

    @Headers(
        "Content-Type:application/json",
        "X-Requested-With:XMLHttpRequest"
    )
    @GET("auth/courses/{id}")
    fun getMatkul(@Header("Authorization") token: String, @Path ("id") id: String): Call<List<MatkulResponse>>

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
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("subject")
    val subject: Subject
)

data class Subject(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("kode")
    val kode: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("updated_at")
    val updatedAt: String
)
data class LoginRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)

data class LoginResponse(
    @SerializedName("access_token")
    val token: String,
    @SerializedName("expires_at")
    val expiresAt: String,
    @SerializedName("token_type")
    val tokenType: String
)

data class ProfileResponse(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("email_verified_at")
    val emailVerifiedAt: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("ni")
    val ni: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("updated_at")
    val updatedAt: String
)

data class LogoutResponse(
    @SerializedName("message")
    val message: String
)


data class MatkulResponse(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("day")
    val day: String,
    @SerializedName("end_time")
    val endTime: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("lecturer")
    val lecturer: Lecturer,
    @SerializedName("lecturer_id")
    val lecturerId: Int,
    @SerializedName("pivot")
    val pivot: Pivot,
    @SerializedName("ruang")
    val ruang: Ruang,
    @SerializedName("ruang_id")
    val ruangId: Int,
    @SerializedName("sks")
    val sks: Int,
    @SerializedName("start_time")
    val startTime: String,
    @SerializedName("subject")
    val subject: SubjectM,
    @SerializedName("subject_id")
    val subjectId: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_attend")
    val userAttend: Boolean
)

data class SubjectM(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("kode")
    val kode: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("updated_at")
    val updatedAt: String
)

data class Lecturer(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("email_verified_at")
    val emailVerifiedAt: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("ni")
    val ni: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("updated_at")
    val updatedAt: String
)

data class Ruang(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("updated_at")
    val updatedAt: String
)

data class Pivot(
    @SerializedName("course_id")
    val courseId: Int,
    @SerializedName("student_id")
    val studentId: Int
)
