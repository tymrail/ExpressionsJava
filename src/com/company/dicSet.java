package com.company;

public class dicSet {
    private String charactor;
    private Integer power;
    public String getCharactor() {
        return charactor;
    }
    public void setCharactor(String charactor) {
        this.charactor = charactor;
    }
    public Integer getPower() {
        return power;
    }
    public void setPower(Integer power) {
        this.power = power;
    }

    public void setAll(String charactor, Integer power) {
        this.charactor = charactor;
        this.power = power;
    }
}
