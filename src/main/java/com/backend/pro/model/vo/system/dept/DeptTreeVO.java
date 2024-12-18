package com.backend.pro.model.vo.system.dept;

import lombok.Data;

import java.util.List;

@Data
public class DeptTreeVO {

    private String label;

    private Long value;

    private List<DeptTreeVO> children;

    public DeptTreeVO(String label, Long value , List<DeptTreeVO> children) {
        this.label = label;
        this.value = value;
        this.children = children;
    }

    public DeptTreeVO() {
    }
}
