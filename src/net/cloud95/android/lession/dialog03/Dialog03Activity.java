package net.cloud95.android.lession.dialog03;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class Dialog03Activity extends Activity {

    private Button button01, button02, button03;
    
    private ProgressDialog progressDialog;
    private Handler progressHandler; //另一個執行序
    
    private boolean progressInTitle = false;
    private static final int MAX = 100;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 需要在狀態列顯示處理中圖示(畫圈圈的進度)，
        // 一定要在指定Activity元件畫面配置資源之前，
        // 使用這行敘述執行設定
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);//取得視窗特徵
        
        setContentView(R.layout.activity_dialog03);
    
        processViews();
        
        // 建立一個測試進度對話框的Handler物件     
       progressHandler = new Handler(){ //是一種執行序
    	   
    	   public void handleMessage(Message msg){
    		   super.handleMessage(msg);
    		   
    		   if(progressDialog.getProgress()>=MAX){
    			   //已經處理完成,關閉對話框
    			   progressDialog.dismiss();
    			   button02.setText("DONE");
    		   }else{
    			   //增加進度對話框目前的進度
    			   progressDialog.incrementProgressBy(2);
    			   //0.1秒後再執行一次handler
    			   progressHandler.sendEmptyMessageDelayed(0, 100);//編號是0的handler,每隔0.1秒
    		   }
    	   }
       };
    }

    private void processViews() {
        button01 = (Button) findViewById(R.id.button01);
        button02 = (Button) findViewById(R.id.button02);
        button03 = (Button) findViewById(R.id.button03);
    }

    public void clickButton01(View view) {
        // 建立進度對話框物件
    	ProgressDialog d = new ProgressDialog(Dialog03Activity.this);
        // 設定進度對話框的標題、訊息與可以取消
    	d.setTitle("Download");
    	d.setMessage("Please wait for download.....");
    	d.setCancelable(true);
        // 加入取消按鈕
        // 第一個參數是按鈕的種類
        // 第二個參數是按鈕的文字
        // 第一個參數是處理按鈕事件的監聽物件
    	d.setButton(DialogInterface.BUTTON_NEGATIVE,"Cancel",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				// TODO Auto-generated method stub
				button01.setText("Cancel");
			}
		});
        // 顯示進度對話框
    	d.show();
    }

    public void clickButton02(View view) {
        // 建立進度對話框物件
    	progressDialog = new ProgressDialog(Dialog03Activity.this);
        // 設定進度對話框的標題、訊息與進度樣式
    	progressDialog.setTitle("Download");
    	progressDialog.setMessage("Please wait for download.....");
    	progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // 設定進度對話框的最大值為100
    	progressDialog.setMax(100);
        // 設定進度對話框目前的進度
    	progressDialog.setProgress(0);
        // 第一次執行Handler
    	progressHandler.sendEmptyMessage(0);
        // 加入取消按鈕
    	progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"Cancle",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				button02.setText("Cancel");
			}
		});
        // 顯示進度對話框
    	progressDialog.show();
    }

    public void clickButton03(View view) {
        // 處理中圖示是否顯示中
    	progressInTitle = !progressInTitle;
        // 顯示或隱藏處理中圖示
    	setProgressBarIndeterminateVisibility(progressInTitle);
    	button03.setText("Progress in title"+(progressInTitle?"(ON)":"(OFF)"));
    }

}
