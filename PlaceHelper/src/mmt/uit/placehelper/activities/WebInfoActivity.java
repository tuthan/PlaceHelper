package mmt.uit.placehelper.activities;

import mmt.uit.placehelper.R;
import mmt.uit.placehelper.R.layout;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class WebInfoActivity extends Activity {
	private WebView mWebView;
	//private ProgressDialog progressDgl;
	Handler mHandler ; 
	String url;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(layout.web_infomation);	
		Bundle b = getIntent().getExtras();
		url = b.getString("url");
		mWebView = (WebView)findViewById(R.id.webview);
		mHandler = new MyHandler();
		mWebView.setWebViewClient(new WebInfoClient());
		setProgressBarIndeterminateVisibility(true);		
		Thread onWeb = new Thread(ra);
		onWeb.start();		
		
	}
	
	Runnable ra = new Runnable() {
		
		@Override
		public void run() {			
			// TODO Auto-generated method stub					
			mWebView.getSettings().setJavaScriptEnabled(true);			
			mWebView.getSettings().setDomStorageEnabled(true);			
			mWebView.loadUrl(url);
			try
			{
				Thread.sleep(5000);
			}
			
			catch (InterruptedException ie){
				ie.printStackTrace();
			}
			
			mHandler.sendEmptyMessage(0);
			
		}
	};
	
	private class WebInfoClient extends WebViewClient {
		@Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        view.loadUrl(url);
	        return true;
	    }
	}
		
	private class MyHandler extends Handler {
		
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub			
			setProgressBarIndeterminateVisibility(false);
		}
	}
}


