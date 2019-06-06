package com.zealtech.policephonebook2019.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.policephonebook2019.R;
import com.zealtech.policephonebook2019.Config.Api;
import com.zealtech.policephonebook2019.Config.ApplicationConfig;
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
    private static final String IMAGE_URL = ApplicationConfig.getImageUrl();

    private TextView tvAbTitle, tvAlertUpdateApp, tvCreateDate, tvNotiAuthor, tvNotiDetail;
    private ImageView imgIconNoti, imgNoti;
    private ImageView imgBack;

    private Api api = AppUtils.getApiService();
    private ArrayList<String> readGroups;
    private String noti_id = "";
    ArrayList<Notification> mNotification = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail);

        tvAbTitle = findViewById(R.id.tv_actionbar_back);
        imgBack = findViewById(R.id.img_back);
        tvAlertUpdateApp = findViewById(R.id.tv_alert_update_application);
        tvCreateDate = findViewById(R.id.tv_create_date);
        tvNotiAuthor = findViewById(R.id.tv_noti_author);
        tvNotiDetail = findViewById(R.id.tv_noti_detail);
        imgNoti = findViewById(R.id.img_noti);
        imgIconNoti = findViewById(R.id.img_icon_noti);

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

        Notification noti = (Notification) getIntent().getSerializableExtra("noti_detail");
        noti_id = noti.getId();
        callApiNoti(noti_id);

    }

    private void callApiNoti(String id) {
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

        tvAlertUpdateApp.setText(mNotification.get(0).getTitle());
        if (mNotification.size() != 0) {
            Glide.with(this).load(IMAGE_URL + mNotification.get(0).getAuthorProfile().getImageProfile()).fitCenter().into(imgIconNoti);

            if (mNotification.get(0).getPicturesPath().size() != 0) {
                Glide.with(this).load(IMAGE_URL + mNotification.get(0).getPicturesPath().get(0)).fitCenter().into(imgNoti);
            } else {
                imgNoti.setVisibility(View.GONE);
            }

            tvNotiAuthor.setText(mNotification.get(0).getAuthorProfile().getFirstName() + " " + mNotification.get(0).getAuthorProfile().getLastName());
            tvNotiDetail.setText(mNotification.get(0).getDescription());

            String dateFormat = mNotification.get(0).getCreateDate().substring(0,10);
            String date = dateFormat.substring(8);
            String month = dateFormat.substring(5);
            month = month.substring(0, 2);
            if (month.equals("01")) {
                month = "มกราคม";
            }
            if (month.equals("02")) {
                month = "กุมภาพันธ์";
            }
            if (month.equals("03")) {
                month = "มีนาคม";
            }
            if (month.equals("04")) {
                month = "เมษายน";
            }
            if (month.equals("05")) {
                month = "พฤษภาคม";
            }
            if (month.equals("06")) {
                month = "มิถุนายน";
            }
            if (month.equals("07")) {
                month = "กรกฎาคม";
            }
            if (month.equals("08")) {
                month = "สิงหาคม";
            }
            if (month.equals("09")) {
                month = "กันยายน";
            }
            if (month.equals("10")) {
                month = "ตุลาคม";
            }
            if (month.equals("11")) {
                month = "พฤษจิกายน";
            }
            if (month.equals("12")) {
                month = "ธันวาคม";
            }
            String year = dateFormat.substring(0, 4);

            tvCreateDate.setText("วันที่อัพเดทข้อมูล " + date + " " + month + " " + year);
        }

    }
}
