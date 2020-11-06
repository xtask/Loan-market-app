package com.market.loan.tools;

import com.market.loan.bean.ConfigData;

import java.util.ArrayList;
import java.util.List;

public class DictsFilter {
    List<ConfigData> dicts;

    DictsFilter() {

    }

    public DictsFilter(List<ConfigData> dicts) {
        this.dicts = dicts;
    }

    public List<ConfigData> getDictsByType(String type) {
        List<ConfigData> dictsByType = new ArrayList<>();
        for (ConfigData dict : dicts) {
            if (dict.getType().equals(type)) {
                dictsByType.add(dict);
            }

        }
        return dictsByType;
    }
}
