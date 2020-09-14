package eventnotifier.en.en.utils.main.pickImageBase;

import android.net.Uri;

import eventnotifier.en.en.utils.main.base.BaseView;

public interface PickImageView extends BaseView {
    void hideLocalProgress();

    void loadImageToImageView(Uri imageUri);
}
