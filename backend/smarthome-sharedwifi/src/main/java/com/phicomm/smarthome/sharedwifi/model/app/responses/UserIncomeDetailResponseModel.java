package com.phicomm.smarthome.sharedwifi.model.app.responses;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.phicomm.smarthome.sharedwifi.model.app.AppIncomeDetailsDaoModel;
import com.phicomm.smarthome.sharedwifi.model.common.BaseResponseModel;
import com.phicomm.smarthome.util.StringUtil;

/**
 * 查询收入明细返回参数
 * @author wenhua.tang
 *
 */
public class UserIncomeDetailResponseModel extends BaseResponseModel{
    
    public UserIncomeDetailResponseModel() {
        super();
        
        //TODO 分页未实现，先返回所有数据
        hasMoreData = 0; //0表示没有下一页
        timeStamp = System.currentTimeMillis()/1000;
        incomeDetails = new ArrayList<UserIncomeDetailResponseModel.Details>(1);
    }

    @JsonProperty("has_more_data")
    private int hasMoreData;

    @JsonProperty("timestamp")
    private long timeStamp;
    
    @JsonProperty("income_details")
    private List<Details> incomeDetails;

    public int getHasMoreData() {
        return hasMoreData;
    }

    public void setHasMoreData(int hasMoreData) {
        this.hasMoreData = hasMoreData;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
    
    public List<Details> getIncomeDetails() {
        return incomeDetails;
    }

    public void setIncomeDetails(List<Details> incomeDetails) {
        this.incomeDetails = incomeDetails;
    }


    public static class Details {
        @JsonProperty("action_name")
        private String actionName;
        
        private String cost;
        
        private String time;
        
        public Details(AppIncomeDetailsDaoModel model) {
            actionName = model.getActionName();
            cost = formatCost(model.getCost());
            time = formatTime(model.getCreateTime() * 1000);
        }
        
        private String formatTime(long time) {
            Date dat = new Date(time);
            //根据GUI效果图，需要3个空格
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd   HH:mm");
            
            return formatter.format(dat);
        }

        public String getActionName() {
            return actionName;
        }

        public void setActionName(String actionName) {
            this.actionName = actionName;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
        
        /**
         * cost like +0.1, -0.23 .etc
         * @param cost
         * @return
         */
        private String formatCost(String cost) {
            if (StringUtil.isNullOrEmpty(cost) || cost.length() == 1) {//格式不正确
                return cost;
            }
            
            try {
                cost = cost.trim();
                char firstSymbol = cost.charAt(0);
                if (firstSymbol != '+' && firstSymbol != '-') {
                    return cost;
                }
                double costD = Double.parseDouble(cost.substring(1));
                return firstSymbol + StringUtil.formatNumberWithTwoPoint(costD);
            } catch (Exception e) {
            }
            return cost;
        }
    }
}
