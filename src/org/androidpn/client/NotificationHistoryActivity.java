package org.androidpn.client;

import java.util.ArrayList;
import java.util.List;

import org.androidpn.demoapp.R;
import org.jivesoftware.smackx.packet.MUCInitialPresence.History;
import org.litepal.crud.DataSupport;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class NotificationHistoryActivity extends Activity {
	private ListView mListView;
	private NotificationHistoryAdapter mHistoryAdapter;
	private List<NotificationHistory> mlist = new ArrayList<NotificationHistory>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notification_history);
		mListView = (ListView) findViewById(R.id.id_history_view);
		mlist = DataSupport.findAll(NotificationHistory.class);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				NotificationHistory mHistory = mlist.get(arg2);
				Intent intent = new Intent(NotificationHistoryActivity.this,
						NotificationDetailsActivity.class);
				intent.putExtra(Constants.NOTIFICATION_API_KEY, mHistory.getApiKey());
				intent.putExtra(Constants.NOTIFICATION_TITLE, mHistory.getTitle());
				intent.putExtra(Constants.NOTIFICATION_MESSAGE, mHistory.getMessage());
				intent.putExtra(Constants.NOTIFICATION_URI, mHistory.getUri());
				intent.putExtra(Constants.NOTIFICATION_IMAGE_URL, mHistory.getImageUrl());
				startActivity(intent);
			}
		});
	
		mHistoryAdapter = new NotificationHistoryAdapter(this, 0, mlist);
		mListView.setAdapter(mHistoryAdapter);
		registerForContextMenu(mListView);
	}

	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		
		menu.add(0, 0, 0, "Remove");
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId()==0) {
		AdapterContextMenuInfo contextMenuInfo=(AdapterContextMenuInfo) item.getMenuInfo();
			int index=contextMenuInfo.position;
			NotificationHistory history=mlist.get(index);
			history.delete();
			mlist.remove(index);
			mHistoryAdapter.notifyDataSetChanged();
			
		}
		
		
		return super.onContextItemSelected(item);
	}
	class NotificationHistoryAdapter extends ArrayAdapter<NotificationHistory> {

		public NotificationHistoryAdapter(Context context, int resource,
				List<NotificationHistory> objects) {
			super(context, resource, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			NotificationHistory history = getItem(position);
			View view;
			if (convertView == null) {
				view = LayoutInflater.from(getContext()).inflate(
						R.layout.notification_history_item, null);
			} else {
				view = convertView;
			}
			TextView tiele = (TextView) view.findViewById(R.id.id_title);
			TextView time = (TextView) view.findViewById(R.id.id_time);

			tiele.setText(history.getTitle());
			time.setText(history.getTime());

			return view;
		}
	}

}
