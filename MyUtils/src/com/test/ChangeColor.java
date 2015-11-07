package com.test;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import com.myutils.R;

public class ChangeColor extends Activity{
	
	private ImageView imageView1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_color);
		imageView1=(ImageView) findViewById(R.id.imageView1);
		
	}
	
	 public static Bitmap createTransparentBitmapFromBitmap(Bitmap bitmap,
		      int replaceThisColor) {
		    if (bitmap != null) {
		      int picw = bitmap.getWidth();
		      int pich = bitmap.getHeight();
		      int[] pix = new int[picw * pich];
		      bitmap.getPixels(pix, 0, picw, 0, 0, picw, pich);
		      for (int y = 0; y < pich; y++) {
		        // from left to right
		        for (int x = 0; x < picw; x++) {
		          int index = y * picw + x;
		          int r = (pix[index] >> 16) & 0xff;
		          int g = (pix[index] >> 8) & 0xff;
		          int b = pix[index] & 0xff;

		          if (pix[index] == replaceThisColor) {
		            pix[index] = Color.TRANSPARENT;
		          } else {
		            break;
		          }
		        }
		        // from right to left
		        for (int x = picw - 1; x >= 0; x--) {
		          int index = y * picw + x;
		          int r = (pix[index] >> 16) & 0xff;
		          int g = (pix[index] >> 8) & 0xff;
		          int b = pix[index] & 0xff;

		          if (pix[index] == replaceThisColor) {
		            pix[index] = Color.TRANSPARENT;
		          } else {
		            break;
		          }
		        }
		      }

		      Bitmap bm = Bitmap.createBitmap(pix, picw, pich,
		          Bitmap.Config.ARGB_4444);

		      return bm;
		    }
		    return null;
		  }

}
