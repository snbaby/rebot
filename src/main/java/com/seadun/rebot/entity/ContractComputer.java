package com.seadun.rebot.entity;

import java.util.Date;

public class ContractComputer {
    private String contractDetailId;

    private String contractId;

    private String computerId;

    private String eqType;

    private String eqNo;
    
    private String cpu;

    private String contractDetailStatus;
    
    private Date contractDetailUptTime;

    private String contract;

    private String contractStatus;

    private String biosSn;

    private String opSystem;

    private String opInstallDate;

    private String ip;

    public String getContractDetailId() {
        return contractDetailId;
    }

    public void setContractDetailId(String contractDetailId) {
        this.contractDetailId = contractDetailId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getComputerId() {
        return computerId;
    }

    public void setComputerId(String computerId) {
        this.computerId = computerId;
    }

    public String getEqType() {
        return eqType;
    }

    public void setEqType(String eqType) {
        this.eqType = eqType;
    }

    public String getEqNo() {
        return eqNo;
    }

    public void setEqNo(String eqNo) {
        this.eqNo = eqNo;
    }

    public String getContractDetailStatus() {
        return contractDetailStatus;
    }

    public void setContractDetailStatus(String contractDetailStatus) {
        this.contractDetailStatus = contractDetailStatus;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public String getBiosSn() {
        return biosSn;
    }

    public void setBiosSn(String biosSn) {
        this.biosSn = biosSn;
    }

    public String getOpSystem() {
        return opSystem;
    }

    public void setOpSystem(String opSystem) {
        this.opSystem = opSystem;
    }

    public String getOpInstallDate() {
        return opInstallDate;
    }

    public void setOpInstallDate(String opInstallDate) {
        this.opInstallDate = opInstallDate;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public Date getContractDetailUptTime() {
		return contractDetailUptTime;
	}

	public void setContractDetailUptTime(Date contractDetailUptTime) {
		this.contractDetailUptTime = contractDetailUptTime;
	}
    
    
}