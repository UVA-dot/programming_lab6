package models;

import java.io.Serializable;

public class DragonHead implements Serializable {
    private Float size; //Поле может быть null
    public DragonHead(){

    }
    public DragonHead(Float size){
        this.size = size;
    }

    public void setSize(Float size) {
        this.size = size;
    }
    public Float getSize(){
        return size;
    }
}