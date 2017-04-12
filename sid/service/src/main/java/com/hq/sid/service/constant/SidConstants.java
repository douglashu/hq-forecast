package com.hq.sid.service.constant;

import java.util.Arrays;

/**
 * Created by Zale on 2016/12/12.
 */
public interface SidConstants {
    public static final String REDIS_KEY_PREFIX_FORGET_PWD="_forgetpwd";
    public static final String REDIS_KEY_PREFIX_FORGET_PWD_SEND="_forgetpwdsend";
      enum  CoreStatus{
        OK("00"),
        LOCK("01"),
        DEL("03");
        private String value;
        CoreStatus(String value) {
            this.value = value;
        }

         public String getValue() {
             return value;
         }
     }
     enum  OperStatus{
         OK("00"),
         DISABLE("01");
         private String value;

         OperStatus(String value) {
            this.value = value;
        }

         public String getValue() {
            return value;
        }
    }
      enum CoreType{
         HEAD_OFFICE("00"),
         MERCHANT("01"),
         SYSTEM("03");
         private String value;
         CoreType(String value) {
             this.value = value;
         }
         public String getValue() {
             return value;
         }
     }
    enum RefundAuth{
        NO_REFUND("00"),
        REFUND_PART("01"),
        REFUND_ALL("02");
        private String value;
        RefundAuth(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }
    enum PICType{
        BUSI_LICENSE("00"),
        BUSI_LICENSE_AUTH("01"),
        SPECIAL_LICENSE("02"),
        ORG_CODE_CERTIFICATE("03"),
        LOGO("04"),
        WEB_AUTH("05"),
        LEGAL_PERSON_CERT_FRONT("06"),
        LEGAL_PERSON_CERT_BACK("07"),
        SHOP_BOARD("08"),
        SHOP_SCENE("09")
        ;
        private String value;
        PICType(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
        public static PICType parse(String code){
            return Arrays.stream(PICType.values()).filter(picType -> picType.getValue().equals(code)).findFirst().get();
        }

    }
}
