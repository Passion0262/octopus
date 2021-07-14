package com.example.octopus.utils;

import com.example.octopus.entity.user.Student;

import java.math.BigInteger;
import java.util.*;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;

/**
 * @author: Xu
 * @date: 2021/7/14 14:38
 * 解析excel文件，返回student列表
 */

public class parseExcelUtils {

    public static List<Student> parseExcel(String path){

        List<Student> stu_list = new ArrayList<>();

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(path);
            XSSFWorkbook sheets = new XSSFWorkbook(fileInputStream);
            //获取sheet
            XSSFSheet sheet = sheets.getSheetAt(0);

            int rows = sheet.getPhysicalNumberOfRows(); // 行数
            XSSFRow row = sheet.getRow(0);
            int columns = row.getPhysicalNumberOfCells();  // 列数
            // 逐行读取
            for(int i=1;i<rows;i++){
                row = sheet.getRow(i);

                // todo 增加判断 列数是否符合条件
                // todo 专业和班级是否可以使用名字

                Student stu = new Student();
                stu.setStuNumber(Long.parseLong(row.getCell(0).toString()));
                stu.setName(row.getCell(1).toString());
                stu.setMajorId(Integer.parseInt(row.getCell(2).toString()));
                stu.setClassId(Integer.parseInt(row.getCell(3).toString()));
                stu.setSchool(row.getCell(4).toString());
                stu.setPhoneNumber(row.getCell(5).toString());

                stu_list.add(stu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stu_list;
    }

}
