package com.example.octopus.entity.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/28 1:48 下午
 * @modified By：
 */
@Data
public class SysRole implements Serializable {
    static final long serialVersionUID = 1L;

    private long id;

    private String name;

}
