package com.example.admin.fastpay.model.request;

import com.zxz.www.base.model.BaseModel;
import com.zxz.www.base.utils.DateUtil;
import com.zxz.www.base.utils.DeviceInfoUtil;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by 曾宪梓 on 2018/1/11.
 */

public class QuicklyPayReq extends BaseModel implements Serializable{


    /**
     * appid : 15151370098289//用户id
     * transactionId : md201801091112z//商户订单号
     * orderAmount : 19989//商户订单金额  1500以上 18000-2W
     * maxFee : 35//费率
     * cur : CNY
     * orderDesc : 刷卡消费//订单描叙
     * pageUrl : http://39.108.170.213/miaodao/returnData.jsp//写死
     * bgUrl : http://39.108.170.213/miaodao/returnUrl//写死
     * expiryDate : 2112//写死
     * cvv2 : 139//写死
     * cardType : 付款方卡类型 借记：DC；贷记：CC
     * private_flag : //对公对私 对公：B；对私：C
     * extraFee : 1//写死
     * ext : 1//写死
     * buyerIp : 127.0.0.8//下单IP
     * payerAcc : 6225768755180450//付款方银行卡号
     * payerName : 杨大龙//付款方名称
     * payerPhoneNo : 15173292172//付款方手机号
     * payerIdNum : 430525199211027410//付款方身份证号
     * payeeUnioBank : 308584000013//收款方银行联行号
     * payeeAcc : 6217857000033799972//收款方银行卡号
     * payeePhoneNo : 15173292172//收款方手机号
     * feeRate : 0.4//费率
     */

    private String appid = "15151370098289";
    private String transactionId = buildTransactionId();
    private String maxFee = "35";
    private String cur = "CNY";
    private String orderDesc = "刷卡消费";
    private String pageUrl = "http://39.108.170.213/miaodao/returnData.jsp";
    private String bgUrl = "http://39.108.170.213/miaodao/returnUrl";
    private String cardType = "CC";
    private String private_flag = "C";
    private String ext = "1";
    private String feeRate = "0.39";
    private String buyerIp = DeviceInfoUtil.getIPAddress();

    private String orderAmount = "250";
    private String expiryDate = "2112";
    private String cvv2 = "896";
    private String payerAcc = "6222530717431139";
    private String payerName = "杨大龙";
    private String payerPhoneNo = "15173292172";
    private String payerIdNum = "430525199211027410";
    private String payeeUnioBank = "308584000013";
    private String payeeAcc = "6217857000033799972";
    private String payeePhoneNo = "15173292172";
    private String payerBankCode = "308584000013";
    private String payeeBankCode = "308584000013";
    private String extraFee = "3";

    public void setPayWayId(int payWayId) {
        this.payWayId = payWayId;
    }

    private int payWayId;

    public static String buildTransactionId() {
        return "md" +  DateUtil.calendarToString(Calendar.getInstance(),"yyyyMMddHHmmSS");
    }


    public String getPayerBankCode() {
        return payerBankCode;
    }

    public void setPayerBankCode(String payerBankCode) {
        this.payerBankCode = payerBankCode;
    }

    public String getPayeeBankCode() {
        return payeeBankCode;
    }

    public void setPayeeBankCode(String payeeBankCode) {
        this.payeeBankCode = payeeBankCode;
    }



    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public float getTotalPrice() {
        return Float.parseFloat(orderAmount);
    }

    public float getFax() {
        return Float.parseFloat(extraFee);
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getMaxFee() {
        return maxFee;
    }

    public void setMaxFee(String maxFee) {
        this.maxFee = maxFee;
    }

    public String getCur() {
        return cur;
    }

    public void setCur(String cur) {
        this.cur = cur;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getBgUrl() {
        return bgUrl;
    }

    public void setBgUrl(String bgUrl) {
        this.bgUrl = bgUrl;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCvv2() {
        return cvv2;
    }

    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getPrivate_flag() {
        return private_flag;
    }

    public void setPrivate_flag(String private_flag) {
        this.private_flag = private_flag;
    }

    public String getExtraFee() {
        return extraFee;
    }

    public void setExtraFee(String extraFee) {
        this.extraFee = extraFee;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getBuyerIp() {
        return buyerIp;
    }

    public void setBuyerIp(String buyerIp) {
        this.buyerIp = buyerIp;
    }

    public String getPayerAcc() {
        return payerAcc;
    }

    public void setPayerAcc(String payerAcc) {
        this.payerAcc = payerAcc;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getPayerPhoneNo() {
        return payerPhoneNo;
    }

    public void setPayerPhoneNo(String payerPhoneNo) {
        this.payerPhoneNo = payerPhoneNo;
    }

    public String getPayerIdNum() {
        return payerIdNum;
    }

    public void setPayerIdNum(String payerIdNum) {
        this.payerIdNum = payerIdNum;
    }

    public String getPayeeUnioBank() {
        return payeeUnioBank;
    }

    public void setPayeeUnioBank(String payeeUnioBank) {
        this.payeeUnioBank = payeeUnioBank;
    }

    public String getPayeeAcc() {
        return payeeAcc;
    }

    public void setPayeeAcc(String payeeAcc) {
        this.payeeAcc = payeeAcc;
    }

    public String getPayeePhoneNo() {
        return payeePhoneNo;
    }

    public void setPayeePhoneNo(String payeePhoneNo) {
        this.payeePhoneNo = payeePhoneNo;
    }

    public String getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(String feeRate) {
        this.feeRate = feeRate;
    }
}
