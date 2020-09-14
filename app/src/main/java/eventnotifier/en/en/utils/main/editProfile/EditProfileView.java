package eventnotifier.en.en.utils.main.editProfile;

import eventnotifier.en.en.utils.main.pickImageBase.PickImageView;

public interface EditProfileView extends PickImageView {
    void setName(String username);

    void setProfilePhoto(String photoUrl);

    String getNameText();

    void setNameError(String string);
}
