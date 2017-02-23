package weixin.entity;

/**
 * 凭证实体类
 * @author lenovo
 *
 */
public class Token {

    // 接口访问凭证
    private String accessToken;
    // 凭证有效期，单位：秒
    private int expiresIn;
    // 用户刷新access_token
    private String refreshToken;
    // 授权用户唯一标识
    private String openId;
    // 用户授权的作用域，使用逗号（,）分隔
    private String scope;
    // 当且仅当该网站应用已获得该用户的userinfo授权时，才会出现该字段。
    private String unionId;

    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public int getExpiresIn() {
        return expiresIn;
    }
    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }
}
