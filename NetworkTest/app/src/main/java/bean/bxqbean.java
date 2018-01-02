package bean;

import java.util.List;

/**
 * Created by 。 on 2017/12/27.
 */

public class bxqbean {
    /**
     * result : success
     * msg : null
     * object : {"iTotalRecords":"xuexi","iTotalDisplayRecords":"xuexi","iDisplayStart":"0","iDisplayLength":"-xuexi","sEcho":"xuexi","sSearch":null,"aaData":[{"xh":"201600162110","xm":"张健翔","bm":"电气16.6","xsh":"19","kch":"sd01920670","kxh":2,"xnxq":"2017-2018-xuexi","qmcj":"64","pscj":20,"lrzt":"确定","sycj":0,"qzcj":0,"kscj":84,"kccj":null,"kssj":"20180115","cxbkbz":null,"bz":null,"djm":null,"sfjscj":"0","xsjc":"电气学院","zczt":"未","jsm":"范成贤4","jsh":"200799014397","kkxsh":"19","kcm":"复变、场论、拉氏变换","xs":48,"xf":3,"kcsx":"必修","wfzdj":"A","wfzjd":4.5,"kscjView":"84.0","sycjView":"0.0","pscjView":"20.0","qzcjView":"0.0","qmcjView":"64","wfzcjView":null,"id":null}]}
     */

    private String result;
    private Object msg;
    private ObjectBean object;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public ObjectBean getObject() {
        return object;
    }

    public void setObject(ObjectBean object) {
        this.object = object;
    }

    public static class ObjectBean {
        /**
         * iTotalRecords : xuexi
         * iTotalDisplayRecords : xuexi
         * iDisplayStart : 0
         * iDisplayLength : -xuexi
         * sEcho : xuexi
         * sSearch : null
         * aaData : [{"xh":"201600162110","xm":"张健翔","bm":"电气16.6","xsh":"19","kch":"sd01920670","kxh":2,"xnxq":"2017-2018-xuexi","qmcj":"64","pscj":20,"lrzt":"确定","sycj":0,"qzcj":0,"kscj":84,"kccj":null,"kssj":"20180115","cxbkbz":null,"bz":null,"djm":null,"sfjscj":"0","xsjc":"电气学院","zczt":"未","jsm":"范成贤4","jsh":"200799014397","kkxsh":"19","kcm":"复变、场论、拉氏变换","xs":48,"xf":3,"kcsx":"必修","wfzdj":"A","wfzjd":4.5,"kscjView":"84.0","sycjView":"0.0","pscjView":"20.0","qzcjView":"0.0","qmcjView":"64","wfzcjView":null,"id":null}]
         */

        private String iTotalRecords;
        private String iTotalDisplayRecords;
        private String iDisplayStart;
        private String iDisplayLength;
        private String sEcho;
        private Object sSearch;
        private List<AaDataBean> aaData;

        public String getITotalRecords() {
            return iTotalRecords;
        }

        public void setITotalRecords(String iTotalRecords) {
            this.iTotalRecords = iTotalRecords;
        }

        public String getITotalDisplayRecords() {
            return iTotalDisplayRecords;
        }

        public void setITotalDisplayRecords(String iTotalDisplayRecords) {
            this.iTotalDisplayRecords = iTotalDisplayRecords;
        }

        public String getIDisplayStart() {
            return iDisplayStart;
        }

        public void setIDisplayStart(String iDisplayStart) {
            this.iDisplayStart = iDisplayStart;
        }

        public String getIDisplayLength() {
            return iDisplayLength;
        }

        public void setIDisplayLength(String iDisplayLength) {
            this.iDisplayLength = iDisplayLength;
        }

        public String getSEcho() {
            return sEcho;
        }

        public void setSEcho(String sEcho) {
            this.sEcho = sEcho;
        }

        public Object getSSearch() {
            return sSearch;
        }

        public void setSSearch(Object sSearch) {
            this.sSearch = sSearch;
        }

        public List<AaDataBean> getAaData() {
            return aaData;
        }

        public void setAaData(List<AaDataBean> aaData) {
            this.aaData = aaData;
        }

        public static class AaDataBean {
            /**
             * xh : 201600162110
             * xm : 张健翔
             * bm : 电气16.6
             * xsh : 19
             * kch : sd01920670
             * kxh : 2
             * xnxq : 2017-2018-xuexi
             * qmcj : 64
             * pscj : 20.0
             * lrzt : 确定
             * sycj : 0.0
             * qzcj : 0.0
             * kscj : 84.0
             * kccj : null
             * kssj : 20180115
             * cxbkbz : null
             * bz : null
             * djm : null
             * sfjscj : 0
             * xsjc : 电气学院
             * zczt : 未
             * jsm : 范成贤4
             * jsh : 200799014397
             * kkxsh : 19
             * kcm : 复变、场论、拉氏变换
             * xs : 48.0
             * xf : 3.0
             * kcsx : 必修
             * wfzdj : A
             * wfzjd : 4.5
             * kscjView : 84.0
             * sycjView : 0.0
             * pscjView : 20.0
             * qzcjView : 0.0
             * qmcjView : 64
             * wfzcjView : null
             * id : null
             */

