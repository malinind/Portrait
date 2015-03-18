package com.android.malinind.portrait;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class MainActivity extends Activity {

    private TextView mPartOfTheFace;
    private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private int helloLiza,whereEye,whereForehead,whereMouth, whereNose, eyes,forehead,mouth,nose,welldone,wrong,meow,boom;
    private static int curPromt=0;
    private static boolean endGame=false;
    private ImageView mCat;
    private Animation mPopup;

    private FrameLayout mNose,mForehead,mMouth,mEyeL,mEyeR;

    private int[] BALLOONS = {
            R.drawable.ballon_green,
            R.drawable.ballon_purple,
            R.drawable.ballon_red,
            R.drawable.ballon_yellow,
            R.drawable.balloon_blue,
            R.drawable.balloon_dark_green,
            R.drawable.balloon_violet,
    };

    private Rect mDisplaySize = new Rect();

    private RelativeLayout mRootLayout;
    private ArrayList<View> mAllImageViews = new ArrayList<View>();

    private float mScale;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.portrait);

        mPartOfTheFace = (TextView)findViewById(R.id.nameOfPart);

        mNose = (FrameLayout)findViewById(R.id.nose);
        mEyeL = (FrameLayout)findViewById(R.id.eyeLeft);
        mEyeR = (FrameLayout)findViewById(R.id.eyeRight);
        mMouth = (FrameLayout)findViewById(R.id.mouth);
        mForehead = (FrameLayout)findViewById(R.id.foreHead);


        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        mAssetManager = getAssets();

        // получим идентификаторы
        helloLiza = loadSound("helloLiza.ogg");
        whereEye = loadSound("whereEye.ogg");
        whereForehead = loadSound("whereForehead.ogg");
        whereMouth = loadSound("whereMouth.ogg");
        whereNose = loadSound("whereNose.ogg");
        eyes = loadSound("eyes.ogg");
        forehead = loadSound("forehead.ogg");
        mouth = loadSound("mouth.ogg");
        nose = loadSound("nose.ogg");
        welldone = loadSound("welldone.ogg");
        wrong = loadSound("wrong.ogg");
        meow = loadSound("cat.ogg");
        boom = loadSound("boom.ogg");


        Display display = getWindowManager().getDefaultDisplay();
        display.getRectSize(mDisplaySize);

        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        mScale = metrics.density;

        mRootLayout = (RelativeLayout) findViewById(R.id.main_layout);

        mCat = (ImageView)findViewById(R.id.cat);
        mPopup = AnimationUtils.loadAnimation(this,R.anim.cat_popup);
        mCat.setAnimation(mPopup);


        playSound(helloLiza);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onRightEyeClick(View view) {
        if(curPromt==whereEye) {
            mPartOfTheFace.setText("Правильно. Это ПРАВЫЙ ГЛАЗ.");
            playSound(eyes);

            try {

                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            game(whereNose);
        }
        else {
            playSound(wrong);

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            playSound(curPromt);
        }
    }

    public void onLeftEyeClick(View view) {
        if(curPromt==whereEye) {
            mPartOfTheFace.setText("Правильно. Это ЛЕВЫЙ ГЛАЗ.");
            playSound(eyes);

            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            game(whereNose);
        }
        else {
            playSound(wrong);

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            playSound(curPromt);
        }
    }

    public void onNoseClick(View view) {

        if(curPromt==whereNose) {
            mPartOfTheFace.setText("Правильно. Это НОС.");
            playSound(nose);

            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            game(whereForehead);

        }
        else
        {
            playSound(wrong);

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            playSound(curPromt);
        }
    }

    public void onForeHeadClick(View view) {
        if(curPromt==whereForehead) {
            mPartOfTheFace.setText("Правильно. Это ЛОБ.");
            playSound(forehead);

            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            game(whereMouth);
        }
        else
        {
            playSound(wrong);

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            playSound(curPromt);
        }
    }

    public void onMouthClick(View view) {
        if(curPromt==whereMouth) {
            mPartOfTheFace.setText("Правильно. Это РОТ.");
            playSound(mouth);

            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            endGame=true;
            game(mouth);
        }
        else
        {
            playSound(wrong);

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            playSound(curPromt);
        }
    }

    public void onClick(View view) {
        mPartOfTheFace.setText("Проверка");

    }

    private void playSound(int sound) {
        if (sound > 0)
            mSoundPool.play(sound, 1, 1, 1, 0, 1);
    }

    private int loadSound(String fileName) {
        AssetFileDescriptor afd = null;
        try {
            afd = mAssetManager.openFd(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Не могу загрузить файл " + fileName,
                    Toast.LENGTH_SHORT).show();
            return -1;
        }
        return mSoundPool.load(afd, 1);
    }

    public void game(int promtId)
    {
        mPartOfTheFace.setText("");
        if (endGame)
        {   mPartOfTheFace.setText("Молодец! Ты все угадала!");
            playSound(welldone);
            endGame=false;

            mNose.setVisibility(View.GONE);
            mEyeL.setVisibility(View.GONE);
            mEyeR.setVisibility(View.GONE);
            mMouth.setVisibility(View.GONE);
            mForehead.setVisibility(View.GONE);

            new Timer().schedule(new ExeTimerTask(), 0, 350);
           // new Timer().schedule(new CatPopupTimerTask(), 5000);
        }
        else {
            playSound(promtId);
            curPromt=promtId;
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void clickStart(View view) {

        view.startAnimation(AnimationUtils.loadAnimation(this,R.anim.cat_hide));
        playSound(meow);

        mNose.setVisibility(View.VISIBLE);
        mEyeL.setVisibility(View.VISIBLE);
        mEyeR.setVisibility(View.VISIBLE);
        mMouth.setVisibility(View.VISIBLE);
        mForehead.setVisibility(View.VISIBLE);

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mCat.setVisibility(View.GONE);
        mCat.setClickable(false);
        game(whereEye);

    }



    public void startAnimation(final ImageView aniView) {

        aniView.setPivotX(aniView.getWidth()/2);
        aniView.setPivotY(aniView.getHeight()/2);

        long delay = new Random().nextInt(Constants.MAX_DELAY);

        final ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(Constants.ANIM_DURATION);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setStartDelay(delay);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            int angle = 50 + (int)(Math.random() * 101);
            int movex = new Random().nextInt(mDisplaySize.right);

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = ((Float) (animation.getAnimatedValue())).floatValue();

                aniView.setRotation(angle*value);
                aniView.setTranslationX((movex-40)*value);
                aniView.setTranslationY((mDisplaySize.bottom + (150*mScale))*value);
            }
        });

        animator.start();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int viewId = new Random().nextInt(BALLOONS.length);
            Drawable d = getResources().getDrawable(BALLOONS[viewId]);
            LayoutInflater inflate = LayoutInflater.from(MainActivity.this);
            ImageView imageView = (ImageView) inflate.inflate(R.layout.ani_image_view, null);
            imageView.setImageDrawable(d);
            mRootLayout.addView(imageView);

            mAllImageViews.add(imageView);

            RelativeLayout.LayoutParams animationLayout = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
            animationLayout.setMargins(new Random().nextInt((int)(500*mScale)), (int)(-150*mScale), new Random().nextInt((int)(500*mScale)), 0);
            animationLayout.width = (int) (150*mScale);
            animationLayout.height = (int) (150*mScale);

            startAnimation(imageView);
        }
    };

    private class ExeTimerTask extends TimerTask {
        @Override
        public void run() {

            mHandler.sendEmptyMessage(Constants.EMPTY_MESSAGE_WHAT);
        }
    }

    public void onClickBalloon(View view) {
        view.setVisibility(View.GONE);
        playSound(boom);
    }

}
