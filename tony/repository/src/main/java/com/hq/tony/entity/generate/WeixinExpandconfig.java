package com.hq.tony.entity.generate;

import java.io.Serializable;

public class WeixinExpandconfig implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_expandconfig.ID
     *
     * @mbg.generated Sat Mar 04 11:19:51 CST 2017
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_expandconfig.ACCOUNTID
     *
     * @mbg.generated Sat Mar 04 11:19:51 CST 2017
     */
    private String accountid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_expandconfig.CLASSNAME
     *
     * @mbg.generated Sat Mar 04 11:19:51 CST 2017
     */
    private String classname;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_expandconfig.KEYWORD
     *
     * @mbg.generated Sat Mar 04 11:19:51 CST 2017
     */
    private String keyword;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_expandconfig.NAME
     *
     * @mbg.generated Sat Mar 04 11:19:51 CST 2017
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_expandconfig.CONTENT
     *
     * @mbg.generated Sat Mar 04 11:19:51 CST 2017
     */
    private String content;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table weixin_expandconfig
     *
     * @mbg.generated Sat Mar 04 11:19:51 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column weixin_expandconfig.ID
     *
     * @return the value of weixin_expandconfig.ID
     *
     * @mbg.generated Sat Mar 04 11:19:51 CST 2017
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column weixin_expandconfig.ID
     *
     * @param id the value for weixin_expandconfig.ID
     *
     * @mbg.generated Sat Mar 04 11:19:51 CST 2017
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column weixin_expandconfig.ACCOUNTID
     *
     * @return the value of weixin_expandconfig.ACCOUNTID
     *
     * @mbg.generated Sat Mar 04 11:19:51 CST 2017
     */
    public String getAccountid() {
        return accountid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column weixin_expandconfig.ACCOUNTID
     *
     * @param accountid the value for weixin_expandconfig.ACCOUNTID
     *
     * @mbg.generated Sat Mar 04 11:19:51 CST 2017
     */
    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column weixin_expandconfig.CLASSNAME
     *
     * @return the value of weixin_expandconfig.CLASSNAME
     *
     * @mbg.generated Sat Mar 04 11:19:51 CST 2017
     */
    public String getClassname() {
        return classname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column weixin_expandconfig.CLASSNAME
     *
     * @param classname the value for weixin_expandconfig.CLASSNAME
     *
     * @mbg.generated Sat Mar 04 11:19:51 CST 2017
     */
    public void setClassname(String classname) {
        this.classname = classname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column weixin_expandconfig.KEYWORD
     *
     * @return the value of weixin_expandconfig.KEYWORD
     *
     * @mbg.generated Sat Mar 04 11:19:51 CST 2017
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column weixin_expandconfig.KEYWORD
     *
     * @param keyword the value for weixin_expandconfig.KEYWORD
     *
     * @mbg.generated Sat Mar 04 11:19:51 CST 2017
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column weixin_expandconfig.NAME
     *
     * @return the value of weixin_expandconfig.NAME
     *
     * @mbg.generated Sat Mar 04 11:19:51 CST 2017
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column weixin_expandconfig.NAME
     *
     * @param name the value for weixin_expandconfig.NAME
     *
     * @mbg.generated Sat Mar 04 11:19:51 CST 2017
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column weixin_expandconfig.CONTENT
     *
     * @return the value of weixin_expandconfig.CONTENT
     *
     * @mbg.generated Sat Mar 04 11:19:51 CST 2017
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column weixin_expandconfig.CONTENT
     *
     * @param content the value for weixin_expandconfig.CONTENT
     *
     * @mbg.generated Sat Mar 04 11:19:51 CST 2017
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weixin_expandconfig
     *
     * @mbg.generated Sat Mar 04 11:19:51 CST 2017
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", accountid=").append(accountid);
        sb.append(", classname=").append(classname);
        sb.append(", keyword=").append(keyword);
        sb.append(", name=").append(name);
        sb.append(", content=").append(content);
        sb.append("]");
        return sb.toString();
    }
}