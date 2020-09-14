package eventnotifier.en.en.utils.model;


import eventnotifier.en.en.utils.enums.ItemType;

public interface LazyLoading {
    ItemType getItemType();
    void setItemType(ItemType itemType);
}
