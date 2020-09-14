package eventnotifier.en.en.utils.main.post;

import android.net.Uri;

import eventnotifier.en.en.utils.main.pickImageBase.PickImageView;

public interface BaseCreatePostView extends PickImageView {
    void setDescriptionError(String error);

    void setTitleError(String error);

    String getTitleText();

    String getDescriptionText();

    void requestImageViewFocus();

    void onPostSavedSuccess();

    Uri getImageUri();
}

