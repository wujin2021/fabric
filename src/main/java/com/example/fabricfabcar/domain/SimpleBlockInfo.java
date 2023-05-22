package com.example.fabricfabcar.domain;

import com.example.fabricfabcar.utils.HashAndStringUtil;

@lombok.Data
public class SimpleBlockInfo {
    public long blockNumber;
    public String preHash;
    public String dataHash;
    public SimpleBlockInfo(long blockNumber, byte[] preHash, byte[] dataHash) {
        this.blockNumber = blockNumber;
        this.preHash = HashAndStringUtil.hashToString(preHash);
        this.dataHash = HashAndStringUtil.hashToString(dataHash);
    }

    @Override
    public String toString() {
        return "SimpleBlockInfo{" +
                "blockNumber=" + blockNumber +
                ", preHash='" + preHash + '\'' +
                ", dataHash='" + dataHash + '\'' +
                '}';
    }
}
