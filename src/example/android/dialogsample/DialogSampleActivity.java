package example.android.dialogsample;

import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class DialogSampleActivity extends Activity {

	//メッセージラベル用テキストビュー
	TextView label = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//レイアウト
		setContentView(R.layout.activity_dialog_sample);

		//メッセージラベル用テキストビュー取得
		label = (TextView)findViewById(R.id.tv_message);

		//通常ダイアログ表示ボタンのクリックリスナー設定
		Button dialogBtn = (Button)findViewById(R.id.bt_dialog);
		dialogBtn.setTag("dialog");
		dialogBtn.setOnClickListener(new ButtonClickListener());

		//テキストダイアログ表示ボタンのクリックリスナー設定
		Button txtdialogBtn = (Button)findViewById(R.id.bt_textdialog);
		txtdialogBtn.setTag("textdialog");
		txtdialogBtn.setOnClickListener(new ButtonClickListener());

		//単一選択ダイアログ表示ボタンのクリックリスナー設定
		Button SingleSelectDialogBtn = (Button)findViewById(R.id.bt_selectdialog);
		SingleSelectDialogBtn.setTag("SingleSelectDialog");
		SingleSelectDialogBtn.setOnClickListener(new ButtonClickListener());

		//日付選択ダイアログ表示ボタンのクリックリスナー設定
		Button DatePickerDialogBtn
			= (Button)findViewById(R.id.bt_datedialog);
		DatePickerDialogBtn.setTag("datePickerDialog");
		DatePickerDialogBtn.setOnClickListener(new ButtonClickListener());

		//時間選択ダイアログ表示ボタンのクリックリスナー設定
		Button TimePickerDialogBtn = (Button)findViewById(R.id.bt_timedialog);
		TimePickerDialogBtn.setTag("TimePickerDialog");
		TimePickerDialogBtn.setOnClickListener(new ButtonClickListener());

		//プログレスバーダイアログ表示ボタンのクリックリスナー設定
		Button ProgressDialogBtn = (Button)findViewById(R.id.bt_progressdialog);
		ProgressDialogBtn.setTag("ProgressDialog");
		ProgressDialogBtn.setOnClickListener(new ButtonClickListener());

	}

	public class ButtonClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			String tag =(String)v.getTag();

			if(tag.equals("dialog")){
				showDialog();
			}else if(tag.equals("textdialog")){
				showTextDialog();
			}else if(tag.equals("SingleSelectDialog")){
				showSingleSelectDialog();
			}else if(tag.equals("datePickerDialog")){
				showDatePickerDialog();
			}else if(tag.equals("TimePickerDialog")){
				showTimePickerDialog();
			}else if(tag.equals("ProgressDialog")){
				showProgressDialog();
			}
		}

		//プログレスバーダイアログの表示
		ProgressDialog dialog ;
		private void showProgressDialog() {
			dialog = new ProgressDialog(DialogSampleActivity.this);
		dialog.setTitle("プログレスバーダイアログ");
		dialog.setMessage("しばらくお待ちください");
		dialog.setIndeterminate(false);
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		//dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setMax(100);
		dialog.setCancelable(false);
		dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {

			}
		});
		dialog.show();
		new Thread(new Runnable() {

			@Override
			public void run() {
				try{
					for(int i=0; i< dialog.getMax();i++){
						dialog.setProgress(i);
						Thread.sleep(100);
					}
				}catch (Exception e) {
					// TODO: handle exception
				}
				dialog.dismiss();
			}
		}).start();
		}

		//時間選択ダイアログの表示
		private void showTimePickerDialog() {
			TimePickerDialog dialog
			= new TimePickerDialog(DialogSampleActivity.this,new TimePickerDialog.OnTimeSetListener() {

				@Override
				public void onTimeSet(TimePicker view, int hour, int min) {
					label.setText("時間選択ダイアログ："+
							hour+"時"+min+"分");
				}
			}, 0, 0, true);
		dialog.show();

		}
		//日付選択ダイアログの表示
		public void showDatePickerDialog() {
			Calendar cal = Calendar.getInstance();

			DatePickerDialog dialog = new DatePickerDialog(DialogSampleActivity.this
					,new DatePickerDialog.OnDateSetListener() {
						public void onDateSet(DatePicker picker,
											int year, int month, int day) {
						label.setText("日付選択ダイアログ：" +
										year + "年" + (month+1) + "月" + day + "日");
						}
					}
					,cal.get(Calendar.YEAR)
					,cal.get(Calendar.MONTH)
					,cal.get(Calendar.DAY_OF_MONTH)
			);
			dialog.show();
		}

		//単一選択ダイアログの表示
		final String[] items =new String[]{"もも","うめ","さくら"};
		int which =0;

		private void showSingleSelectDialog() {
			AlertDialog.Builder dialog
				= new AlertDialog.Builder(DialogSampleActivity.this);
			dialog.setTitle("単一選択ダイアログ");
			dialog.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int whichButton) {
					which = whichButton;
				}
			});
			dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int whichButton) {
					String selected = items[which];
					label.setText("単一選択ダイアログ："+
									selected +
									"が入力されました。");

				}
			});
			dialog.show();
		}

		//テキストダイアログの表示
		private void showTextDialog() {
			final EditText editText =new EditText(DialogSampleActivity.this);
			AlertDialog.Builder dialog
				= new AlertDialog.Builder(DialogSampleActivity.this);
			dialog.setTitle("テキストダイアログ");
			dialog.setMessage("テキストを入力してくらはい");
			dialog.setView(editText);
			dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					label.setText("テキストダイアログ："+
									editText.getText().toString()+
									"が入力されました");
				}
			});
			dialog.show();
		}

		//通常ダイアログの表示
		private void showDialog() {
			// TODO 自動生成されたメソッド・スタブ
			AlertDialog.Builder dialog
				= new AlertDialog.Builder(DialogSampleActivity.this);
			dialog.setTitle("通常ダイアログ");
			dialog.setMessage("選択してください。");
			dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					label.setText("通常ダイアログ：OKが選択されました。");
				}
			});

			dialog.setNegativeButton("NG", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					label.setText("通常ダイアログ:NGが選択されました。");
				}
			});
			dialog.show();
		}
	}

	//onCreateOptionMenuメソッド
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_dialog_sample, menu);
		return true;
	}

}
