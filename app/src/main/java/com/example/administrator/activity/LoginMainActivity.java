package com.example.administrator.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.example.administrator.R;
import com.example.administrator.entity.Constants;
import com.example.administrator.util.GetDataUtil;

/**
 * 欢迎页面
 * @author dongli
 *
 */
public class LoginMainActivity extends Activity{

	private Context mContext;
	private final static int close = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_main);
		mContext = this;
		showMainpage();
		GetDataUtil.put(mContext,Constants.USERINFO,Constants.FIRSTOPENAPP,"1");
	}
	public void showMainpage(){
		findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginMainActivity.this,LoginActivity.class);
				intent.putExtra(LoginActivity.ISCLOSE,true);
				LoginMainActivity.this.startActivityForResult(intent,close);
			}
		});
		findViewById(R.id.register_btn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginMainActivity.this,RegisterActivity.class);
				LoginMainActivity.this.startActivity(intent);
			}
		});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case close:
				if (resultCode == RESULT_OK) {// dl repair
					LoginMainActivity.this.finish();
					return;
				}
				break;
		}
	}
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_UP
				&& event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			this.finish();
			System.exit(0);
		}
		return super.dispatchKeyEvent(event);
	}
	
}
