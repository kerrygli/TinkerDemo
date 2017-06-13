package com.wipace.tinkerdemo;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tencent.tinker.lib.library.TinkerLoadLibrary;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;
import com.wipace.tinkerdemo.util.Utils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private Button mLoadPatch;
    private Button mLoadJar;
    private Button mClearPatch;
    private Button mKillSelf;
    private Button mShowMessage;
    private TextView mTvInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mLoadPatch = (Button) findViewById(R.id.id_btn_loadPatch);
        mLoadJar = (Button) findViewById(R.id.id_btn_loadLibrary);
        mClearPatch = (Button) findViewById(R.id.id_btn_cleanPatch);
        mKillSelf = (Button) findViewById(R.id.id_btn_killSelf);
        mShowMessage = (Button) findViewById(R.id.id_btn_showInfo);
        mTvInfo = (TextView) findViewById(R.id.id_textView);


        mLoadPatch.setOnClickListener(this);
        mLoadJar.setOnClickListener(this);
        mClearPatch.setOnClickListener(this);
        mKillSelf.setOnClickListener(this);
        mShowMessage.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        Log.e(TAG, "i am on onResume");

        super.onResume();
        Utils.setBackground(false);

    }

    @Override
    protected void onPause() {
        super.onPause();
        Utils.setBackground(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_btn_loadPatch:

                TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/patch_signed_7zip.apk");

                break;
            case R.id.id_btn_loadLibrary:

                TinkerLoadLibrary.installNavitveLibraryABI(getApplicationContext(), "armeabi");
                System.loadLibrary("stlport_shared");

                break;
            case R.id.id_btn_cleanPatch:
                Tinker.with(getApplicationContext()).cleanPatch();
                break;
            case R.id.id_btn_killSelf:
                ShareTinkerInternals.killAllOtherProcess(getApplicationContext());
                android.os.Process.killProcess(android.os.Process.myPid());
                break;
            case R.id.id_btn_showInfo:
                mTvInfo.setText("出现了一个bug！");
                break;

        }

    }
}
