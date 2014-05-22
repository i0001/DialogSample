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

	//���b�Z�[�W���x���p�e�L�X�g�r���[
	TextView label = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//���C�A�E�g
		setContentView(R.layout.activity_dialog_sample);

		//���b�Z�[�W���x���p�e�L�X�g�r���[�擾
		label = (TextView)findViewById(R.id.tv_message);

		//�ʏ�_�C�A���O�\���{�^���̃N���b�N���X�i�[�ݒ�
		Button dialogBtn = (Button)findViewById(R.id.bt_dialog);
		dialogBtn.setTag("dialog");
		dialogBtn.setOnClickListener(new ButtonClickListener());

		//�e�L�X�g�_�C�A���O�\���{�^���̃N���b�N���X�i�[�ݒ�
		Button txtdialogBtn = (Button)findViewById(R.id.bt_textdialog);
		txtdialogBtn.setTag("textdialog");
		txtdialogBtn.setOnClickListener(new ButtonClickListener());

		//�P��I���_�C�A���O�\���{�^���̃N���b�N���X�i�[�ݒ�
		Button SingleSelectDialogBtn = (Button)findViewById(R.id.bt_selectdialog);
		SingleSelectDialogBtn.setTag("SingleSelectDialog");
		SingleSelectDialogBtn.setOnClickListener(new ButtonClickListener());

		//���t�I���_�C�A���O�\���{�^���̃N���b�N���X�i�[�ݒ�
		Button DatePickerDialogBtn
			= (Button)findViewById(R.id.bt_datedialog);
		DatePickerDialogBtn.setTag("datePickerDialog");
		DatePickerDialogBtn.setOnClickListener(new ButtonClickListener());

		//���ԑI���_�C�A���O�\���{�^���̃N���b�N���X�i�[�ݒ�
		Button TimePickerDialogBtn = (Button)findViewById(R.id.bt_timedialog);
		TimePickerDialogBtn.setTag("TimePickerDialog");
		TimePickerDialogBtn.setOnClickListener(new ButtonClickListener());

		//�v���O���X�o�[�_�C�A���O�\���{�^���̃N���b�N���X�i�[�ݒ�
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

		//�v���O���X�o�[�_�C�A���O�̕\��
		ProgressDialog dialog ;
		private void showProgressDialog() {
			dialog = new ProgressDialog(DialogSampleActivity.this);
		dialog.setTitle("�v���O���X�o�[�_�C�A���O");
		dialog.setMessage("���΂炭���҂���������");
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

		//���ԑI���_�C�A���O�̕\��
		private void showTimePickerDialog() {
			TimePickerDialog dialog
			= new TimePickerDialog(DialogSampleActivity.this,new TimePickerDialog.OnTimeSetListener() {

				@Override
				public void onTimeSet(TimePicker view, int hour, int min) {
					label.setText("���ԑI���_�C�A���O�F"+
							hour+"��"+min+"��");
				}
			}, 0, 0, true);
		dialog.show();

		}
		//���t�I���_�C�A���O�̕\��
		public void showDatePickerDialog() {
			Calendar cal = Calendar.getInstance();

			DatePickerDialog dialog = new DatePickerDialog(DialogSampleActivity.this
					,new DatePickerDialog.OnDateSetListener() {
						public void onDateSet(DatePicker picker,
											int year, int month, int day) {
						label.setText("���t�I���_�C�A���O�F" +
										year + "�N" + (month+1) + "��" + day + "��");
						}
					}
					,cal.get(Calendar.YEAR)
					,cal.get(Calendar.MONTH)
					,cal.get(Calendar.DAY_OF_MONTH)
			);
			dialog.show();
		}

		//�P��I���_�C�A���O�̕\��
		final String[] items =new String[]{"����","����","������"};
		int which =0;

		private void showSingleSelectDialog() {
			AlertDialog.Builder dialog
				= new AlertDialog.Builder(DialogSampleActivity.this);
			dialog.setTitle("�P��I���_�C�A���O");
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
					label.setText("�P��I���_�C�A���O�F"+
									selected +
									"�����͂���܂����B");

				}
			});
			dialog.show();
		}

		//�e�L�X�g�_�C�A���O�̕\��
		private void showTextDialog() {
			final EditText editText =new EditText(DialogSampleActivity.this);
			AlertDialog.Builder dialog
				= new AlertDialog.Builder(DialogSampleActivity.this);
			dialog.setTitle("�e�L�X�g�_�C�A���O");
			dialog.setMessage("�e�L�X�g����͂��Ă���͂�");
			dialog.setView(editText);
			dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					label.setText("�e�L�X�g�_�C�A���O�F"+
									editText.getText().toString()+
									"�����͂���܂���");
				}
			});
			dialog.show();
		}

		//�ʏ�_�C�A���O�̕\��
		private void showDialog() {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			AlertDialog.Builder dialog
				= new AlertDialog.Builder(DialogSampleActivity.this);
			dialog.setTitle("�ʏ�_�C�A���O");
			dialog.setMessage("�I�����Ă��������B");
			dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					label.setText("�ʏ�_�C�A���O�FOK���I������܂����B");
				}
			});

			dialog.setNegativeButton("NG", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					label.setText("�ʏ�_�C�A���O:NG���I������܂����B");
				}
			});
			dialog.show();
		}
	}

	//onCreateOptionMenu���\�b�h
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_dialog_sample, menu);
		return true;
	}

}
