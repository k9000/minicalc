package com.tlulybluemonochrome.minimarucalc;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class CalcWidget extends AppWidgetProvider {

	private static final String BUTTON_CLICK_ACTION = "BUTTON_CLICK_ACTION";

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);

		for (int appWidgetId : appWidgetIds) {

			// ボタンのidを配列に
			int[] fig = { R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3,
					R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8,
					R.id.btn9, R.id.btnEqual, R.id.btnAdd, R.id.btnSub,
					R.id.btnMul, R.id.btnDiv, R.id.btnDot, R.id.btnCLR };

			// リモートビューインスタンスを取得
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
					R.layout.widget_main);

			// ボタンが押された時に発行されるインテントを準備する
			Intent[] buttonIntentF = new Intent[fig.length];

			PendingIntent[] pendingIntentF = new PendingIntent[fig.length];

			for (int i = 0; i < fig.length; i++) {
				int[] id = { i, appWidgetId };
				buttonIntentF[i] = new Intent(BUTTON_CLICK_ACTION);
				buttonIntentF[i].putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
						id);
				pendingIntentF[i] = PendingIntent.getService(context, i,
						buttonIntentF[i], appWidgetId);
				remoteViews.setOnClickPendingIntent(fig[i], pendingIntentF[i]);
			}

			// ウィジェット更新
			appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

		}

	}
}
