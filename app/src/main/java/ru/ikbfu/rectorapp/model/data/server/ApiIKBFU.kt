package ru.ikbfu.rectorapp.model.data.server

import androidx.annotation.Keep
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.ikbfu.rectorapp.model.data.server.model.*

@Keep
interface ApiIKBFU {

    @POST("/rector_app_auth")
    fun login(@Body loginRequest: LoginRequest): Single<LoginResponse>

    @GET("/api/user/user_info")
    fun getUserInfo(): Single<User>

    @GET("/api/priemka/get_current_year_count")
    fun getCurrentPriemCount(): Single<Int>

    @GET("/api/priemka/get_priem")
    fun getPriemka(): Single<List<SelectionCommitteeElement>>

    @GET("api/StudentContingent/get_graduate_years")
    fun getGraduatedYears(): Single<List<Int>>

    @GET("api/StudentContingent/get_graduated_students")
    fun getGraduates(): Single<List<Graduate>>

    @GET("api/StudentContingent/get_current_students")
    fun getCurrentStudents(): Single<List<Student>>

    @GET("api/science/get_science_graph")
    fun getScience(): Single<List<ScienceItem>>

    @GET("api/staff/get_staff")
    fun getStaff(): Single<List<StaffHierarchyItem>>
}