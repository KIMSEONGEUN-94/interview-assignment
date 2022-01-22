package com.interview.assignment.common;


public enum EnumResult {

    /*
     * Result Code
     *   cd : result code ( 4자리 ),
     *   msg : result message ( String ),
     *   desc : 메세지 설명.
     */

    SUCCESS("0000", "success.", "ok"),
    FAIL("9999", "fail", "unknown error");

    private String cd;
    private String msg;
    private String desc;

    private EnumResult(String cd, String msg, String desc) {
        this.cd = cd;
        this.msg = msg;
        this.desc = desc;
    }

    /**
     * @return the cd
     */
    public String getCd() {
        return cd;
    }

    /**
     * @param cd the cd to set
     */
    public void setCd(String cd) {
        this.cd = cd;
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @param desc the status to set
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @return the desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
