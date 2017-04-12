package com.hq.elva.entity.generate;

import java.util.ArrayList;
import java.util.List;

public class TBasBkinExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_bas_bkin
     *
     * @mbggenerated Thu Nov 03 09:37:18 CST 2016
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_bas_bkin
     *
     * @mbggenerated Thu Nov 03 09:37:18 CST 2016
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_bas_bkin
     *
     * @mbggenerated Thu Nov 03 09:37:18 CST 2016
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bas_bkin
     *
     * @mbggenerated Thu Nov 03 09:37:18 CST 2016
     */
    public TBasBkinExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bas_bkin
     *
     * @mbggenerated Thu Nov 03 09:37:18 CST 2016
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bas_bkin
     *
     * @mbggenerated Thu Nov 03 09:37:18 CST 2016
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bas_bkin
     *
     * @mbggenerated Thu Nov 03 09:37:18 CST 2016
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bas_bkin
     *
     * @mbggenerated Thu Nov 03 09:37:18 CST 2016
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bas_bkin
     *
     * @mbggenerated Thu Nov 03 09:37:18 CST 2016
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bas_bkin
     *
     * @mbggenerated Thu Nov 03 09:37:18 CST 2016
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bas_bkin
     *
     * @mbggenerated Thu Nov 03 09:37:18 CST 2016
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bas_bkin
     *
     * @mbggenerated Thu Nov 03 09:37:18 CST 2016
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bas_bkin
     *
     * @mbggenerated Thu Nov 03 09:37:18 CST 2016
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_bas_bkin
     *
     * @mbggenerated Thu Nov 03 09:37:18 CST 2016
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_bas_bkin
     *
     * @mbggenerated Thu Nov 03 09:37:18 CST 2016
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andLbnkNoIsNull() {
            addCriterion("LBNK_NO is null");
            return (Criteria) this;
        }

        public Criteria andLbnkNoIsNotNull() {
            addCriterion("LBNK_NO is not null");
            return (Criteria) this;
        }

        public Criteria andLbnkNoEqualTo(String value) {
            addCriterion("LBNK_NO =", value, "lbnkNo");
            return (Criteria) this;
        }

        public Criteria andLbnkNoNotEqualTo(String value) {
            addCriterion("LBNK_NO <>", value, "lbnkNo");
            return (Criteria) this;
        }

        public Criteria andLbnkNoGreaterThan(String value) {
            addCriterion("LBNK_NO >", value, "lbnkNo");
            return (Criteria) this;
        }

        public Criteria andLbnkNoGreaterThanOrEqualTo(String value) {
            addCriterion("LBNK_NO >=", value, "lbnkNo");
            return (Criteria) this;
        }

        public Criteria andLbnkNoLessThan(String value) {
            addCriterion("LBNK_NO <", value, "lbnkNo");
            return (Criteria) this;
        }

        public Criteria andLbnkNoLessThanOrEqualTo(String value) {
            addCriterion("LBNK_NO <=", value, "lbnkNo");
            return (Criteria) this;
        }

        public Criteria andLbnkNoLike(String value) {
            addCriterion("LBNK_NO like", value, "lbnkNo");
            return (Criteria) this;
        }

        public Criteria andLbnkNoNotLike(String value) {
            addCriterion("LBNK_NO not like", value, "lbnkNo");
            return (Criteria) this;
        }

        public Criteria andLbnkNoIn(List<String> values) {
            addCriterion("LBNK_NO in", values, "lbnkNo");
            return (Criteria) this;
        }

        public Criteria andLbnkNoNotIn(List<String> values) {
            addCriterion("LBNK_NO not in", values, "lbnkNo");
            return (Criteria) this;
        }

        public Criteria andLbnkNoBetween(String value1, String value2) {
            addCriterion("LBNK_NO between", value1, value2, "lbnkNo");
            return (Criteria) this;
        }

        public Criteria andLbnkNoNotBetween(String value1, String value2) {
            addCriterion("LBNK_NO not between", value1, value2, "lbnkNo");
            return (Criteria) this;
        }

        public Criteria andLbnkCdIsNull() {
            addCriterion("LBNK_CD is null");
            return (Criteria) this;
        }

        public Criteria andLbnkCdIsNotNull() {
            addCriterion("LBNK_CD is not null");
            return (Criteria) this;
        }

        public Criteria andLbnkCdEqualTo(String value) {
            addCriterion("LBNK_CD =", value, "lbnkCd");
            return (Criteria) this;
        }

        public Criteria andLbnkCdNotEqualTo(String value) {
            addCriterion("LBNK_CD <>", value, "lbnkCd");
            return (Criteria) this;
        }

        public Criteria andLbnkCdGreaterThan(String value) {
            addCriterion("LBNK_CD >", value, "lbnkCd");
            return (Criteria) this;
        }

        public Criteria andLbnkCdGreaterThanOrEqualTo(String value) {
            addCriterion("LBNK_CD >=", value, "lbnkCd");
            return (Criteria) this;
        }

        public Criteria andLbnkCdLessThan(String value) {
            addCriterion("LBNK_CD <", value, "lbnkCd");
            return (Criteria) this;
        }

        public Criteria andLbnkCdLessThanOrEqualTo(String value) {
            addCriterion("LBNK_CD <=", value, "lbnkCd");
            return (Criteria) this;
        }

        public Criteria andLbnkCdLike(String value) {
            addCriterion("LBNK_CD like", value, "lbnkCd");
            return (Criteria) this;
        }

        public Criteria andLbnkCdNotLike(String value) {
            addCriterion("LBNK_CD not like", value, "lbnkCd");
            return (Criteria) this;
        }

        public Criteria andLbnkCdIn(List<String> values) {
            addCriterion("LBNK_CD in", values, "lbnkCd");
            return (Criteria) this;
        }

        public Criteria andLbnkCdNotIn(List<String> values) {
            addCriterion("LBNK_CD not in", values, "lbnkCd");
            return (Criteria) this;
        }

        public Criteria andLbnkCdBetween(String value1, String value2) {
            addCriterion("LBNK_CD between", value1, value2, "lbnkCd");
            return (Criteria) this;
        }

        public Criteria andLbnkCdNotBetween(String value1, String value2) {
            addCriterion("LBNK_CD not between", value1, value2, "lbnkCd");
            return (Criteria) this;
        }

        public Criteria andCorpOrgIsNull() {
            addCriterion("CORP_ORG is null");
            return (Criteria) this;
        }

        public Criteria andCorpOrgIsNotNull() {
            addCriterion("CORP_ORG is not null");
            return (Criteria) this;
        }

        public Criteria andCorpOrgEqualTo(String value) {
            addCriterion("CORP_ORG =", value, "corpOrg");
            return (Criteria) this;
        }

        public Criteria andCorpOrgNotEqualTo(String value) {
            addCriterion("CORP_ORG <>", value, "corpOrg");
            return (Criteria) this;
        }

        public Criteria andCorpOrgGreaterThan(String value) {
            addCriterion("CORP_ORG >", value, "corpOrg");
            return (Criteria) this;
        }

        public Criteria andCorpOrgGreaterThanOrEqualTo(String value) {
            addCriterion("CORP_ORG >=", value, "corpOrg");
            return (Criteria) this;
        }

        public Criteria andCorpOrgLessThan(String value) {
            addCriterion("CORP_ORG <", value, "corpOrg");
            return (Criteria) this;
        }

        public Criteria andCorpOrgLessThanOrEqualTo(String value) {
            addCriterion("CORP_ORG <=", value, "corpOrg");
            return (Criteria) this;
        }

        public Criteria andCorpOrgLike(String value) {
            addCriterion("CORP_ORG like", value, "corpOrg");
            return (Criteria) this;
        }

        public Criteria andCorpOrgNotLike(String value) {
            addCriterion("CORP_ORG not like", value, "corpOrg");
            return (Criteria) this;
        }

        public Criteria andCorpOrgIn(List<String> values) {
            addCriterion("CORP_ORG in", values, "corpOrg");
            return (Criteria) this;
        }

        public Criteria andCorpOrgNotIn(List<String> values) {
            addCriterion("CORP_ORG not in", values, "corpOrg");
            return (Criteria) this;
        }

        public Criteria andCorpOrgBetween(String value1, String value2) {
            addCriterion("CORP_ORG between", value1, value2, "corpOrg");
            return (Criteria) this;
        }

        public Criteria andCorpOrgNotBetween(String value1, String value2) {
            addCriterion("CORP_ORG not between", value1, value2, "corpOrg");
            return (Criteria) this;
        }

        public Criteria andAdmCityIsNull() {
            addCriterion("ADM_CITY is null");
            return (Criteria) this;
        }

        public Criteria andAdmCityIsNotNull() {
            addCriterion("ADM_CITY is not null");
            return (Criteria) this;
        }

        public Criteria andAdmCityEqualTo(String value) {
            addCriterion("ADM_CITY =", value, "admCity");
            return (Criteria) this;
        }

        public Criteria andAdmCityNotEqualTo(String value) {
            addCriterion("ADM_CITY <>", value, "admCity");
            return (Criteria) this;
        }

        public Criteria andAdmCityGreaterThan(String value) {
            addCriterion("ADM_CITY >", value, "admCity");
            return (Criteria) this;
        }

        public Criteria andAdmCityGreaterThanOrEqualTo(String value) {
            addCriterion("ADM_CITY >=", value, "admCity");
            return (Criteria) this;
        }

        public Criteria andAdmCityLessThan(String value) {
            addCriterion("ADM_CITY <", value, "admCity");
            return (Criteria) this;
        }

        public Criteria andAdmCityLessThanOrEqualTo(String value) {
            addCriterion("ADM_CITY <=", value, "admCity");
            return (Criteria) this;
        }

        public Criteria andAdmCityLike(String value) {
            addCriterion("ADM_CITY like", value, "admCity");
            return (Criteria) this;
        }

        public Criteria andAdmCityNotLike(String value) {
            addCriterion("ADM_CITY not like", value, "admCity");
            return (Criteria) this;
        }

        public Criteria andAdmCityIn(List<String> values) {
            addCriterion("ADM_CITY in", values, "admCity");
            return (Criteria) this;
        }

        public Criteria andAdmCityNotIn(List<String> values) {
            addCriterion("ADM_CITY not in", values, "admCity");
            return (Criteria) this;
        }

        public Criteria andAdmCityBetween(String value1, String value2) {
            addCriterion("ADM_CITY between", value1, value2, "admCity");
            return (Criteria) this;
        }

        public Criteria andAdmCityNotBetween(String value1, String value2) {
            addCriterion("ADM_CITY not between", value1, value2, "admCity");
            return (Criteria) this;
        }

        public Criteria andAdmProvIsNull() {
            addCriterion("ADM_PROV is null");
            return (Criteria) this;
        }

        public Criteria andAdmProvIsNotNull() {
            addCriterion("ADM_PROV is not null");
            return (Criteria) this;
        }

        public Criteria andAdmProvEqualTo(String value) {
            addCriterion("ADM_PROV =", value, "admProv");
            return (Criteria) this;
        }

        public Criteria andAdmProvNotEqualTo(String value) {
            addCriterion("ADM_PROV <>", value, "admProv");
            return (Criteria) this;
        }

        public Criteria andAdmProvGreaterThan(String value) {
            addCriterion("ADM_PROV >", value, "admProv");
            return (Criteria) this;
        }

        public Criteria andAdmProvGreaterThanOrEqualTo(String value) {
            addCriterion("ADM_PROV >=", value, "admProv");
            return (Criteria) this;
        }

        public Criteria andAdmProvLessThan(String value) {
            addCriterion("ADM_PROV <", value, "admProv");
            return (Criteria) this;
        }

        public Criteria andAdmProvLessThanOrEqualTo(String value) {
            addCriterion("ADM_PROV <=", value, "admProv");
            return (Criteria) this;
        }

        public Criteria andAdmProvLike(String value) {
            addCriterion("ADM_PROV like", value, "admProv");
            return (Criteria) this;
        }

        public Criteria andAdmProvNotLike(String value) {
            addCriterion("ADM_PROV not like", value, "admProv");
            return (Criteria) this;
        }

        public Criteria andAdmProvIn(List<String> values) {
            addCriterion("ADM_PROV in", values, "admProv");
            return (Criteria) this;
        }

        public Criteria andAdmProvNotIn(List<String> values) {
            addCriterion("ADM_PROV not in", values, "admProv");
            return (Criteria) this;
        }

        public Criteria andAdmProvBetween(String value1, String value2) {
            addCriterion("ADM_PROV between", value1, value2, "admProv");
            return (Criteria) this;
        }

        public Criteria andAdmProvNotBetween(String value1, String value2) {
            addCriterion("ADM_PROV not between", value1, value2, "admProv");
            return (Criteria) this;
        }

        public Criteria andAdmRgnIsNull() {
            addCriterion("ADM_RGN is null");
            return (Criteria) this;
        }

        public Criteria andAdmRgnIsNotNull() {
            addCriterion("ADM_RGN is not null");
            return (Criteria) this;
        }

        public Criteria andAdmRgnEqualTo(String value) {
            addCriterion("ADM_RGN =", value, "admRgn");
            return (Criteria) this;
        }

        public Criteria andAdmRgnNotEqualTo(String value) {
            addCriterion("ADM_RGN <>", value, "admRgn");
            return (Criteria) this;
        }

        public Criteria andAdmRgnGreaterThan(String value) {
            addCriterion("ADM_RGN >", value, "admRgn");
            return (Criteria) this;
        }

        public Criteria andAdmRgnGreaterThanOrEqualTo(String value) {
            addCriterion("ADM_RGN >=", value, "admRgn");
            return (Criteria) this;
        }

        public Criteria andAdmRgnLessThan(String value) {
            addCriterion("ADM_RGN <", value, "admRgn");
            return (Criteria) this;
        }

        public Criteria andAdmRgnLessThanOrEqualTo(String value) {
            addCriterion("ADM_RGN <=", value, "admRgn");
            return (Criteria) this;
        }

        public Criteria andAdmRgnLike(String value) {
            addCriterion("ADM_RGN like", value, "admRgn");
            return (Criteria) this;
        }

        public Criteria andAdmRgnNotLike(String value) {
            addCriterion("ADM_RGN not like", value, "admRgn");
            return (Criteria) this;
        }

        public Criteria andAdmRgnIn(List<String> values) {
            addCriterion("ADM_RGN in", values, "admRgn");
            return (Criteria) this;
        }

        public Criteria andAdmRgnNotIn(List<String> values) {
            addCriterion("ADM_RGN not in", values, "admRgn");
            return (Criteria) this;
        }

        public Criteria andAdmRgnBetween(String value1, String value2) {
            addCriterion("ADM_RGN between", value1, value2, "admRgn");
            return (Criteria) this;
        }

        public Criteria andAdmRgnNotBetween(String value1, String value2) {
            addCriterion("ADM_RGN not between", value1, value2, "admRgn");
            return (Criteria) this;
        }

        public Criteria andProvCdIsNull() {
            addCriterion("PROV_CD is null");
            return (Criteria) this;
        }

        public Criteria andProvCdIsNotNull() {
            addCriterion("PROV_CD is not null");
            return (Criteria) this;
        }

        public Criteria andProvCdEqualTo(String value) {
            addCriterion("PROV_CD =", value, "provCd");
            return (Criteria) this;
        }

        public Criteria andProvCdNotEqualTo(String value) {
            addCriterion("PROV_CD <>", value, "provCd");
            return (Criteria) this;
        }

        public Criteria andProvCdGreaterThan(String value) {
            addCriterion("PROV_CD >", value, "provCd");
            return (Criteria) this;
        }

        public Criteria andProvCdGreaterThanOrEqualTo(String value) {
            addCriterion("PROV_CD >=", value, "provCd");
            return (Criteria) this;
        }

        public Criteria andProvCdLessThan(String value) {
            addCriterion("PROV_CD <", value, "provCd");
            return (Criteria) this;
        }

        public Criteria andProvCdLessThanOrEqualTo(String value) {
            addCriterion("PROV_CD <=", value, "provCd");
            return (Criteria) this;
        }

        public Criteria andProvCdLike(String value) {
            addCriterion("PROV_CD like", value, "provCd");
            return (Criteria) this;
        }

        public Criteria andProvCdNotLike(String value) {
            addCriterion("PROV_CD not like", value, "provCd");
            return (Criteria) this;
        }

        public Criteria andProvCdIn(List<String> values) {
            addCriterion("PROV_CD in", values, "provCd");
            return (Criteria) this;
        }

        public Criteria andProvCdNotIn(List<String> values) {
            addCriterion("PROV_CD not in", values, "provCd");
            return (Criteria) this;
        }

        public Criteria andProvCdBetween(String value1, String value2) {
            addCriterion("PROV_CD between", value1, value2, "provCd");
            return (Criteria) this;
        }

        public Criteria andProvCdNotBetween(String value1, String value2) {
            addCriterion("PROV_CD not between", value1, value2, "provCd");
            return (Criteria) this;
        }

        public Criteria andCityCdIsNull() {
            addCriterion("CITY_CD is null");
            return (Criteria) this;
        }

        public Criteria andCityCdIsNotNull() {
            addCriterion("CITY_CD is not null");
            return (Criteria) this;
        }

        public Criteria andCityCdEqualTo(String value) {
            addCriterion("CITY_CD =", value, "cityCd");
            return (Criteria) this;
        }

        public Criteria andCityCdNotEqualTo(String value) {
            addCriterion("CITY_CD <>", value, "cityCd");
            return (Criteria) this;
        }

        public Criteria andCityCdGreaterThan(String value) {
            addCriterion("CITY_CD >", value, "cityCd");
            return (Criteria) this;
        }

        public Criteria andCityCdGreaterThanOrEqualTo(String value) {
            addCriterion("CITY_CD >=", value, "cityCd");
            return (Criteria) this;
        }

        public Criteria andCityCdLessThan(String value) {
            addCriterion("CITY_CD <", value, "cityCd");
            return (Criteria) this;
        }

        public Criteria andCityCdLessThanOrEqualTo(String value) {
            addCriterion("CITY_CD <=", value, "cityCd");
            return (Criteria) this;
        }

        public Criteria andCityCdLike(String value) {
            addCriterion("CITY_CD like", value, "cityCd");
            return (Criteria) this;
        }

        public Criteria andCityCdNotLike(String value) {
            addCriterion("CITY_CD not like", value, "cityCd");
            return (Criteria) this;
        }

        public Criteria andCityCdIn(List<String> values) {
            addCriterion("CITY_CD in", values, "cityCd");
            return (Criteria) this;
        }

        public Criteria andCityCdNotIn(List<String> values) {
            addCriterion("CITY_CD not in", values, "cityCd");
            return (Criteria) this;
        }

        public Criteria andCityCdBetween(String value1, String value2) {
            addCriterion("CITY_CD between", value1, value2, "cityCd");
            return (Criteria) this;
        }

        public Criteria andCityCdNotBetween(String value1, String value2) {
            addCriterion("CITY_CD not between", value1, value2, "cityCd");
            return (Criteria) this;
        }

        public Criteria andTmSmpIsNull() {
            addCriterion("TM_SMP is null");
            return (Criteria) this;
        }

        public Criteria andTmSmpIsNotNull() {
            addCriterion("TM_SMP is not null");
            return (Criteria) this;
        }

        public Criteria andTmSmpEqualTo(String value) {
            addCriterion("TM_SMP =", value, "tmSmp");
            return (Criteria) this;
        }

        public Criteria andTmSmpNotEqualTo(String value) {
            addCriterion("TM_SMP <>", value, "tmSmp");
            return (Criteria) this;
        }

        public Criteria andTmSmpGreaterThan(String value) {
            addCriterion("TM_SMP >", value, "tmSmp");
            return (Criteria) this;
        }

        public Criteria andTmSmpGreaterThanOrEqualTo(String value) {
            addCriterion("TM_SMP >=", value, "tmSmp");
            return (Criteria) this;
        }

        public Criteria andTmSmpLessThan(String value) {
            addCriterion("TM_SMP <", value, "tmSmp");
            return (Criteria) this;
        }

        public Criteria andTmSmpLessThanOrEqualTo(String value) {
            addCriterion("TM_SMP <=", value, "tmSmp");
            return (Criteria) this;
        }

        public Criteria andTmSmpLike(String value) {
            addCriterion("TM_SMP like", value, "tmSmp");
            return (Criteria) this;
        }

        public Criteria andTmSmpNotLike(String value) {
            addCriterion("TM_SMP not like", value, "tmSmp");
            return (Criteria) this;
        }

        public Criteria andTmSmpIn(List<String> values) {
            addCriterion("TM_SMP in", values, "tmSmp");
            return (Criteria) this;
        }

        public Criteria andTmSmpNotIn(List<String> values) {
            addCriterion("TM_SMP not in", values, "tmSmp");
            return (Criteria) this;
        }

        public Criteria andTmSmpBetween(String value1, String value2) {
            addCriterion("TM_SMP between", value1, value2, "tmSmp");
            return (Criteria) this;
        }

        public Criteria andTmSmpNotBetween(String value1, String value2) {
            addCriterion("TM_SMP not between", value1, value2, "tmSmp");
            return (Criteria) this;
        }

        public Criteria andNodIdIsNull() {
            addCriterion("NOD_ID is null");
            return (Criteria) this;
        }

        public Criteria andNodIdIsNotNull() {
            addCriterion("NOD_ID is not null");
            return (Criteria) this;
        }

        public Criteria andNodIdEqualTo(String value) {
            addCriterion("NOD_ID =", value, "nodId");
            return (Criteria) this;
        }

        public Criteria andNodIdNotEqualTo(String value) {
            addCriterion("NOD_ID <>", value, "nodId");
            return (Criteria) this;
        }

        public Criteria andNodIdGreaterThan(String value) {
            addCriterion("NOD_ID >", value, "nodId");
            return (Criteria) this;
        }

        public Criteria andNodIdGreaterThanOrEqualTo(String value) {
            addCriterion("NOD_ID >=", value, "nodId");
            return (Criteria) this;
        }

        public Criteria andNodIdLessThan(String value) {
            addCriterion("NOD_ID <", value, "nodId");
            return (Criteria) this;
        }

        public Criteria andNodIdLessThanOrEqualTo(String value) {
            addCriterion("NOD_ID <=", value, "nodId");
            return (Criteria) this;
        }

        public Criteria andNodIdLike(String value) {
            addCriterion("NOD_ID like", value, "nodId");
            return (Criteria) this;
        }

        public Criteria andNodIdNotLike(String value) {
            addCriterion("NOD_ID not like", value, "nodId");
            return (Criteria) this;
        }

        public Criteria andNodIdIn(List<String> values) {
            addCriterion("NOD_ID in", values, "nodId");
            return (Criteria) this;
        }

        public Criteria andNodIdNotIn(List<String> values) {
            addCriterion("NOD_ID not in", values, "nodId");
            return (Criteria) this;
        }

        public Criteria andNodIdBetween(String value1, String value2) {
            addCriterion("NOD_ID between", value1, value2, "nodId");
            return (Criteria) this;
        }

        public Criteria andNodIdNotBetween(String value1, String value2) {
            addCriterion("NOD_ID not between", value1, value2, "nodId");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_bas_bkin
     *
     * @mbggenerated do_not_delete_during_merge Thu Nov 03 09:37:18 CST 2016
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_bas_bkin
     *
     * @mbggenerated Thu Nov 03 09:37:18 CST 2016
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value) {
            super();
            this.condition = condition;
            this.value = value;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.betweenValue = true;
        }
    }
}