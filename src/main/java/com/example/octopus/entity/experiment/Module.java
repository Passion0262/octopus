package com.example.octopus.entity.experiment;
import lombok.Data;

import java.sql.Date;

@Data
public class Module {

    private long id;

    private String name;

    private int number;

    private long experimentId;


}
