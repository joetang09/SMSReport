package com.sergey.smsreport.act;

import android.content.Intent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sergey.framework.core.BaseActivity;
import com.sergey.smsreport.R;

public class SplashScreenActivity extends BaseActivity{

	private RelativeLayout rlFlashScreen = null;
	private TextView tvDescription = null;
	
	@Override
	protected int setView() {
		return R.layout.activity_splash_screen;
	}

	@Override
	protected void initView() {
		rlFlashScreen = (RelativeLayout)findViewById(R.id.ll_flash_screen);
		tvDescription = (TextView)findViewById(R.id.tv_description);
	}

	@Override
	protected void initAction() {
		tvDescription.setText("SMSReport");
		AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
		alphaAnimation.setDuration(2000);
		rlFlashScreen.startAnimation(alphaAnimation);
		alphaAnimation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {}
			@Override
			public void onAnimationRepeat(Animation animation) {}
			@Override
			public void onAnimationEnd(Animation animation) {
				Intent intent = new Intent();
				intent.setClass(SplashScreenActivity.this, MainActivity.class);
				SplashScreenActivity.this.jumpAndKillself(intent);
			}
		});
	}
}
