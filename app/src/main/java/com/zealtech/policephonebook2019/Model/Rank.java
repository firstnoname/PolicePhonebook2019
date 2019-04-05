package com.zealtech.policephonebook2019.Model;

import com.zealtech.policephonebook2019.Model.base.BaseFilterItem;

import java.io.Serializable;

public class Rank extends BaseFilterItem implements Serializable {

    String color;
    String createDate;
    String editBy;
    String id;
    int rankId;
    String rankName;
    int sequence;
    String shortName;
    String updateDate;


    @Override
    protected String setId() {
        return String.valueOf(rankId);
    }

    @Override
    protected String setName() {
        return rankName;
    }

    @Override
    public BaseFilterItem createTotalItem() {
        Rank rank = new Rank();
        rank.rankId = 0;
        rank.rankName = "ทั้งหมด";

        return rank;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRankId() {
        return rankId;
    }

    public void setRankId(int rankId) {
        this.rankId = rankId;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}
