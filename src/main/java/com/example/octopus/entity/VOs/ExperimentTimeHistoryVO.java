package com.example.octopus.entity.VOs;

import lombok.Data;

import java.sql.Date;

/**
 * @author ：shadow
 * @date ：Created in 2021/7/14 3:55 下午
 */
@Data
public class ExperimentTimeHistoryVO {

    Date date;  //日期

    Integer time;   //学习时长

}
