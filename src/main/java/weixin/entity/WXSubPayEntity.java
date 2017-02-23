package weixin.entity;

/**
 * 微信公众号支付参数实体
 * 微信浏览器里面打开H5网页中执行JS调起支付
 * @author lenovo
 *
 */
public class WXSubPayEntity {

    // 公众号id
    private String appId;
    // 时间戳
    private String timeStamp;
    // 随机字符串
    private String nonceStr;
    // 订单详情扩展字符串 统一下单接口返回的prepay_id参数值，提交格式如：prepay_id=***
    private String packages;
    // 签名方式  签名算法，暂支持MD5
    private String signType;
    // 签名
    private String paySign;

    public String getAppId() {
        return appId;
    }
    public void setAppId(String appId) {
        this.appId = appId;
    }
    public String getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
    public String getNonceStr() {
        return nonceStr;
    }
    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }
    public String getPackages() {
        return packages;
    }
    public void setPackages(String packages) {
        this.packages = packages;
    }
    public String getSignType() {
        return signType;
    }
    public void setSignType(String signType) {
        this.signType = signType;
    }
    public String getPaySign() {
        return paySign;
    }
    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }
}
