package com.tlulybluemonochrome.minimarucalc;


import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

public class CalcService extends Service {

	int wig = 0;
	int fig = 0;
	int[] id = new int[4];
	Calc[] calc = { new Calc(), new Calc(), new Calc(), new Calc() };

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);

		/* リモートビューインスタンスを取得 */
		RemoteViews remoteViews = new RemoteViews(getPackageName(),
				R.layout.widget_main);

		int[] appWidgetId = intent.getExtras().getIntArray(
				AppWidgetManager.EXTRA_APPWIDGET_ID);

		fig = appWidgetId[0];

		wig = Serch(appWidgetId[1], wig);

		if (fig >= 0 && fig <= 9) {
			remoteViews.setTextViewText(R.id.textView1, calc[wig].figure(fig));
		} else if (fig >= 10 && fig <= 14) {
			remoteViews.setTextViewText(R.id.textView1,
					calc[wig].calc(fig - 10));
		} else if (fig == 15) {
			remoteViews.setTextViewText(R.id.textView1, calc[wig].dot());
		} else if (fig == 16) {
			remoteViews.setTextViewText(R.id.textView1, calc[wig].AC());
		}

		// AppWidgetの画面更新
		AppWidgetManager manager = AppWidgetManager.getInstance(this);
		manager.updateAppWidget(appWidgetId[1], remoteViews);

		return START_NOT_STICKY;
	}

	public int Serch(int widgetid, int oldid) {
		for (int i = 0; i < id.length; i++) {
			if (id[i] == widgetid) {
				return i;
			}
		}
		oldid++;
		id[oldid] = widgetid;
		return oldid;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		// throw new UnsupportedOperationException("Not yet implemented");
		return null;
	}

}
