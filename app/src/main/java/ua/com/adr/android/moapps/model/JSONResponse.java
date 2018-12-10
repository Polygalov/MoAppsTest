package ua.com.adr.android.moapps.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class JSONResponse {

    @SerializedName("data")
    @Expose
    private ArrayList<Datum> datas = new ArrayList<>();

    @SerializedName("err")
    @Expose
    private String err;

    @SerializedName("code")
    @Expose
    private String code;

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<Datum> getDatas() {
        return datas;    }

    public void setDatas(ArrayList<Datum> datas) {
        this.datas = datas;    }

}
