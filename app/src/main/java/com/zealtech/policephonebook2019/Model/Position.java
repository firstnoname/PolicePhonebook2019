package com.zealtech.policephonebook2019.Model;

import com.zealtech.policephonebook2019.Model.base.BaseFilterItem;

import java.io.Serializable;

public class Position extends BaseFilterItem implements Serializable {

    String createDate;
    String editBy;
    String id;
    int positionId;
    String positionName;
    String shorName;
    String updateDate;

    @Override
    protected String setId() {
        return String.valueOf(positionId);
    }

    @Override
    protected String setName() {
        return positionName;
    }

    @Override
    public BaseFilterItem createTotalItem() {
        Position position = new Position();
        position.positionId = 0;
        position.positionName = "ทั้งหมด";

        return position;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getEditBy() {
        return editBy;
    }

    public void setEditBy(String editBy) {
        this.editBy = editBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getShorName() {
        return shorName;
    }

    public void setShorName(String shorName) {
        this.shorName = shorName;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}
