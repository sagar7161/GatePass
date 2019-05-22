package com.example.gatepass;

import com.example.gatepass.Notifications.MyResponse;
import com.example.gatepass.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAA2fLjmnU:APA91bGy3a2PSzyqg1A62QGu-81h-yT2Li-no91tAOGLzGMcd1ztmtKG85u39eRd97WWsmYMpidRg73G8_PNnHKV5PmpT-yr4j0v2wIVbyLuHzDXmocYkk-EBIz4tcbOhAfTFypUJMdO"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
