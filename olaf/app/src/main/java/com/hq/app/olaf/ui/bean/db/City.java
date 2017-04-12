package com.hq.app.olaf.ui.bean.db;

import com.hq.component.db.annotation.Column;
import com.hq.component.db.annotation.Table;
import com.hq.app.olaf.ui.widget.SelItem;


/**
 * Created by huwentao on 16-4-28.
 */
@Table(name = "t_city")
public class City extends SelItem {
    @Column(name = "pCode") String pCode;
    @Column(name = "code",isId = true,autoGen = false) private String code;
    @Column(name = "name") private String name;

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

    @Override
    public String getValue() {
        return getName();
    }

    @Override
    public String getKey() {
        return getCode();
    }

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }
}
