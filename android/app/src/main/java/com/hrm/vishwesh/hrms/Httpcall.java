package com.hrm.vishwesh.hrms;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vishw on 29-11-2017.
 */

public class Httpcall {
        //intiallisation
        private HashMap<String, String> params;

        //getting parammeters
        public HashMap<String, String> getParams() {
            return params;
        }
        //setting parameters
        public void setParams(HashMap<String, String> params) {
            this.params = params;
        }



}
