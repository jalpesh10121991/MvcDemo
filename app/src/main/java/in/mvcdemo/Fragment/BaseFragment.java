package in.mvcdemo.Fragment;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import in.mvcdemo.Utills.MessageUtil;
import in.mvcdemo.Utills.VApp;
import in.mvcdemo.Utills.VPreferences;

@EFragment
public class BaseFragment extends Fragment {

    public VPreferences preferences;
    public MessageUtil messageUtil;
    public VApp application;
    public String email;
    public ProgressDialog mDialog;

    @AfterViews
    public void init() {
      //  email = getArguments().getString("email");
    }


    protected VPreferences getPreferences() {
        if (preferences == null)
            preferences = new VPreferences(getActivity());
        return preferences;
    }

    protected MessageUtil getMessageUtil() {
        if (messageUtil == null)
            messageUtil = new MessageUtil(getActivity());
        return messageUtil;
    }

    protected VApp getApplication() {
        if (application == null)
            application = ((VApp) getActivity().getApplication());
        return application;
    }
}

