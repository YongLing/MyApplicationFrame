/**
 * zuibon.com Inc.
 * Copyright (c) 2012-2015 All Rights Reserved.
 */

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

/**
 * 
 * @author dolphin0520
 * @version $Id: BaseActivity.java, v 0.1 2015-5-8 上午11:48:35 dolphin0520 Exp $
 */
public class BaseActivity extends Activity {

	protected Dialog loadingDialog;

	LayoutInflater inflater;

	public MainApplication getApp() {
		return ((MainApplication) getApplication()).getInstance();
	}

	public void showToast(String content) {
		Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
	}

	public static final String CONTENT_VIEW = "content_view";

	/**
	 * 更新UserScore、titleBar数据
	 * 

	 *            <Award> awards
	 */


//	protected void showLoading() {
//		// TODO Auto-generated method stub
//		tipHudDiag = new TipHudDiag(this, "Loading...", R.drawable.drop_down_list_arrow,
//				AnimationUtils.loadAnimation(this, R.anim.loading_annimation));
//		tipHudDiag.show();
//	}
//
//	protected void dismissLoading() {
//		if (tipHudDiag != null && tipHudDiag.isShowing()) {
//			tipHudDiag.dismiss();
//		}
//	}

	/**
	 * 更新titleBar样式
	 * 
	 */
	private void setTitleBarStyle(final boolean isWhite, final Activity current) {
//		LinearLayout titleInfoBarView = (LinearLayout) findViewById(R.id.title_info_bar_view);
//		int textStyleColor = getResources().getColor(R.color.white);
//		int xpTextStyleColor = getResources().getColor(R.color.orange);
//		int backColor = R.color.title_info_bar_black;
//		if (isWhite) {
//			textStyleColor = getResources().getColor(
//					R.color.title_info_bar_gray);
//			backColor = R.color.white;
//		}
//		titleInfoBarView.setBackgroundColor(getResources().getColor(backColor));
//		findViewById(R.id.split_lines_1).setBackgroundColor(textStyleColor);
//		findViewById(R.id.split_lines_2).setBackgroundColor(textStyleColor);
//		TextView titleBarLevel = (TextView) findViewById(R.id.title_bar_level);
//		titleBarLevel.setTextColor(textStyleColor);
//
//		TextView titleBarXp = (TextView) findViewById(R.id.title_bar_xp);
//		titleBarXp.setTextColor(xpTextStyleColor);
//
//		TextView titleBarCorpCount = (TextView) findViewById(R.id.title_bar_cop_count);
//		titleBarCorpCount.setTextColor(textStyleColor);
//
//		TextView titleBarGiveCount = (TextView) findViewById(R.id.title_bar_give_count);
//		titleBarGiveCount.setTextColor(textStyleColor);
//
//		TextView titleBarCoinCount = (TextView) findViewById(R.id.title_bar_coins_count);
//		titleBarCoinCount.setTextColor(textStyleColor);
//
//		findViewById(R.id.title_info_bar_bottom_border).setVisibility(
//				View.VISIBLE);
//		titleInfoBarView.setVisibility(View.VISIBLE);
//		Animation animation = AnimationUtils.loadAnimation(current,
//				R.anim.slide_in_from_top);
//		titleInfoBarView.startAnimation(animation);
	}


	public void showProgressDlg(String title, String msg) {

//		LayoutInflater inflater = LayoutInflater.from(this);
//		View v = inflater.inflate(R.layout.progress_dlg_layout, null);
//		LinearLayout layout = (LinearLayout) v.findViewById(R.id.progress_dlg);
//		ImageView progressImg = (ImageView) v
//				.findViewById(R.id.loading_progress_img);
//
//		Animation an = AnimationUtils.loadAnimation(this,
//				R.anim.loading_annimation);
//		progressImg.startAnimation(an);
//
//		loadingDialog = new Dialog(this, R.style.loading_dialog);// 创建自定义样式dialog
//
//		loadingDialog.setCancelable(true);// 不可以用“返回键”取消
//		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
//				LinearLayout.LayoutParams.WRAP_CONTENT,
//				LinearLayout.LayoutParams.WRAP_CONTENT));// 设置布局
//
//		loadingDialog.show();
	}

	public void canleProgressDlg() {
		if (this.loadingDialog != null) {
			loadingDialog.cancel();
		}
	}

	@Override
	protected void onDestroy() {
		Log.d(this.getClass().getName(), "finsh current activity");
		super.onDestroy();
	}

	public String getResString(int id) {
		return getResources().getString(id);
	}

	public String[] getResStringArray(int resId) {
		return getResources().getStringArray(resId);
	}

	public int[] getResIntegerArray(int resId) {
		return getResources().getIntArray(resId);
	}

	public Drawable getResDrawable(int id) {
		return getResources().getDrawable(id);
	}

	/**
	 * 退出当前的app 原理，跳入到欢迎页，清除欢迎页上面所有的activity同时配合欢迎页的代码finish self
	 * 
	 */
	protected void exitApp() {
		this.finish();
//		Intent intent = new Intent(getApplicationContext(), GuideActivity.class);
//		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		intent.putExtra("EXIT", true);
//		startActivity(intent);
	}

}
