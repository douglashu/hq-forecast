package com.hq.elva.entity.generate;

import java.util.ArrayList;
import java.util.List;

public class TBasAlimccExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TBasAlimccExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
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

        public Criteria andMccCdIsNull() {
            addCriterion("MCC_CD is null");
            return (Criteria) this;
        }

        public Criteria andMccCdIsNotNull() {
            addCriterion("MCC_CD is not null");
            return (Criteria) this;
        }

        public Criteria andMccCdEqualTo(String value) {
            addCriterion("MCC_CD =", value, "mccCd");
            return (Criteria) this;
        }

        public Criteria andMccCdNotEqualTo(String value) {
            addCriterion("MCC_CD <>", value, "mccCd");
            return (Criteria) this;
        }

        public Criteria andMccCdGreaterThan(String value) {
            addCriterion("MCC_CD >", value, "mccCd");
            return (Criteria) this;
        }

        public Criteria andMccCdGreaterThanOrEqualTo(String value) {
            addCriterion("MCC_CD >=", value, "mccCd");
            return (Criteria) this;
        }

        public Criteria andMccCdLessThan(String value) {
            addCriterion("MCC_CD <", value, "mccCd");
            return (Criteria) this;
        }

        public Criteria andMccCdLessThanOrEqualTo(String value) {
            addCriterion("MCC_CD <=", value, "mccCd");
            return (Criteria) this;
        }

        public Criteria andMccCdLike(String value) {
            addCriterion("MCC_CD like", value, "mccCd");
            return (Criteria) this;
        }

        public Criteria andMccCdNotLike(String value) {
            addCriterion("MCC_CD not like", value, "mccCd");
            return (Criteria) this;
        }

        public Criteria andMccCdIn(List<String> values) {
            addCriterion("MCC_CD in", values, "mccCd");
            return (Criteria) this;
        }

        public Criteria andMccCdNotIn(List<String> values) {
            addCriterion("MCC_CD not in", values, "mccCd");
            return (Criteria) this;
        }

        public Criteria andMccCdBetween(String value1, String value2) {
            addCriterion("MCC_CD between", value1, value2, "mccCd");
            return (Criteria) this;
        }

        public Criteria andMccCdNotBetween(String value1, String value2) {
            addCriterion("MCC_CD not between", value1, value2, "mccCd");
            return (Criteria) this;
        }

        public Criteria andPMccCdIsNull() {
            addCriterion("P_MCC_CD is null");
            return (Criteria) this;
        }

        public Criteria andPMccCdIsNotNull() {
            addCriterion("P_MCC_CD is not null");
            return (Criteria) this;
        }

        public Criteria andPMccCdEqualTo(String value) {
            addCriterion("P_MCC_CD =", value, "pMccCd");
            return (Criteria) this;
        }

        public Criteria andPMccCdNotEqualTo(String value) {
            addCriterion("P_MCC_CD <>", value, "pMccCd");
            return (Criteria) this;
        }

        public Criteria andPMccCdGreaterThan(String value) {
            addCriterion("P_MCC_CD >", value, "pMccCd");
            return (Criteria) this;
        }

        public Criteria andPMccCdGreaterThanOrEqualTo(String value) {
            addCriterion("P_MCC_CD >=", value, "pMccCd");
            return (Criteria) this;
        }

        public Criteria andPMccCdLessThan(String value) {
            addCriterion("P_MCC_CD <", value, "pMccCd");
            return (Criteria) this;
        }

        public Criteria andPMccCdLessThanOrEqualTo(String value) {
            addCriterion("P_MCC_CD <=", value, "pMccCd");
            return (Criteria) this;
        }

        public Criteria andPMccCdLike(String value) {
            addCriterion("P_MCC_CD like", value, "pMccCd");
            return (Criteria) this;
        }

        public Criteria andPMccCdNotLike(String value) {
            addCriterion("P_MCC_CD not like", value, "pMccCd");
            return (Criteria) this;
        }

        public Criteria andPMccCdIn(List<String> values) {
            addCriterion("P_MCC_CD in", values, "pMccCd");
            return (Criteria) this;
        }

        public Criteria andPMccCdNotIn(List<String> values) {
            addCriterion("P_MCC_CD not in", values, "pMccCd");
            return (Criteria) this;
        }

        public Criteria andPMccCdBetween(String value1, String value2) {
            addCriterion("P_MCC_CD between", value1, value2, "pMccCd");
            return (Criteria) this;
        }

        public Criteria andPMccCdNotBetween(String value1, String value2) {
            addCriterion("P_MCC_CD not between", value1, value2, "pMccCd");
            return (Criteria) this;
        }

        public Criteria andBusClsIsNull() {
            addCriterion("BUS_CLS is null");
            return (Criteria) this;
        }

        public Criteria andBusClsIsNotNull() {
            addCriterion("BUS_CLS is not null");
            return (Criteria) this;
        }

        public Criteria andBusClsEqualTo(String value) {
            addCriterion("BUS_CLS =", value, "busCls");
            return (Criteria) this;
        }

        public Criteria andBusClsNotEqualTo(String value) {
            addCriterion("BUS_CLS <>", value, "busCls");
            return (Criteria) this;
        }

        public Criteria andBusClsGreaterThan(String value) {
            addCriterion("BUS_CLS >", value, "busCls");
            return (Criteria) this;
        }

        public Criteria andBusClsGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_CLS >=", value, "busCls");
            return (Criteria) this;
        }

        public Criteria andBusClsLessThan(String value) {
            addCriterion("BUS_CLS <", value, "busCls");
            return (Criteria) this;
        }

        public Criteria andBusClsLessThanOrEqualTo(String value) {
            addCriterion("BUS_CLS <=", value, "busCls");
            return (Criteria) this;
        }

        public Criteria andBusClsLike(String value) {
            addCriterion("BUS_CLS like", value, "busCls");
            return (Criteria) this;
        }

        public Criteria andBusClsNotLike(String value) {
            addCriterion("BUS_CLS not like", value, "busCls");
            return (Criteria) this;
        }

        public Criteria andBusClsIn(List<String> values) {
            addCriterion("BUS_CLS in", values, "busCls");
            return (Criteria) this;
        }

        public Criteria andBusClsNotIn(List<String> values) {
            addCriterion("BUS_CLS not in", values, "busCls");
            return (Criteria) this;
        }

        public Criteria andBusClsBetween(String value1, String value2) {
            addCriterion("BUS_CLS between", value1, value2, "busCls");
            return (Criteria) this;
        }

        public Criteria andBusClsNotBetween(String value1, String value2) {
            addCriterion("BUS_CLS not between", value1, value2, "busCls");
            return (Criteria) this;
        }

        public Criteria andBusDescIsNull() {
            addCriterion("BUS_DESC is null");
            return (Criteria) this;
        }

        public Criteria andBusDescIsNotNull() {
            addCriterion("BUS_DESC is not null");
            return (Criteria) this;
        }

        public Criteria andBusDescEqualTo(String value) {
            addCriterion("BUS_DESC =", value, "busDesc");
            return (Criteria) this;
        }

        public Criteria andBusDescNotEqualTo(String value) {
            addCriterion("BUS_DESC <>", value, "busDesc");
            return (Criteria) this;
        }

        public Criteria andBusDescGreaterThan(String value) {
            addCriterion("BUS_DESC >", value, "busDesc");
            return (Criteria) this;
        }

        public Criteria andBusDescGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_DESC >=", value, "busDesc");
            return (Criteria) this;
        }

        public Criteria andBusDescLessThan(String value) {
            addCriterion("BUS_DESC <", value, "busDesc");
            return (Criteria) this;
        }

        public Criteria andBusDescLessThanOrEqualTo(String value) {
            addCriterion("BUS_DESC <=", value, "busDesc");
            return (Criteria) this;
        }

        public Criteria andBusDescLike(String value) {
            addCriterion("BUS_DESC like", value, "busDesc");
            return (Criteria) this;
        }

        public Criteria andBusDescNotLike(String value) {
            addCriterion("BUS_DESC not like", value, "busDesc");
            return (Criteria) this;
        }

        public Criteria andBusDescIn(List<String> values) {
            addCriterion("BUS_DESC in", values, "busDesc");
            return (Criteria) this;
        }

        public Criteria andBusDescNotIn(List<String> values) {
            addCriterion("BUS_DESC not in", values, "busDesc");
            return (Criteria) this;
        }

        public Criteria andBusDescBetween(String value1, String value2) {
            addCriterion("BUS_DESC between", value1, value2, "busDesc");
            return (Criteria) this;
        }

        public Criteria andBusDescNotBetween(String value1, String value2) {
            addCriterion("BUS_DESC not between", value1, value2, "busDesc");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

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

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}