package in.mvcdemo.Net;


import in.mvcdemo.Model.AddPlayer;
import in.mvcdemo.Model.GetAllEvent;
import in.mvcdemo.Model.GetCity;
import in.mvcdemo.Model.GetPlayerEvent;
import in.mvcdemo.Model.GetPlayerProfile;
import in.mvcdemo.Model.GetScore;
import in.mvcdemo.Model.GetTeamName;
import in.mvcdemo.Model.Instruction;
import in.mvcdemo.Model.UpdatePlayer;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.mime.TypedFile;

public interface RestClient {

    @GET("/getcitylist")
    void getAllCity(Callback<GetCity> callback);

//    //    http://www.wpfix247.com/theteammate/webservices/index.php/user/getallevent
//    @GET("/getallevent")
//    void getAllEvent(Callback<GetAllEvent> callback);

    @GET("/getalleventbycity")
    void getAllEventByCity(@Query("city") String city, Callback<GetAllEvent> callback);

    @GET("/getalleventbycity")
    void getPastEvent(@Query("city") String city, Callback<GetAllEvent> callback);

    @GET("/getalleventbycity")
    void getLiveEvent(@Query("city") String city, Callback<GetAllEvent> callback);

    @GET("/getalleventbycity")
    void getFutureEvent(@Query("city") String city, Callback<GetAllEvent> callback);

    @Multipart
    @POST("/addplayer")
    void addPlayer(@Part("name") String name,
                   @Part("gender") String gender,
                   @Part("propic") TypedFile propic,
                   @Part("eid") String eid,
                   @Part("coachname") String coachname,
                   @Part("tid") String tid,
                   @Part("shirtno") String shirtno,
                   @Part("other_attributes") String other_attributes,
                   @Part("position") String position,
                   Callback<AddPlayer> callback);

    @Multipart
    @POST("/editplayer")
    void editPlayer(@Part("name") String name,
                   @Part("gender") String gender,
                   @Part("propic") TypedFile propic,
                   @Part("eid") String eid,
                   @Part("coachname") String coachname,
                   @Part("tid") String tid,
                   @Part("shirtno") String shirtno,
                   @Part("other_attributes") String other_attributes,
                   @Part("position") String position, @Part("pid") String pid,
                   Callback<UpdatePlayer> callback);

    @GET("/getallplayerbyevent")
    void getPlayerByEvent(@Query("eid") String eid,
                          Callback<GetPlayerEvent> callback);

    @GET("/getallplayerbyteamname")
    void getPlayerByTeamName(@Query("teamname") String teamname, @Query("eid") String eid,
                             Callback<GetPlayerEvent> callback);

    @GET("/getallplayerbycoachname")
    void getPlayerByCoachName(@Query("coachname") String coachname, @Query("eid") String eid,
                              Callback<GetPlayerEvent> callback);

//    http://nametheteammate.com/admin/webservices/index.php/user/viewplayerinfo?name=Kenneth&jno=78&eid=11&tid=11
    @GET("/viewplayerinfo")
    void getPlayerProfile(@Query("name") String name, @Query("jno") String jno, @Query("eid") String eid,@Query("pid") String pid,
                          Callback<GetPlayerProfile> callback);

    //    http://wpfix247.com/theteammate/webservices/index.php/user/getinformation
    @GET("/getinformation")
    void getInstruction(Callback<Instruction> callback);


    //    http://nametheteammate.com/admin/webservices/index.php/user/getallteambyeid?eid=11
    @GET("/getallteambyeid")
    void getTeamName(@Query("eid") String eid, Callback<GetTeamName> callback);

    //    http://nametheteammate.com/admin/webservices/index.php/user/viewscore?eid=11
    @GET("/viewscore")
    void viewScore(@Query("eid") String eid, Callback<GetScore> callback);
}
