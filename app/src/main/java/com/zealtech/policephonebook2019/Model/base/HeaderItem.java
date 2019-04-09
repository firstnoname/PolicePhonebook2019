package com.zealtech.policephonebook2019.Model.base;

import android.content.res.Resources;

public class HeaderItem extends BaseItem {

    private String name;

    public HeaderItem(String name) {
        super(BaseItemType.HEADER);
        this.name = name;
    }

    @Override
    protected String setId() {
        return this.name;
    }

    @Override
    protected String setName() {
        return this.name;
    }

}
