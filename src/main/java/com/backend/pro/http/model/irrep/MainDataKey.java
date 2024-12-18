package com.backend.pro.http.model.irrep;

import com.backend.pro.http.model.ir.MainData;
import lombok.Data;

@Data
public class MainDataKey {
    private String kfsj;
    private String RegisterID;
    private String Kfysxm;

    public MainDataKey(MainData data) {
        this.kfsj = data.getKfsj();
        this.RegisterID = data.getRegisterID();
        this.Kfysxm = data.getKfysxm();
    }

    public MainDataKey(String kfsj, String registerID, String kfysxm) {
        this.kfsj = kfsj;
        this.RegisterID = registerID;
        this.Kfysxm = kfysxm;
    }

    // ... 其他的equals(), hashCode() 方法保持不变
}