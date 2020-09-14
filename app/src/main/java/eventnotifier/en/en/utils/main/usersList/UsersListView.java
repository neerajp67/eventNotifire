package eventnotifier.en.en.utils.main.usersList;

import android.support.annotation.StringRes;

import java.util.List;

import eventnotifier.en.en.utils.main.base.BaseView;


public interface UsersListView extends BaseView {

    void onProfilesIdsListLoaded(List<String> list);

    void showLocalProgress();

    void hideLocalProgress();

    void setTitle(@StringRes int title);

    void showEmptyListMessage(String message);

    void hideEmptyListMessage();

    void updateSelectedItem();
}
