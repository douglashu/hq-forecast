package com.hq.app.olaf.ui.bean.db;


import com.hq.component.db.annotation.Column;
import com.hq.component.db.annotation.Table;
import com.hq.app.olaf.ui.widget.SelItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huwentao on 16-4-28.
 */
@Table(name = "t_province")
public class Province extends SelItem {
    @Column(name = "code",isId = true,autoGen = false) private String code;
    @Column(name = "name") private String name;
    private List<City> citys = new ArrayList<>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<City> getCitys() {
        return citys;
    }

    public void setCitys(List<City> citys) {
        this.citys.clear();
        this.citys.addAll(citys);
    }

    @Override
    public String getValue() {
        return getName();
    }

    @Override
    public String getKey() {
        return getCode();
    }
}
