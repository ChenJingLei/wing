package com.chinatel.caur2cdtest.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class CallBill {

    @Id
    private String sessionid;

    private String chargeNbr;
    private String displayNbr;
    private String callerNbr;
    private String calledNbr;
    private String serviceNbr;
    private String startTime;
    private String endTime;
    private String duration;
    private String serviceType;

}
