package com.hq.elva.entity.generate;

import java.util.ArrayList;
import java.util.List;

public class TBasAreaExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TBasAreaExample() {
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

        public Criteria andAreaCodeIsNull() {
            addCriterion("area_code is null");
            return (Criteria) this;
        }

        public Criteria andAreaCodeIsNotNull() {
            addCriterion("area_code is not null");
            return (Criteria) this;
        }

        public Criteria andAreaCodeEqualTo(String value) {
            addCriterion("area_code =", value, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeNotEqualTo(String value) {
            addCriterion("area_code <>", value, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeGreaterThan(String value) {
            addCriterion("area_code >", value, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeGreaterThanOrEqualTo(String value) {
            addCriterion("area_code >=", value, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeLessThan(String value) {
            addCriterion("area_code <", value, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeLessThanOrEqualTo(String value) {
            addCriterion("area_code <=", value, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeLike(String value) {
            addCriterion("area_code like", value, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeNotLike(String value) {
            addCriterion("area_code not like", value, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeIn(List<String> values) {
            addCriterion("area_code in", values, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeNotIn(List<String> values) {
            addCriterion("area_code not in", values, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeBetween(String value1, String value2) {
            addCriterion("area_code between", value1, value2, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeNotBetween(String value1, String value2) {
            addCriterion("area_code not between", value1, value2, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaNameIsNull() {
            addCriterion("area_name is null");
            return (Criteria) this;
        }

        public Criteria andAreaNameIsNotNull() {
            addCriterion("area_name is not null");
            return (Criteria) this;
        }

        public Criteria andAreaNameEqualTo(String value) {
            addCriterion("area_name =", value, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameNotEqualTo(String value) {
            addCriterion("area_name <>", value, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameGreaterThan(String value) {
            addCriterion("area_name >", value, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameGreaterThanOrEqualTo(String value) {
            addCriterion("area_name >=", value, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameLessThan(String value) {
            addCriterion("area_name <", value, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameLessThanOrEqualTo(String value) {
            addCriterion("area_name <=", value, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameLike(String value) {
            addCriterion("area_name like", value, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameNotLike(String value) {
            addCriterion("area_name not like", value, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameIn(List<String> values) {
            addCriterion("area_name in", values, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameNotIn(List<String> values) {
            addCriterion("area_name not in", values, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameBetween(String value1, String value2) {
            addCriterion("area_name between", value1, value2, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameNotBetween(String value1, String value2) {
            addCriterion("area_name not between", value1, value2, "areaName");
            return (Criteria) this;
        }

        public Criteria andUpAreaCodeIsNull() {
            addCriterion("up_area_code is null");
            return (Criteria) this;
        }

        public Criteria andUpAreaCodeIsNotNull() {
            addCriterion("up_area_code is not null");
            return (Criteria) this;
        }

        public Criteria andUpAreaCodeEqualTo(String value) {
            addCriterion("up_area_code =", value, "upAreaCode");
            return (Criteria) this;
        }

        public Criteria andUpAreaCodeNotEqualTo(String value) {
            addCriterion("up_area_code <>", value, "upAreaCode");
            return (Criteria) this;
        }

        public Criteria andUpAreaCodeGreaterThan(String value) {
            addCriterion("up_area_code >", value, "upAreaCode");
            return (Criteria) this;
        }

        public Criteria andUpAreaCodeGreaterThanOrEqualTo(String value) {
            addCriterion("up_area_code >=", value, "upAreaCode");
            return (Criteria) this;
        }

        public Criteria andUpAreaCodeLessThan(String value) {
            addCriterion("up_area_code <", value, "upAreaCode");
            return (Criteria) this;
        }

        public Criteria andUpAreaCodeLessThanOrEqualTo(String value) {
            addCriterion("up_area_code <=", value, "upAreaCode");
            return (Criteria) this;
        }

        public Criteria andUpAreaCodeLike(String value) {
            addCriterion("up_area_code like", value, "upAreaCode");
            return (Criteria) this;
        }

        public Criteria andUpAreaCodeNotLike(String value) {
            addCriterion("up_area_code not like", value, "upAreaCode");
            return (Criteria) this;
        }

        public Criteria andUpAreaCodeIn(List<String> values) {
            addCriterion("up_area_code in", values, "upAreaCode");
            return (Criteria) this;
        }

        public Criteria andUpAreaCodeNotIn(List<String> values) {
            addCriterion("up_area_code not in", values, "upAreaCode");
            return (Criteria) this;
        }

        public Criteria andUpAreaCodeBetween(String value1, String value2) {
            addCriterion("up_area_code between", value1, value2, "upAreaCode");
            return (Criteria) this;
        }

        public Criteria andUpAreaCodeNotBetween(String value1, String value2) {
            addCriterion("up_area_code not between", value1, value2, "upAreaCode");
            return (Criteria) this;
        }

        public Criteria andAreaLevelIsNull() {
            addCriterion("area_level is null");
            return (Criteria) this;
        }

        public Criteria andAreaLevelIsNotNull() {
            addCriterion("area_level is not null");
            return (Criteria) this;
        }

        public Criteria andAreaLevelEqualTo(Integer value) {
            addCriterion("area_level =", value, "areaLevel");
            return (Criteria) this;
        }

        public Criteria andAreaLevelNotEqualTo(Integer value) {
            addCriterion("area_level <>", value, "areaLevel");
            return (Criteria) this;
        }

        public Criteria andAreaLevelGreaterThan(Integer value) {
            addCriterion("area_level >", value, "areaLevel");
            return (Criteria) this;
        }

        public Criteria andAreaLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("area_level >=", value, "areaLevel");
            return (Criteria) this;
        }

        public Criteria andAreaLevelLessThan(Integer value) {
            addCriterion("area_level <", value, "areaLevel");
            return (Criteria) this;
        }

        public Criteria andAreaLevelLessThanOrEqualTo(Integer value) {
            addCriterion("area_level <=", value, "areaLevel");
            return (Criteria) this;
        }

        public Criteria andAreaLevelIn(List<Integer> values) {
            addCriterion("area_level in", values, "areaLevel");
            return (Criteria) this;
        }

        public Criteria andAreaLevelNotIn(List<Integer> values) {
            addCriterion("area_level not in", values, "areaLevel");
            return (Criteria) this;
        }

        public Criteria andAreaLevelBetween(Integer value1, Integer value2) {
            addCriterion("area_level between", value1, value2, "areaLevel");
            return (Criteria) this;
        }

        public Criteria andAreaLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("area_level not between", value1, value2, "areaLevel");
            return (Criteria) this;
        }

        public Criteria andAreaPinyinIsNull() {
            addCriterion("area_pinyin is null");
            return (Criteria) this;
        }

        public Criteria andAreaPinyinIsNotNull() {
            addCriterion("area_pinyin is not null");
            return (Criteria) this;
        }

        public Criteria andAreaPinyinEqualTo(String value) {
            addCriterion("area_pinyin =", value, "areaPinyin");
            return (Criteria) this;
        }

        public Criteria andAreaPinyinNotEqualTo(String value) {
            addCriterion("area_pinyin <>", value, "areaPinyin");
            return (Criteria) this;
        }

        public Criteria andAreaPinyinGreaterThan(String value) {
            addCriterion("area_pinyin >", value, "areaPinyin");
            return (Criteria) this;
        }

        public Criteria andAreaPinyinGreaterThanOrEqualTo(String value) {
            addCriterion("area_pinyin >=", value, "areaPinyin");
            return (Criteria) this;
        }

        public Criteria andAreaPinyinLessThan(String value) {
            addCriterion("area_pinyin <", value, "areaPinyin");
            return (Criteria) this;
        }

        public Criteria andAreaPinyinLessThanOrEqualTo(String value) {
            addCriterion("area_pinyin <=", value, "areaPinyin");
            return (Criteria) this;
        }

        public Criteria andAreaPinyinLike(String value) {
            addCriterion("area_pinyin like", value, "areaPinyin");
            return (Criteria) this;
        }

        public Criteria andAreaPinyinNotLike(String value) {
            addCriterion("area_pinyin not like", value, "areaPinyin");
            return (Criteria) this;
        }

        public Criteria andAreaPinyinIn(List<String> values) {
            addCriterion("area_pinyin in", values, "areaPinyin");
            return (Criteria) this;
        }

        public Criteria andAreaPinyinNotIn(List<String> values) {
            addCriterion("area_pinyin not in", values, "areaPinyin");
            return (Criteria) this;
        }

        public Criteria andAreaPinyinBetween(String value1, String value2) {
            addCriterion("area_pinyin between", value1, value2, "areaPinyin");
            return (Criteria) this;
        }

        public Criteria andAreaPinyinNotBetween(String value1, String value2) {
            addCriterion("area_pinyin not between", value1, value2, "areaPinyin");
            return (Criteria) this;
        }

        public Criteria andAreaShortNameIsNull() {
            addCriterion("area_short_name is null");
            return (Criteria) this;
        }

        public Criteria andAreaShortNameIsNotNull() {
            addCriterion("area_short_name is not null");
            return (Criteria) this;
        }

        public Criteria andAreaShortNameEqualTo(String value) {
            addCriterion("area_short_name =", value, "areaShortName");
            return (Criteria) this;
        }

        public Criteria andAreaShortNameNotEqualTo(String value) {
            addCriterion("area_short_name <>", value, "areaShortName");
            return (Criteria) this;
        }

        public Criteria andAreaShortNameGreaterThan(String value) {
            addCriterion("area_short_name >", value, "areaShortName");
            return (Criteria) this;
        }

        public Criteria andAreaShortNameGreaterThanOrEqualTo(String value) {
            addCriterion("area_short_name >=", value, "areaShortName");
            return (Criteria) this;
        }

        public Criteria andAreaShortNameLessThan(String value) {
            addCriterion("area_short_name <", value, "areaShortName");
            return (Criteria) this;
        }

        public Criteria andAreaShortNameLessThanOrEqualTo(String value) {
            addCriterion("area_short_name <=", value, "areaShortName");
            return (Criteria) this;
        }

        public Criteria andAreaShortNameLike(String value) {
            addCriterion("area_short_name like", value, "areaShortName");
            return (Criteria) this;
        }

        public Criteria andAreaShortNameNotLike(String value) {
            addCriterion("area_short_name not like", value, "areaShortName");
            return (Criteria) this;
        }

        public Criteria andAreaShortNameIn(List<String> values) {
            addCriterion("area_short_name in", values, "areaShortName");
            return (Criteria) this;
        }

        public Criteria andAreaShortNameNotIn(List<String> values) {
            addCriterion("area_short_name not in", values, "areaShortName");
            return (Criteria) this;
        }

        public Criteria andAreaShortNameBetween(String value1, String value2) {
            addCriterion("area_short_name between", value1, value2, "areaShortName");
            return (Criteria) this;
        }

        public Criteria andAreaShortNameNotBetween(String value1, String value2) {
            addCriterion("area_short_name not between", value1, value2, "areaShortName");
            return (Criteria) this;
        }

        public Criteria andCenLongitudeIsNull() {
            addCriterion("cen_longitude is null");
            return (Criteria) this;
        }

        public Criteria andCenLongitudeIsNotNull() {
            addCriterion("cen_longitude is not null");
            return (Criteria) this;
        }

        public Criteria andCenLongitudeEqualTo(String value) {
            addCriterion("cen_longitude =", value, "cenLongitude");
            return (Criteria) this;
        }

        public Criteria andCenLongitudeNotEqualTo(String value) {
            addCriterion("cen_longitude <>", value, "cenLongitude");
            return (Criteria) this;
        }

        public Criteria andCenLongitudeGreaterThan(String value) {
            addCriterion("cen_longitude >", value, "cenLongitude");
            return (Criteria) this;
        }

        public Criteria andCenLongitudeGreaterThanOrEqualTo(String value) {
            addCriterion("cen_longitude >=", value, "cenLongitude");
            return (Criteria) this;
        }

        public Criteria andCenLongitudeLessThan(String value) {
            addCriterion("cen_longitude <", value, "cenLongitude");
            return (Criteria) this;
        }

        public Criteria andCenLongitudeLessThanOrEqualTo(String value) {
            addCriterion("cen_longitude <=", value, "cenLongitude");
            return (Criteria) this;
        }

        public Criteria andCenLongitudeLike(String value) {
            addCriterion("cen_longitude like", value, "cenLongitude");
            return (Criteria) this;
        }

        public Criteria andCenLongitudeNotLike(String value) {
            addCriterion("cen_longitude not like", value, "cenLongitude");
            return (Criteria) this;
        }

        public Criteria andCenLongitudeIn(List<String> values) {
            addCriterion("cen_longitude in", values, "cenLongitude");
            return (Criteria) this;
        }

        public Criteria andCenLongitudeNotIn(List<String> values) {
            addCriterion("cen_longitude not in", values, "cenLongitude");
            return (Criteria) this;
        }

        public Criteria andCenLongitudeBetween(String value1, String value2) {
            addCriterion("cen_longitude between", value1, value2, "cenLongitude");
            return (Criteria) this;
        }

        public Criteria andCenLongitudeNotBetween(String value1, String value2) {
            addCriterion("cen_longitude not between", value1, value2, "cenLongitude");
            return (Criteria) this;
        }

        public Criteria andCenLatitudeIsNull() {
            addCriterion("cen_latitude is null");
            return (Criteria) this;
        }

        public Criteria andCenLatitudeIsNotNull() {
            addCriterion("cen_latitude is not null");
            return (Criteria) this;
        }

        public Criteria andCenLatitudeEqualTo(String value) {
            addCriterion("cen_latitude =", value, "cenLatitude");
            return (Criteria) this;
        }

        public Criteria andCenLatitudeNotEqualTo(String value) {
            addCriterion("cen_latitude <>", value, "cenLatitude");
            return (Criteria) this;
        }

        public Criteria andCenLatitudeGreaterThan(String value) {
            addCriterion("cen_latitude >", value, "cenLatitude");
            return (Criteria) this;
        }

        public Criteria andCenLatitudeGreaterThanOrEqualTo(String value) {
            addCriterion("cen_latitude >=", value, "cenLatitude");
            return (Criteria) this;
        }

        public Criteria andCenLatitudeLessThan(String value) {
            addCriterion("cen_latitude <", value, "cenLatitude");
            return (Criteria) this;
        }

        public Criteria andCenLatitudeLessThanOrEqualTo(String value) {
            addCriterion("cen_latitude <=", value, "cenLatitude");
            return (Criteria) this;
        }

        public Criteria andCenLatitudeLike(String value) {
            addCriterion("cen_latitude like", value, "cenLatitude");
            return (Criteria) this;
        }

        public Criteria andCenLatitudeNotLike(String value) {
            addCriterion("cen_latitude not like", value, "cenLatitude");
            return (Criteria) this;
        }

        public Criteria andCenLatitudeIn(List<String> values) {
            addCriterion("cen_latitude in", values, "cenLatitude");
            return (Criteria) this;
        }

        public Criteria andCenLatitudeNotIn(List<String> values) {
            addCriterion("cen_latitude not in", values, "cenLatitude");
            return (Criteria) this;
        }

        public Criteria andCenLatitudeBetween(String value1, String value2) {
            addCriterion("cen_latitude between", value1, value2, "cenLatitude");
            return (Criteria) this;
        }

        public Criteria andCenLatitudeNotBetween(String value1, String value2) {
            addCriterion("cen_latitude not between", value1, value2, "cenLatitude");
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