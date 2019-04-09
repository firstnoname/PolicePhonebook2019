package com.zealtech.policephonebook2019.Model.base;

import java.io.Serializable;


abstract public class BaseItem implements Serializable {

    private BaseItemType type;

    public BaseItem() {
        this(BaseItemType.ITEM);
    }

    public BaseItem(BaseItemType type) {
        this.type = type;
    }

    protected abstract String setId();

    protected abstract String setName();

    public String getId() {
        return setId();
    }

    public String getName() {
        return setName();
    }

    public BaseItemType getType() {
        return type;
    }
}
