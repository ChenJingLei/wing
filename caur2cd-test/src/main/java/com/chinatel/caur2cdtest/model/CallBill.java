package com.chinatel.caur2cdtest.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class CallBill {

    @Id
    @GenericGenerator(name="idGenerator", strategy="uuid") //这个是hibernate的注解/生成32位UUID
    @GeneratedValue(generator="idGenerator")
    private String id;

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
