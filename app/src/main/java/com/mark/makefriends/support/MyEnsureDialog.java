package com.mark.makefriends.support;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.mark.makefriends.R;

public class MyEnsureDialog extends Dialog {
	private TextView messageTV;

	@SuppressWarnings("deprecation")
	public MyEnsureDialog(Context context, String title, String message,
			String cancel_button, String ensure_button,
			final android.view.View.OnClickListener listener_cancel,
			final android.view.View.OnClickListener listener_ensure) {
		super(context, R.style.my_dialog);
		setContentView(R.layout.dialog_my_ensure);

		WindowManager m = ((Activity) context).getWindowManager();
		Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
		WindowManager.LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参数值
		p.width = (int) (d.getWidth() * 0.75); // 宽度设置为屏幕的0.65
		getWindow().setAttributes(p);

		((TextView) findViewById(R.id.tv_dialog_title)).setText(title);
		messageTV = ((TextView) findViewById(R.id.tv_dialog_message));
		messageTV.setText(message);
		Button button_ensure = (Button) findViewById(R.id.rl_ensure);
		button_ensure.setText(cancel_button);
		button_ensure
				.setOnClickListener(new android.view.View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if (listener_cancel != null) {
							listener_cancel.onClick(v);
						}
						dismiss();
					}
				});

		Button button_cancel = (Button) findViewById(R.id.btn_cancel);
		button_cancel.setText(ensure_button);
		button_cancel
				.setOnClickListener(new android.view.View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if (listener_ensure != null) {
							listener_ensure.onClick(v);
						}
						dismiss();
					}
				});
	}

	public void setMessage(String message) {
		if (messageTV != null) {
			messageTV.setText(message);
		}
	}

	public String getMessage() {
		if (messageTV != null) {
			return messageTV.getText().toString();
		}
		return null;
	}

}
