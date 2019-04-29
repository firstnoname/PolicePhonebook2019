package com.zealtech.policephonebook2019.Model.base;

public class HeaderItem extends BaseItem {

    private String name;
    private String id;

    public HeaderItem(String name) {
        super(BaseItemType.HEADER);
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
