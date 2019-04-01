package com.zealtech.policephonebook2019.Model.base;

import java.io.Serializable;

abstract public class BaseFilterItem implements Serializable {

    protected abstract String setId();
    protected abstract String setName();

    public String getId() {
        return setId();
    }

    public String getName() {
        return setName();
    }
}
