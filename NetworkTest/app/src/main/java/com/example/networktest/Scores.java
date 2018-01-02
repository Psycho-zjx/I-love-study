package com.example.networktest;

/**
 * Created by 。 on 2017/12/30.
 */

public class Scores {

    private String kcm;
    private String xuefen;
    private String exam_score;
    private String pscj;
    private String zcj;
    private String level;
    private String jd;

    public Scores(String kcm, String xuefen, String exam_score, String pscj, String zcj, String level,String jd){
        this.kcm = kcm;
        this.xuefen = xuefen;
        this.exam_score = exam_score;
        this.pscj = pscj;
        this.zcj =zcj;
        this.level = level;
        this.jd = jd;
    }

    public String getKcm(){
        return kcm;
    }

    public String getXuefen(){
        return xuefen;
    }

    public String getExam_score() {return exam_score;}
}
