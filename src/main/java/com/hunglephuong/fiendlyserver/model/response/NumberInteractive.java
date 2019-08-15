package com.hunglephuong.fiendlyserver.model.response;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class NumberInteractive {
    @Column(name = "number_like")
    private int numberLike;
    @Column(name = "number_comment")
    private int numberCommnet;
    @Column(name = "number_share")
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
