package com.hand.dto;

import java.io.Serializable;

/**
 * Created by hand on 2017/4/7.
 */
public class RequestBodyDTO  implements Serializable{
    private String companyOID ;
    private String message ;
    private String timestamp ;
    private String signature ;
    private String nonce ;

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getCompanyOID() {
        return companyOID;
    }

    public void setCompanyOID(String companyOID) {
        this.companyOID = companyOID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
