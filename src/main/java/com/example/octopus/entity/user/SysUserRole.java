package com.example.octopus.entity.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/29 3:42 下午
 * @modified By：
 */
@Data
public class SysUserRole implements Serializable {
    static final long serialVersionUID = 1L;

    private String stuNumber;

    private long roleId;

}