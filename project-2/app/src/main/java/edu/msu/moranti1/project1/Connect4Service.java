package edu.msu.moranti1.project1;



import static edu.msu.moranti1.project1.Cloud.CAT_PATH;
import static edu.msu.moranti1.project1.Cloud.CREATE_USER_PATH;
import static edu.msu.moranti1.project1.Cloud.LOGIN_PATH;
import static edu.msu.moranti1.project1.Cloud.FCM_REGISTER_PATH;


import edu.msu.moranti1.project1.Model.Catalog;
import edu.msu.moranti1.project1.Model.LoginResult;
import edu.msu.moranti1.project1.Model.SaveResult;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Connect4Service {
    @FormUrlEncoded
    @POST(LOGIN_PATH)
    Call<LoginResult> getlogin(
            @Field("name") String userId,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST(CREATE_USER_PATH)
    Call<LoginResult> getcreateuser(
            @Field("name") String userId,
            @Field("password") String password
    );

    @GET(CAT_PATH)
    Call<Catalog> getcatalog(
            @Query("user") String userId,
            @Query("magic") String magic,
            @Query("pw") String password
    );

    @FormUrlEncoded
    @POST(FCM_REGISTER_PATH)
    Call<SaveResult> registerFCM(
            @Field("user") String userName,
            @Field("fcmid") String fcmid
    );
}
