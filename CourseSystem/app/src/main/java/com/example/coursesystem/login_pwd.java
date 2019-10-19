package com.example.coursesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class login_pwd extends AppCompatActivity {

    EditText ET_username_pwd;
    EditText ET_password_pwd;
    Button BT_login_pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pwd);

        ET_username_pwd = findViewById(R.id.ET_username_pwd);
        ET_password_pwd = findViewById(R.id.ET_password_pwd);
        BT_login_pwd = findViewById(R.id.BT_login_pwd);

        BT_login_pwd.setOnClickListener(v -> new Thread(() -> {
            Looper.prepare();
            SoapObject request = new SoapObject("pers.liam.countManager.MyService", "verify_userInfo");
            request.addProperty("arg0", ET_username_pwd.getText().toString().trim());
            request.addProperty("arg1", ET_password_pwd.getText().toString().trim());
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = request;
            HttpTransportSE ht = new HttpTransportSE("http://hzmaple.club/MyWeb_war/MyService.wsdl");
            try {
                ht.call("pers.liam.countManager.MyService/verify_userInfo", envelope);
                if (envelope.getResponse() != null) {
                    SoapObject soapObject = (SoapObject) envelope.bodyIn;
                    String n = soapObject.getProperty(0).toString();
                    if (n.equals("1")) {
                        Toast mToast = Toast.makeText(login_pwd.this, null, Toast.LENGTH_SHORT);
                        mToast.setText("该用户不存在，请再试一下吧！");
                        mToast.show();
                    }
                    if (n.equals("2")){
                        Toast mToast = Toast.makeText(login_pwd.this, null, Toast.LENGTH_SHORT);
                        mToast.setText("密码错误，请再检查一下吧！");
                        mToast.show();
                    }

                    if (n.equals("3")) {
                        Toast mToast = Toast.makeText(login_pwd.this, null, Toast.LENGTH_SHORT);
                        mToast.setText("登录成功！");
                        mToast.show();
                        Intent intent = new Intent(login_pwd.this, MainActivity.class);
                        startActivity(intent);
                    }
                    if (n.equals("0")) {
                        Toast mToast = Toast.makeText(login_pwd.this, null, Toast.LENGTH_SHORT);
                        mToast.setText("服务器异常！");
                        mToast.show();
                    }
                }
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }
            Looper.loop();
        }).start());

    }
    public void retrieve_Password(View view) {
        Intent intent = new Intent(this, forgetPassword.class);
        startActivity(intent);
    }

    public void signin(View view) {
        Intent intent = new Intent(this, signin.class);
        startActivity(intent);
    }
    public void login_code(View view) {
        Intent intent = new Intent(this,login.class);
        startActivity(intent);
    }
}
