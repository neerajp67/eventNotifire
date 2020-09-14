package eventnotifier.en.en.utils.managers.listeners;

import java.util.List;

public interface OnDataChangedListener<T> {

    public void onListChanged(List<T> list);
}
