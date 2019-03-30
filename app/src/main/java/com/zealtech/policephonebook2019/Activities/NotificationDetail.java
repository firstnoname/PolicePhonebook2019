package com.zealtech.policephonebook2019.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.policephonebook2019.R;
import com.zealtech.policephonebook2019.Config.Api;
import com.zealtech.policephonebook2019.Model.Notification;
import com.zealtech.policephonebook2019.Model.response.ResponseNotification;
import com.zealtech.policephonebook2019.Util.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationDetail extends AppCompatActivity {

    private static final String TAG = "NotificationDetail";

    private TextView tvAbTitle, tvAlertUpdateApp, tvCreateDate, tvNotiDepartment, tvNotiDetail;
    private ImageView imgIconNoti, imgNoti;
    private ImageView imgBack;

    private Api api = AppUtils.getApiService();
    private ArrayList<String> readGroups;
    private String id = "5c9c9a82c9e77c00016d43e9";
    ArrayList<Notification> mNotification = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail);

        tvAbTitle = findViewById(R.id.tv_actionbar_back);
        imgBack = findViewById(R.id.img_back);
        tvAlertUpdateApp = findViewById(R.id.tv_alert_update_application);
        tvCreateDate = findViewById(R.id.tv_create_date);
        tvNotiDepartment = findViewById(R.id.tv_noti_department);
        tvNotiDetail = findViewById(R.id.tv_noti_detail);
        imgNoti = findViewById(R.id.img_noti);

//        Customize action bar.
        tvAbTitle.setText("รายละเอียด");

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        Bundle intent = getIntent().getExtras();
//        if (intent != null) {
//            String id = intent.getString("id");
//            /*String messageBody = intent.getString("messageBody");
//            String contentType = intent.getString("contentType");
//            String notification = intent.getString("notification ");*/
//
//            Log.d(TAG, id);
//        } else {
//            Log.d(TAG, "bundle null");
//        }

        callApiNoti();

    }

    private void callApiNoti() {
        Call<ResponseNotification> call = api.getNotifications(id);
        call.enqueue(new Callback<ResponseNotification>() {
            @Override
            public void onResponse(Call<ResponseNotification> call, Response<ResponseNotification> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equalsIgnoreCase("OK")) {
                        if (response.body().getCode().equals("OK")) {
                            mNotification.addAll(response.body().getData().getContent());
                            setDataToLayout();
                        } else {
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        if (jObjError.has("code") && jObjError.get("message").equals("ไม่พบผู้ใช้งาน")) {
                            Log.d(TAG, response.errorBody().string());
                        } else if (jObjError.has("message") && jObjError.get("message").equals("ไม่พบผู้ใช้งาน")){
                            Log.d(TAG, response.errorBody().string());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseNotification> call, Throwable t) {
                Log.d(TAG, String.valueOf(call));
                Log.d(TAG, String.valueOf(t));
            }
        });
    }

    private void setDataToLayout() {
        tvCreateDate.setText(mNotification.get(0).getCreateDate());
        tvNotiDepartment.setText(mNotification.get(0).getAuthor());
        tvNotiDetail.setText(mNotification.get(0).getDescription());
    }
}
