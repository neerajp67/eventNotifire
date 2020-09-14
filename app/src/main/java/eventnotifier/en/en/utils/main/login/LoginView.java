package eventnotifier.en.en.utils.main.login;

import com.google.firebase.auth.AuthCredential;
import eventnotifier.en.en.utils.main.base.BaseView;

public interface LoginView extends BaseView {
    void startCreateProfileActivity();

    void signInWithGoogle();

    void signInWithFacebook();

    void setProfilePhotoUrl(String url);

    void firebaseAuthWithCredentials(AuthCredential credential);
}
