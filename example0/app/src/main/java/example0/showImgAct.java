package moji.physics.example0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Display;
import android.widget.ImageView;

public class showImgAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_img);

        Intent in = getIntent();
        int index = in.getIntExtra("org.moji.physics.example0.ITEM_INDEX",-1);
        if (index > -1){
            int pic = getImg(index);
            ImageView img = (ImageView) findViewById(R.id.imageView);
            scaleImg(img,pic);
        }
    }

    private int getImg(int index){
        switch (index){
            case 0: return R.drawable.atmarket;
            case 1: return R.drawable.santas_helper;
            case 2: return R.drawable.dinos;
            case 3: return R.drawable.necktie_full_windsor;
            case 4: return R.drawable.cute_girl;
            default: return -1;
        }
    }

    private void scaleImg(ImageView img, int pic){
        Display myScreen = getWindowManager().getDefaultDisplay();
        BitmapFactory.Options imgOptions = new BitmapFactory.Options();
        imgOptions.inJustDecodeBounds=true;
        BitmapFactory.decodeResource(getResources(),pic,imgOptions);

        int imgWidth = imgOptions.outWidth;
        int screenWidth = myScreen.getWidth();
        if (imgWidth > screenWidth){
            int ratio = Math.round((float)imgWidth/(float) screenWidth);
            imgOptions.inSampleSize = ratio;
        }
        imgOptions.inJustDecodeBounds=false;
        Bitmap scaledImg = BitmapFactory.decodeResource(getResources(),pic,imgOptions);
        img.setImageBitmap(scaledImg);
    }
}