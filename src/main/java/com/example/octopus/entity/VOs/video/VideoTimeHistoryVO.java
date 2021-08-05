package com.example.octopus.entity.VOs.video;

import lombok.Data;

import java.sql.Date;

/**
 * @author ：shadow
 * @date ：Created in 2021/7/14 3:26 下午
 */
@Data
public class VideoTimeHistoryVO {

    Date date;  //日期

    Integer time;   //学习时长

}
