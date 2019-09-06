package com.hunglephuong.fiendlyserver.model.response;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

public class NumberInteractive {
    private int numberLike;
    private int numberCommnet;

    private int numberShare;
    public int getNumberLike() {
        return numberLike;
    }

    public void setNumberLike(int numberLike) {
        this.numberLike = numberLike;
    }

    public int getNumberCommnet() {
        return numberCommnet;
    }

    public void setNumberCommnet(int numberCommnet) {
        this.numberCommnet = numberCommnet;
    }

    public int getNumberShare() {
        return numberShare;
    }

    public void setNumberShare(int numberShare) {
        this.numberShare = numberShare;
    }
}
