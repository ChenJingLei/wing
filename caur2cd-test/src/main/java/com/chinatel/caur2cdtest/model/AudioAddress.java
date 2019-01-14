package com.chinatel.caur2cdtest.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class AudioAddress {

    @Id
    private String sessionid;

    private String callerNbr;
    private String calledNbr;
    private String chargeNbr;
    private String voiceAddress;

}
