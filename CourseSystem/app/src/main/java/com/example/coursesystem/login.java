package com.example.coursesystem;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mob.MobSDK;


import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;



public class login extends AppCompatActivity {

    Button BT_getCode;
    Button BT_login;
    EditText ET_username;
    EditText ET_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MobSDK.init(this);

        ET_username = findViewById(R.id.ET_username);
        ET_password = findViewById(R.id.ET_password);
        BT_login = findViewById(R.id.BT_login);
        BT_getCode = findViewById(R.id.BT_getCode);

        EventHandler eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                // afterEvent会在子线程被调用，因此如果后续有UI相关操作，需要将数据发送到UI线程
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                new Handler(Looper.getMainLooper(), msg1 -> {
                    int event1 = msg1.arg1;
                    int result1 = msg1.arg2;
                    Object data1 = msg1.obj;
                    if (event1 == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        if (result1 == SMSSDK.RESULT_COMPLETE) {
                            // TODO 处理成功得到验证码的结果
                            // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                            Toast mToast = Toast.makeText(login.this, null, Toast.LENGTH_SHORT);
                            mToast.setText("验证码已发送！");
                            mToast.show();
                        } else {
                            // TODO 处理错误的结果
                            Toast mToast = Toast.makeText(login.this, null, Toast.LENGTH_SHORT);
                            mToast.setText("验证码发送失败！");
                            mToast.show();
                            ((Throwable) data1).printStackTrace();
                        }
                    } else if (event1 == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        if (result1 == SMSSDK.RESULT_COMPLETE) {
                            // TODO 处理验证码验证通过的结果
                            Toast mToast = Toast.makeText(login.this, null, Toast.LENGTH_SHORT);
                            mToast.setText("验证码正确！");
                            mToast.show();
                            //跳转到别的界面去
                            Intent intent = new Intent(login.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            // TODO 处理错误的结果
                            Toast mToast = Toast.makeText(login.this, null, Toast.LENGTH_SHORT);
                            mToast.setText("验证码错误！");
                            mToast.show();
                            ((Throwable) data1).printStackTrace();
                        }
                    }
                    // TODO 其他接口的返回结果也类似，根据event判断当前数据属于哪个接口
                    return false;
                }).sendMessage(msg);
            }
        };
// 注册一个事件回调，用于处理SMSSDK接口请求的结果
        SMSSDK.registerEventHandler(eventHandler);

        BT_getCode.setOnClickListener(view -> {
            if (ET_username.getText().length() != 11) {
                Toast mToast = Toast.makeText(login.this, null, Toast.LENGTH_SHORT);
                mToast.setText("请输入正确的手机号！");
                mToast.show();
            }

            /*else if (password.getText().length() != 4)
                Toast.makeText(login.this, "您输入的密码长度错误", Toast.LENGTH_SHORT).show();*/
            else {
                SMSSDK.getVerificationCode("+86", ET_username.getText().toString());
                timer.start();
            }
        });

        BT_login.setOnClickListener(view -> {
            Intent intent = new Intent(login.this, MainActivity.class);
            startActivity(intent);

            //SMSSDK.submitVerificationCode("+86", ET_username.getText().toString(), ET_password.getText().toString());
        });
    }

    public void retrieve_Password(View view) {
        Intent intent = new Intent(this, forgetPassword.class);
        startActivity(intent);
    }

    public void signin(View view) {
        Intent intent = new Intent(this, signin.class);
        startActivity(intent);
    }

    private CountDownTimer timer = new CountDownTimer(60000,1000) {
        @Override
        public void onTick(long l) {
            BT_getCode.setText((l/1000)+"秒后重试");
            BT_getCode.setBackgroundColor(Color.LTGRAY);
            BT_getCode.setEnabled(false);
        }

        @Override
        public void onFinish() {
            BT_getCode.setEnabled(true);
            BT_getCode.setText("获取验证码");
            BT_getCode.setBackgroundColor(Color.CYAN);
        }
    };

    public void login_password(View view) {
        Intent intent = new Intent(this,login_pwd.class);
        startActivity(intent);
    }
}

