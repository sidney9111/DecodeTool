package net.stone;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;








import android.annotation.SuppressLint;
//import ofs.ahd.dii.AdManager;
//import ofs.ahd.dii.br.AdSize;
//import ofs.ahd.dii.br.AdView;
//import ofs.ahd.dii.st.SpotManager;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;


@SuppressLint("HandlerLeak")
public class SplashActivity extends Activity{
	private UIHandler UIhandler;
	//private ActivityInfo mainActivityInfo; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//LinearLayout layout=new LinearLayout(SplashActivity.this);
		RelativeLayout layout = new RelativeLayout(SplashActivity.this);
		LayoutParams par = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		layout.setLayoutParams(par);
		
		ImageView image=new ImageView(SplashActivity.this);
		android.view.ViewGroup.LayoutParams parImage = new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		image.setLayoutParams(parImage);
		String[] images = null;
		AssetManager assets = getAssets();
//		try{  
//            //获取/assests/目录下的所有的文件   
//            images = assets.list("");  
//              
//        }catch(IOException e){  
//            e.printStackTrace();  
//        }  
//		int currentImage = 0;
//		while(!images[currentImage].endsWith(".jpg")){  
//            currentImage++;  
//           //如果发生数组越界   
//           if(currentImage >= images.length){  
//               currentImage = 0;  
//           }  
//		}
//           
		//InputStream assetFile = SplashActivity.this.getClass().getResourceAsStream("logo.png");
		InputStream assetFile = null;
		try {
			assetFile = assets.open("logo.jpg");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BitmapDrawable bitmapDrawable = (BitmapDrawable)image.getDrawable();  
        //如果图片还未回收，先强制回收该图片   
        if(bitmapDrawable !=null && !bitmapDrawable.getBitmap().isRecycled()){  
            bitmapDrawable.getBitmap().recycle();  
        }  
        //该变现实的图片   
        image.setImageBitmap(BitmapFactory.decodeStream(assetFile));  
        
      
//        <LinearLayout
//        android:id="@+id/adLayout"
//        android:layout_width="fill_parent"
//        android:layout_height="wrap_content"
//        android:gravity="center_horizontal">
//    </LinearLayout>
	
	
		layout.addView(image);
		setContentView(layout);
		
		UIhandler = new UIHandler();  
        UIThread thread = new UIThread();  
        thread.start(); 
		
        
        
        //AdManager.getInstance(SplashActivity.this).init("329b836cc0953f22", "5d4702e83ddf8727", true);
        //SpotManager.getInstance(SplashActivity.this).loadSpotAds();
        LinearLayout adLayout = new LinearLayout(SplashActivity.this);
        adLayout.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        adLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        layout.addView(adLayout);
//    	// 实例化广告条
//		AdView adView = new AdView(this, AdSize.FIT_SCREEN);
//		//获取要嵌入广告条的布局
//		//LinearLayout adLayout=(LinearLayout)findViewById(R.id.adLayout);
//		// 将广告条加入到布局中
//		adLayout.addView(adView);
        
        
	}
	
	private class UIHandler extends Handler{  
        @Override  
        public void handleMessage(Message msg) {  
            super.handleMessage(msg);
            //==直接调用测试用例==
            //Bundle bundle = msg.getData();  
            //String color = bundle.getString("color");  
            //UITxt.setText(color);  
            //startActivity(new Intent(SplashActivity.this, net.mamacode.fileexplorer.FileExplorerTabActivity.class));
            //startActivity(new Intent(SplashActivity.this,com.example.listviewsample.MainActivity.class));
            
            //startActivity(new Intent(SplashActivity.this,com.sohomob.android.aeroplane_chess_battle_ludo_2.StartMenuActivity.class));
            //==action方法调用==
////            Uri uri = getIntent().getData();
////            String str= uri.getHost();
////             str变量得到的值就是“哈哈哈”
//            Uri  uri  = Uri.parse("myscheme://哈哈哈");
//            Intent intent = new Intent("net.stone.mainaction",uri);
//            startActivity(intent);
//            //startActivity(new Intent(Intent.ACTION_CALL,Uri.parse("com.myaction")));
            //==找xml，后调用==
            //本工作室下面多个activity可能声明同一个intent-fiter:"mainaction"
            //所以为了避免重复的情况
            //这里绕了一圈，获取当前application下的activity才启动
            ActivityInfo mainActivityInfo = SplashActivity.this.loadIntent_filter();
            if(mainActivityInfo!=null){
            	Intent intent;
				try {
					intent = new Intent(SplashActivity.this, Class.forName(mainActivityInfo.name));
					startActivity(intent);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	
            }
//            
            finish();
        }  
    }  
	/**
	 * 获取当前application下的activity
	 * (通过PackageManager)
	 * @return
	 */
	private ActivityInfo loadIntent_filter(){
		PackageManager manager = SplashActivity.this.getPackageManager(); //获得PackageManager对象
		Intent intent = new Intent("net.stone.mainaction",null);
//	    intent.setAction("net.stone.mainaction");
	    intent.addCategory(Intent.CATEGORY_DEFAULT);

//		intent.addCategory(Intent.CATEGORY_LAUNCHER);
//	    // NOTE: Provide some data to help the Intent resolver
//	    intent.setData(Uri.parse("http://www.google.com"));
	    //intent.setData(Uri.parse("myscheme://哈哈哈"));
	    // Query for all activities that match my filter and request that the filter used
	    //  to match is returned in the ResolveInfo
	    List<ResolveInfo> infos = manager.queryIntentActivities (intent,
	                                   //PackageManager.GET_RESOLVED_FILTER
	    								PackageManager.MATCH_DEFAULT_ONLY);
	    for (ResolveInfo info : infos) {
	    	
	        ActivityInfo activityInfo = info.activityInfo;
	   
	        IntentFilter filter = info.filter;
	      //不确定，但是应该是没初始化的时候filter会等于null，所以不可用
//	        if (filter != null && filter.hasAction("net.stone.mainaction") &&
//	                  filter.hasCategory(Intent.CATEGORY_DEFAULT)) {
	            // This activity resolves my Intent with the filter I'm looking for
	            String activityPackageName = activityInfo.packageName;
	            
	            String activityName = activityInfo.name;
	            if(activityPackageName.equals(SplashActivity.this.getPackageName())){
	            	return activityInfo;
	            }
	            //System.out.println("Activity "+activityPackageName + "/" + activityName);
	        //}
	    }
	    
	    return null;
	}
	private class UIThread extends Thread{  
        @Override  
        public void run() {  
            try {  
                Thread.sleep(3500);  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
            Message msg = new Message();  
            Bundle bundle = new Bundle();  
            //bundle.putString("color", "黄色");  
            msg.setData(bundle);  
            SplashActivity.this.UIhandler.sendMessage(msg);  
              
        }  
    }  
}