            private String xh;
            private String xm;
            private String bm;
            private String xsh;
            private String kch;
            private int kxh;
            private String xnxq;
            private String qmcj;
            private double pscj;
            private String lrzt;
            private double sycj;
            private double qzcj;
            private double kscj;
            private Object kccj;
            private String kssj;
            private Object cxbkbz;
            private Object bz;
            private Object djm;
            private String sfjscj;
            private String xsjc;
            private String zczt;
            private String jsm;
            private String jsh;
            private String kkxsh;
            private String kcm;
            private double xs;
            private double xf;
            private String kcsx;
            private String wfzdj;
            private double wfzjd;
            private String kscjView;
            private String sycjView;
            private String pscjView;
            private String qzcjView;
            private String qmcjView;
            private Object wfzcjView;
            private Object id;

            public String getXh() {
                return xh;
            }

            public void setXh(String xh) {
                this.xh = xh;
            }

            public String getXm() {
                return xm;
            }

            public void setXm(String xm) {
                this.xm = xm;
            }

            public String getBm() {
                return bm;
            }

            public void setBm(String bm) {
                this.bm = bm;
            }

            public String getXsh() {
                return xsh;
            }

            public void setXsh(String xsh) {
                this.xsh = xsh;
            }

            public String getKch() {
                return kch;
            }

            public void setKch(String kch) {
                this.kch = kch;
            }

            public int getKxh() {
                return kxh;
            }

            public void setKxh(int kxh) {
                this.kxh = kxh;
            }

            public String getXnxq() {
                return xnxq;
            }

            public void setXnxq(String xnxq) {
                this.xnxq = xnxq;
            }

            public String getQmcj() {
                return qmcj;
            }

            public void setQmcj(String qmcj) {
                this.qmcj = qmcj;
            }

            public double getPscj() {
                return pscj;
            }

            public void setPscj(double pscj) {
                this.pscj = pscj;
            }

            public String getLrzt() {
                return lrzt;
            }

            public void setLrzt(String lrzt) {
                this.lrzt = lrzt;
            }

            public double getSycj() {
                return sycj;
            }

            public void setSycj(double sycj) {
                this.sycj = sycj;
            }

            public double getQzcj() {
                return qzcj;
            }

            public void setQzcj(double qzcj) {
                this.qzcj = qzcj;
            }

            public double getKscj() {
                return kscj;
            }

            public void setKscj(double kscj) {
                this.kscj = kscj;
            }

            public Object getKccj() {
                return kccj;
            }

            public void setKccj(Object kccj) {
                this.kccj = kccj;
            }

            public String getKssj() {
                return kssj;
            }

            public void setKssj(String kssj) {
                this.kssj = kssj;
            }

            public Object getCxbkbz() {
                return cxbkbz;
            }

            public void setCxbkbz(Object cxbkbz) {
                this.cxbkbz = cxbkbz;
            }

            public Object getBz() {
                return bz;
            }

            public void setBz(Object bz) {
                this.bz = bz;
            }

            public Object getDjm() {
                return djm;
            }

            public void setDjm(Object djm) {
                this.djm = djm;
            }

            public String getSfjscj() {
                return sfjscj;
            }

            public void setSfjscj(String sfjscj) {
                this.sfjscj = sfjscj;
            }

            public String getXsjc() {
                return xsjc;
            }

            public void setXsjc(String xsjc) {
                this.xsjc = xsjc;
            }

            public String getZczt() {
                return zczt;
            }

            public void setZczt(String zczt) {
                this.zczt = zczt;
            }

            public String getJsm() {
                return jsm;
            }

            public void setJsm(String jsm) {
                this.jsm = jsm;
            }

            public String getJsh() {
                return jsh;
            }

            public void setJsh(String jsh) {
                this.jsh = jsh;
            }

            public String getKkxsh() {
                return kkxsh;
            }

            public void setKkxsh(String kkxsh) {
                this.kkxsh = kkxsh;
            }

            public String getKcm() {
                return kcm;
            }

            public void setKcm(String kcm) {
                this.kcm = kcm;
            }

            public double getXs() {
                return xs;
            }

            public void setXs(double xs) {
                this.xs = xs;
            }

            public double getXf() {
                return xf;
            }

            public void setXf(double xf) {
                this.xf = xf;
            }

            public String getKcsx() {
                return kcsx;
            }

            public void setKcsx(String kcsx) {
                this.kcsx = kcsx;
            }

            public String getWfzdj() {
                return wfzdj;
            }

            public void setWfzdj(String wfzdj) {
                this.wfzdj = wfzdj;
            }

            public double getWfzjd() {
                return wfzjd;
            }

            public void setWfzjd(double wfzjd) {
                this.wfzjd = wfzjd;
            }

            public String getKscjView() {
                return kscjView;
            }

            public void setKscjView(String kscjView) {
                this.kscjView = kscjView;
            }

            public String getSycjView() {
                return sycjView;
            }

            public void setSycjView(String sycjView) {
                this.sycjView = sycjView;
            }

            public String getPscjView() {
                return pscjView;
            }

            public void setPscjView(String pscjView) {
                this.pscjView = pscjView;
            }

            public String getQzcjView() {
                return qzcjView;
            }

            public void setQzcjView(String qzcjView) {
                this.qzcjView = qzcjView;
            }

            public String getQmcjView() {
                return qmcjView;
            }

            public void setQmcjView(String qmcjView) {
                this.qmcjView = qmcjView;
            }

            public Object getWfzcjView() {
                return wfzcjView;
            }

            public void setWfzcjView(Object wfzcjView) {
                this.wfzcjView = wfzcjView;
            }

            public Object getId() {
                return id;
            }

            public void setId(Object id) {
                this.id = id;
            }
        }
    }
}
