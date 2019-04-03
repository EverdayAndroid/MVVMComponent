package com.everday.module_login.entity;

public class LoginBean {
    /**
     * 对应数据库 user.id
     */
    private Integer userId;

    /**
     * 对应数据库 user.login_name
     * 登录名
     */
    private String loginName;

    /**
     * 对应数据库 user.password
     * 密码
     */
    private String password;

    /**
     * 对应数据库 user.nick_name
     * 用户昵称
     */
    private String nickName;

    /**
     * 对应数据库 user.telephone
     * 手机号
     */
    private String telephone;

    /**
     * 对应数据库 user.email
     * 邮箱
     */
    private String email;

    /**
     * 对应数据库 user.create_time
     * 创建时间
     */
    private String createTime;

    /**
     * 对应数据库 user.last_login_time
     * 最后登录时间
     */
    private String lastLoginTime;

    /**
     * 对应数据库 user.status
     * 1:有效，0:禁止登录
     */
    private Integer status;

    private boolean rememberMe;

    private Integer roleId;

    private String roleName;

    private String roleCode;

    private String token;
    //头像
    private String avatar;

    private String organizationName;

    private String communityName;
    /**
     * 社区id
     */
    private Integer communityId;
    private String qq;
    private String wechat;
    private String signature;
    /** 自我介绍 */
    private String introduction;
    private String comments;
    //头像
    private String img;
    private String cityOrganizationName;
    private String subOrganizationName;

    /**
     * 1代表可以切换系统
     */
    private Integer flag;

    /** 系统标识 */
    private String system;

    /** ---------------------以下属性为派出所系统 ------------------------------------*/
    private Integer policeId;
    private String policeName;
    /**
     * 1: 民警主  2：民警辅  3：辅警
     */
    private Integer identity;
    private String adminName;
    private String adminHeadImg;

    //派出所id
    private String organizationId;
    // 市局id
    private Integer cityOrganizationId;
    // 分局id
    private Integer subOrganizationId;

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public Integer getCityOrganizationId() {
        return cityOrganizationId;
    }

    public void setCityOrganizationId(Integer cityOrganizationId) {
        this.cityOrganizationId = cityOrganizationId;
    }

    public Integer getSubOrganizationId() {
        return subOrganizationId;
    }

    public void setSubOrganizationId(Integer subOrganizationId) {
        this.subOrganizationId = subOrganizationId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public Integer getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCityOrganizationName() {
        return cityOrganizationName;
    }

    public void setCityOrganizationName(String cityOrganizationName) {
        this.cityOrganizationName = cityOrganizationName;
    }

    public String getSubOrganizationName() {
        return subOrganizationName;
    }

    public void setSubOrganizationName(String subOrganizationName) {
        this.subOrganizationName = subOrganizationName;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public Integer getPoliceId() {
        return policeId;
    }

    public void setPoliceId(Integer policeId) {
        this.policeId = policeId;
    }

    public String getPoliceName() {
        return policeName;
    }

    public void setPoliceName(String policeName) {
        this.policeName = policeName;
    }

    public Integer getIdentity() {
        return identity;
    }

    public void setIdentity(Integer identity) {
        this.identity = identity;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminHeadImg() {
        return adminHeadImg;
    }

    public void setAdminHeadImg(String adminHeadImg) {
        this.adminHeadImg = adminHeadImg;
    }
}
