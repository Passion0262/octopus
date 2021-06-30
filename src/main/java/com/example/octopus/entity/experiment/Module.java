package com.example.octopus.entity.experiment;
import lombok.Data;

import java.sql.Date;

@Data
public class Module {

    private long moduleId;

    private int moduleNumber;

    private String moduleName;

    private long experimentId;


}
