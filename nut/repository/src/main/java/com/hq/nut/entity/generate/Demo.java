package com.hq.nut.entity.generate;

import java.io.Serializable;

public class Demo implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column demo.id
     *
     * @mbggenerated Fri Oct 21 14:58:47 CST 2016
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column demo.name
     *
     * @mbggenerated Fri Oct 21 14:58:47 CST 2016
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table demo
     *
     * @mbggenerated Fri Oct 21 14:58:47 CST 2016
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column demo.id
     *
     * @return the value of demo.id
     *
     * @mbggenerated Fri Oct 21 14:58:47 CST 2016
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column demo.id
     *
     * @param id the value for demo.id
     *
     * @mbggenerated Fri Oct 21 14:58:47 CST 2016
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column demo.name
     *
     * @return the value of demo.name
     *
     * @mbggenerated Fri Oct 21 14:58:47 CST 2016
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column demo.name
     *
     * @param name the value for demo.name
     *
     * @mbggenerated Fri Oct 21 14:58:47 CST 2016
     */
    public void setName(String name) {
        this.name = name;
    }
}