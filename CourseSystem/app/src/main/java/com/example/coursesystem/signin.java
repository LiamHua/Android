package com.example.coursesystem;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class signin extends AppCompatActivity {

    EditText ET_username_signin;
    EditText ET_password_signin;
    Button BT_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        ET_username_signin = findViewById(R.id.ET_username_signin);
        ET_password_signin = findViewById(R.id.ET_password_signin);
        BT_register = findViewById(R.id.BT_register);


        BT_register.setOnClickListener(v -> new Thread(() -> {
            Looper.prepare();
            if (ET_username_signin.getText().toString().length() != 11) {
                Toast mToast = Toast.makeText(signin.this, null, Toast.LENGTH_SHORT);
                mToast.setText("请输入正确的手机号哦！");
                mToast.show();
            } else if (ET_password_signin.getText().toString().length() < 10) {
                Toast mToast = Toast.makeText(signin.this, null, Toast.LENGTH_SHORT);
                mToast.setText("请输入10位及以上密码哦！");
                mToast.show();
            } else {
                SoapObject request = new SoapObject("pers.liam.countManager.MyService", "addUser");
                request.addProperty("arg0", ET_username_signin.getText().toString().trim());
                request.addProperty("arg1", ET_password_signin.getText().toString().trim());
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.bodyOut = request;
                HttpTransportSE ht = new HttpTransportSE("http://hzmaple.club/MyWeb_war/MyService.wsdl");
                try {
                    ht.call("pers.liam.countManager.MyService/addUser", envelope);
                    if (envelope.getResponse() != null) {
                        SoapObject soapObject = (SoapObject) envelope.bodyIn;
                        String n = soapObject.getProperty(0).toString();
                        if (n.equals("1")) {
                            Toast mToast = Toast.makeText(signin.this, null, Toast.LENGTH_SHORT);
                            mToast.setText("该用户已注册，请再试一下吧！");
                            mToast.show();
                        }

                        if (n.equals("2")) {
                            Toast mToast = Toast.makeText(signin.this, null, Toast.LENGTH_SHORT);
                            mToast.setText("注册成功");
                            mToast.show();
                            Intent intent = new Intent(signin.this, login.class);
                            startActivity(intent);
                        }
                        if (n.equals("3")) {
                            Toast mToast = Toast.makeText(signin.this, null, Toast.LENGTH_SHORT);
                            mToast.setText("服务器异常！");
                            mToast.show();
                        }
                    }
                } catch (IOException | XmlPullParserException e) {
                    e.printStackTrace();
                }
            }
            Looper.loop();
        }).start());

    }
}
