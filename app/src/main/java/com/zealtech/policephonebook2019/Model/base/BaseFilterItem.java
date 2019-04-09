package com.zealtech.policephonebook2019.Model.base;

import java.io.Serializable;


abstract public class BaseFilterItem implements Serializable {

    private BaseItemType type;

    public BaseFilterItem() {
        this(BaseItemType.ITEM);
    }

    public BaseFilterItem(BaseItemType type) {
        this.type = type;
    }

    protected abstract String setId();

    protected abstract String setName();

    public abstract BaseFilterItem createTotalItem();

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
