package com.hongbang.ic.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by xionghf on 16/4/10.
 */
public class CommunityInfoList {

    @SerializedName("parks")
    public ArrayList<OneCommunityInfo> parks;

}
