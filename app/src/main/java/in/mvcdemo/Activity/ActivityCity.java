package in.mvcdemo.Activity;

import android.content.Intent;
import android.util.Log;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import in.mvcdemo.Custom.CustomTextView;
import in.mvcdemo.Custom.SearchableSpinner;
import in.mvcdemo.R;


@EActivity(R.layout.activity_city)
public class ActivityCity extends BaseActivity {

    @ViewById(R.id.spCity)
    SearchableSpinner spCity;
    @ViewById(R.id.imgNext)
    CustomTextView imgNext;
    @ViewById(R.id.txtNeedHelp)
    CustomTextView txtNeedHelp;
    @ViewById(R.id.txtPrivacyPolicy)
    CustomTextView txtPrivacyPolicy;

    @AfterViews
    public void init(){
    }

    @Click
    public void imgNext(){
        if(spCity.getSelectedItem()!=null){
            Log.d("LOGDATA",spCity.getSelectedItem().toString());
            Intent intent = new Intent(ActivityCity.this,ActivityEvent_.class);
            intent.putExtra("CITY", spCity.getSelectedItem().toString());
            startActivity(intent);
        }else{
            messageUtil.showToast("Please Select City");
        }

    }

    @Click
    public void txtPrivacyPolicy(){
        Intent intent = new Intent(ActivityCity.this,PrivacyPolicy.class);
        startActivity(intent);


    }
//    File f=new File("/sdcard/html.html");
//        Uri uri = Uri.fromFile("teammate.html");
//        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
//        browserIntent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
//        browserIntent.setData(uri);
//        startActivity(browserIntent);
//    }

    @Click
    public void txtNeedHelp(){
        Intent intent = new Intent(ActivityCity.this,InstructionActivity_.class);
        startActivity(intent);
    }


}
