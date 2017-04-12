package com.hq.flood.entity.generate;

import java.io.Serializable;

public class TFloodRoleTemplate implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_flood_role_template.id
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_flood_role_template.template_name
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    private String templateName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_flood_role_template.sequence
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    private Integer sequence;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_flood_role_template.state
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    private Byte state;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_flood_role_template.describes
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    private String describes;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_flood_role_template
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_flood_role_template.id
     *
     * @return the value of t_flood_role_template.id
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_flood_role_template.id
     *
     * @param id the value for t_flood_role_template.id
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_flood_role_template.template_name
     *
     * @return the value of t_flood_role_template.template_name
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_flood_role_template.template_name
     *
     * @param templateName the value for t_flood_role_template.template_name
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_flood_role_template.sequence
     *
     * @return the value of t_flood_role_template.sequence
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    public Integer getSequence() {
        return sequence;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_flood_role_template.sequence
     *
     * @param sequence the value for t_flood_role_template.sequence
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_flood_role_template.state
     *
     * @return the value of t_flood_role_template.state
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    public Byte getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_flood_role_template.state
     *
     * @param state the value for t_flood_role_template.state
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    public void setState(Byte state) {
        this.state = state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_flood_role_template.describes
     *
     * @return the value of t_flood_role_template.describes
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    public String getDescribes() {
        return describes;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_flood_role_template.describes
     *
     * @param describes the value for t_flood_role_template.describes
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    public void setDescribes(String describes) {
        this.describes = describes;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_flood_role_template
     *
     * @mbg.generated Wed Feb 15 23:13:34 CST 2017
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", templateName=").append(templateName);
        sb.append(", sequence=").append(sequence);
        sb.append(", state=").append(state);
        sb.append(", describes=").append(describes);
        sb.append("]");
        return sb.toString();
    }
}