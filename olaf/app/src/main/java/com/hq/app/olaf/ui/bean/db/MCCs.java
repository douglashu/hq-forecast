package com.hq.app.olaf.ui.bean.db;


import com.hq.component.db.annotation.Column;
import com.hq.component.db.annotation.Table;
import com.hq.app.olaf.ui.widget.SelItem;

/**
 * Created by huwentao on 16-4-29.
 */
@Table(name = "t_mccs")
public class MCCs extends SelItem {
    @Column(name = "typeId") private String typeId;//行业类别类型ID
    @Column(name = "code", isId = true, autoGen = false) private String code;//行业类别Code
    @Column(name = "name") private String name;//行业类别名称
    @Column(name = "descs") private String descs;//描述说明信息

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

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

    public String getDescs() {
        return descs;
    }

    public void setDescs(String descs) {
        this.descs = descs;
    }

    @Override
    public String getKey() {
        return getCode();
    }

    @Override
    public String getValue() {
        return getName();
    }
}
