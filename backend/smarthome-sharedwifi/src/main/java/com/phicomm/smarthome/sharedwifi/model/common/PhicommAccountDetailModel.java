package com.phicomm.smarthome.sharedwifi.model.common;

/**
 * 斐讯云账号的 bean对象，用来映射斐讯云账号
 * @author rongwei.huang
 *
 */
public class PhicommAccountDetailModel {
    private String token_status;
    private String error;
    private Data data;
    private String reason;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getToken_status() {
        return token_status;
    }

    public void setToken_status(String token_status) {
        this.token_status = token_status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private String zipcode;
        private String uid;
        private String img;
        private String address;
        private String zone;
        private String sex;
        private String nickname;
        private String phonenumber;
        private String age;
        private String realname;
        private String mailaddress;
        private String accountname;


        public String getMailaddress() {
            return mailaddress;
        }
        public void setMailaddress(String mailaddress) {
            this.mailaddress = mailaddress;
        }
        public String getAccountname() {
            return accountname;
        }
        public void setAccountname(String accountname) {
            this.accountname = accountname;
        }
        public String getZipcode() {
            return zipcode;
        }
        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }
        public String getUid() {
            return uid;
        }
        public void setUid(String uid) {
            this.uid = uid;
        }
        public String getImg() {
            return img;
        }
        public void setImg(String img) {
            this.img = img;
        }
        public String getAddress() {
            return address;
        }
        public void setAddress(String address) {
            this.address = address;
        }
        public String getZone() {
            return zone;
        }
        public void setZone(String zone) {
            this.zone = zone;
        }
        public String getSex() {
            return sex;
        }
        public void setSex(String sex) {
            this.sex = sex;
        }
        public String getNickname() {
            return nickname;
        }
        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
        public String getPhonenumber() {
            return phonenumber;
        }
        public void setPhonenumber(String phonenumber) {
            this.phonenumber = phonenumber;
        }
        public String getAge() {
            return age;
        }
        public void setAge(String age) {
            this.age = age;
        }
        public String getRealname() {
            return realname;
        }
        public void setRealname(String realname) {
            this.realname = realname;
        }
    }
}
