package net.stone;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class SplashActivity extends Activity{
	private UIHandler UIhandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		LinearLayout layout=new LinearLayout(SplashActivity.this);
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

	
		layout.addView(image);
		setContentView(layout);
		
		UIhandler = new UIHandler();  
        UIThread thread = new UIThread();  
        thread.start(); 
		
	}
	
	private class UIHandler extends Handler{  
        @Override  
        public void handleMessage(Message msg) {  
            super.handleMessage(msg);  
            //Bundle bundle = msg.getData();  
            //String color = bundle.getString("color");  
            //UITxt.setText(color);  
            startActivity(new Intent(SplashActivity.this, net.mamacode.fileexplorer.FileExplorerTabActivity.class));
        }  
    }  
	
	private class UIThread extends Thread{  
        @Override  
        public void run() {  
            try {  
                Thread.sleep(3000);  
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
