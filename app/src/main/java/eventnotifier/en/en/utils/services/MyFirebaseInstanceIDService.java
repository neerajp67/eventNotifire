package eventnotifier.en.en.utils.services;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import eventnotifier.en.en.utils.main.interactors.ProfileInteractor;
import eventnotifier.en.en.utils.utils.LogUtil;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    /**
     * Called if InstanceID token is updated.
     */
    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        LogUtil.logDebug(TAG, "Refreshed token: " + refreshedToken);

        sendRegistrationToServer(refreshedToken);
    }
    private void sendRegistrationToServer(String token) {
        ProfileInteractor.getInstance(getApplicationContext()).updateRegistrationToken(token);
    }
}
