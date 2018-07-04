package com.pivotalsoft.property.Response;

import java.util.List;

/**
 * Created by USER on 1/9/2018.
 */

public class AddsInfoItem {


    /**
     * addata : [{"adid":"1","adpic":"ps-img-3.jpg","jobid":"14"},{"adid":"2","adpic":"ps-img-4.jpg","jobid":"12"}]
     * message : success
     */

    private String message;
    private List<AddataBean> addata;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<AddataBean> getAddata() {
        return addata;
    }

    public void setAddata(List<AddataBean> addata) {
        this.addata = addata;
    }

    public static class AddataBean {
        /**
         * adid : 1
         * adpic : ps-img-3.jpg
         * jobid : 14
         */

        private String adid;
        private String adpic;
        private String jobid;

        public String getAdid() {
            return adid;
        }

        public void setAdid(String adid) {
            this.adid = adid;
        }

        public String getAdpic() {
            return adpic;
        }

        public void setAdpic(String adpic) {
            this.adpic = adpic;
        }

        public String getJobid() {
            return jobid;
        }

        public void setJobid(String jobid) {
            this.jobid = jobid;
        }
    }
}
