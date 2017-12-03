package app.my.com.diyizhou;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.style.BackgroundColorSpan;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import app.my.com.diyizhou.fragment.Bazaar;
import app.my.com.diyizhou.fragment.HomePage;
import app.my.com.diyizhou.fragment.Idea;
import app.my.com.diyizhou.fragment.Inform;
import app.my.com.diyizhou.fragment.More;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.homePage)
    RadioButton homePage;
    @BindView(R.id.idea)
    RadioButton idea;
    @BindView(R.id.bazaar)
    RadioButton bazaar;
    @BindView(R.id.inform)
    RadioButton inform;
    @BindView(R.id.more)
    RadioButton more;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        HomePage hp = new HomePage();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, hp);
        fragmentTransaction.commit();
    }

    @OnClick(R.id.homePage)
    public void onHomePageClicked() {
        HomePage hp = new HomePage();
        homePage.setBackgroundColor(Color.YELLOW);
        idea.setBackgroundColor(Color.WHITE);
        bazaar.setBackgroundColor(Color.WHITE);
        more.setBackgroundColor(Color.WHITE);
        inform.setBackgroundColor(Color.WHITE);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, hp);
        fragmentTransaction.commit();
    }

    @OnClick(R.id.idea)
    public void onIdeaClicked() {
        Idea hp = new Idea();
        homePage.setBackgroundColor(Color.WHITE);
        idea.setBackgroundColor(Color.YELLOW);
        bazaar.setBackgroundColor(Color.WHITE);
        more.setBackgroundColor(Color.WHITE);
        inform.setBackgroundColor(Color.WHITE);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, hp);
        fragmentTransaction.commit();
    }

    @OnClick(R.id.bazaar)
    public void onBazaarClicked() {
        Bazaar hp = new Bazaar();
        homePage.setBackgroundColor(Color.WHITE);
        idea.setBackgroundColor(Color.WHITE);
        bazaar.setBackgroundColor(Color.YELLOW);
        more.setBackgroundColor(Color.WHITE);
        inform.setBackgroundColor(Color.WHITE);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, hp);
        fragmentTransaction.commit();
    }

    @OnClick(R.id.inform)
    public void onInformClicked() {
        Inform hp = new Inform();
        homePage.setBackgroundColor(Color.WHITE);
        idea.setBackgroundColor(Color.WHITE);
        bazaar.setBackgroundColor(Color.WHITE);
        more.setBackgroundColor(Color.WHITE);
        inform.setBackgroundColor(Color.YELLOW);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, hp);
        fragmentTransaction.commit();
    }

    @OnClick(R.id.more)
    public void onMoreClicked() {
        More hp = new More();
        homePage.setBackgroundColor(Color.WHITE);
        idea.setBackgroundColor(Color.WHITE);
        bazaar.setBackgroundColor(Color.WHITE);
        more.setBackgroundColor(Color.YELLOW);
        inform.setBackgroundColor(Color.WHITE);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, hp);
        fragmentTransaction.commit();
    }
}
