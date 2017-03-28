package cz.hlubyluk.hellolibs;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.google.android.flexbox.FlexboxLayout;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "___MainActivity";
    private Button b1;
    private Button b2;
    private FlexboxLayout co;
    private ProgressBar pr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.b1 = (Button) this.findViewById(R.id.b1);
        this.b2 = (Button) this.findViewById(R.id.b2);
        this.co = (FlexboxLayout) this.findViewById(R.id.co);
        this.pr = (ProgressBar) this.findViewById(R.id.pr);

        new CountDownTimer(TimeUnit.SECONDS.toMillis(5), TimeUnit.SECONDS.toMillis(1)) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "onTick() called with: millisUntilFinished = [" + millisUntilFinished + "]");
            }

            @Override
            public void onFinish() {
                MainActivity.this.countDown();
            }
        }.start();
    }

    private void countDown() {
        this.pr.setVisibility(View.GONE);
        this.b1.setOnClickListener(this::click);
        this.b2.setOnClickListener(this::click);
    }

    private void click(View view) {
        switch (view.getId()) {
            case R.id.b1:
                if (BuildConfig.DEBUG) Log.d(TAG, "click: B1!!!");
                this.co.addView(new ViewRandom(this));
                break;
            case R.id.b2:
                if (BuildConfig.DEBUG) Log.d(TAG, "click: B2!!!");
                this.co.removeViewAt(this.co.getFlexItemCount() - 1);
                break;
            default:
                throw new IllegalArgumentException("Unknown view click");
        }
    }
    
    private static class ViewRandom extends FrameLayout {
        public ViewRandom(Context context) {
            this(context, null);
        }

        public ViewRandom(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public ViewRandom(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            View.inflate(context, R.layout.layout_random_view, this);
            Random random = new Random();
            int rgb = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
            this.setBackgroundColor(rgb);
            if (BuildConfig.DEBUG)
                Log.d(TAG, "ViewRandom: rgb->" + rgb);
        }
    }
}
