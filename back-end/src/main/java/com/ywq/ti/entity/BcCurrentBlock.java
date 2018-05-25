package com.ywq.ti.entity;

public class BcCurrentBlock {

    private String bcType; // 区块链类型，默认ETH
    private Long blockNumber; // 当前待处理的区块号，默认0

    public String getBcType() {
        return bcType;
    }

    public void setBcType(String bcType) {
        this.bcType = bcType;
    }

    public Long getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(Long blockNumber) {
        this.blockNumber = blockNumber;
    }
}