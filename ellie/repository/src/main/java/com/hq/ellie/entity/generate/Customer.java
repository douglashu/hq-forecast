package com.hq.ellie.entity.generate;

import java.io.Serializable;
import java.util.Date;

public class Customer extends CustomerKey implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.plat_type
     *
     * @mbg.generated Tue Feb 21 01:23:58 CST 2017
     */
    private Integer plat_type;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.nickname
     *
     * @mbg.generated Tue Feb 21 01:23:58 CST 2017
     */
    private String nickname;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.profile_photo
     *
     * @mbg.generated Tue Feb 21 01:23:58 CST 2017
     */
    private String profile_photo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.sex
     *
     * @mbg.generated Tue Feb 21 01:23:58 CST 2017
     */
    private String sex;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.visit_num
     *
     * @mbg.generated Tue Feb 21 01:23:58 CST 2017
     */
    private Integer visit_num;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.lastvisit_time
     *
     * @mbg.generated Tue Feb 21 01:23:58 CST 2017
     */
    private Date lastvisit_time;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table customer
     *
     * @mbg.generated Tue Feb 21 01:23:58 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.plat_type
     *
     * @return the value of customer.plat_type
     *
     * @mbg.generated Tue Feb 21 01:23:58 CST 2017
     */
    public Integer getPlat_type() {
        return plat_type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.plat_type
     *
     * @param plat_type the value for customer.plat_type
     *
     * @mbg.generated Tue Feb 21 01:23:58 CST 2017
     */
    public void setPlat_type(Integer plat_type) {
        this.plat_type = plat_type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.nickname
     *
     * @return the value of customer.nickname
     *
     * @mbg.generated Tue Feb 21 01:23:58 CST 2017
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.nickname
     *
     * @param nickname the value for customer.nickname
     *
     * @mbg.generated Tue Feb 21 01:23:58 CST 2017
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.profile_photo
     *
     * @return the value of customer.profile_photo
     *
     * @mbg.generated Tue Feb 21 01:23:58 CST 2017
     */
    public String getProfile_photo() {
        return profile_photo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.profile_photo
     *
     * @param profile_photo the value for customer.profile_photo
     *
     * @mbg.generated Tue Feb 21 01:23:58 CST 2017
     */
    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.sex
     *
     * @return the value of customer.sex
     *
     * @mbg.generated Tue Feb 21 01:23:58 CST 2017
     */
    public String getSex() {
        return sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.sex
     *
     * @param sex the value for customer.sex
     *
     * @mbg.generated Tue Feb 21 01:23:58 CST 2017
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.visit_num
     *
     * @return the value of customer.visit_num
     *
     * @mbg.generated Tue Feb 21 01:23:58 CST 2017
     */
    public Integer getVisit_num() {
        return visit_num;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.visit_num
     *
     * @param visit_num the value for customer.visit_num
     *
     * @mbg.generated Tue Feb 21 01:23:58 CST 2017
     */
    public void setVisit_num(Integer visit_num) {
        this.visit_num = visit_num;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.lastvisit_time
     *
     * @return the value of customer.lastvisit_time
     *
     * @mbg.generated Tue Feb 21 01:23:58 CST 2017
     */
    public Date getLastvisit_time() {
        return lastvisit_time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.lastvisit_time
     *
     * @param lastvisit_time the value for customer.lastvisit_time
     *
     * @mbg.generated Tue Feb 21 01:23:58 CST 2017
     */
    public void setLastvisit_time(Date lastvisit_time) {
        this.lastvisit_time = lastvisit_time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer
     *
     * @mbg.generated Tue Feb 21 01:23:58 CST 2017
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", plat_type=").append(plat_type);
        sb.append(", nickname=").append(nickname);
        sb.append(", profile_photo=").append(profile_photo);
        sb.append(", sex=").append(sex);
        sb.append(", visit_num=").append(visit_num);
        sb.append(", lastvisit_time=").append(lastvisit_time);
        sb.append("]");
        return sb.toString();
    }
}