package eventnotifier.en.en.utils.managers.listeners;

public interface OnObjectChangedListener<T> {

    void onObjectChanged(T obj);

    void onError(String errorText);
}
