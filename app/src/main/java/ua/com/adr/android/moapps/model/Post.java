package ua.com.adr.android.moapps.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("err")
    @Expose
    private Boolean err;
    @SerializedName("code")
    @Expose
    private Integer code;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Boolean isErr() {
        return err;
    }

    public void setErr(Boolean err) {
        this.err = err;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


}
