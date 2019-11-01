package io.frictionlessdata.tableschema;

import java.util.HashMap;
import java.util.Map;

public enum FieldType {
    FIELD_TYPE_STRING	("string"),
    FIELD_TYPE_INTEGER	("integer"),
    FIELD_TYPE_NUMBER	("number"),
    FIELD_TYPE_BOOLEAN	("boolean"),
    FIELD_TYPE_OBJECT	("object"),
    FIELD_TYPE_ARRAY	("array"),
    FIELD_TYPE_DATE		("date"),
    FIELD_TYPE_TIME		("time"),
    FIELD_TYPE_DATETIME	("datetime"),
    FIELD_TYPE_YEAR		("year"),
    FIELD_TYPE_YEARMONTH("yearmonth"),
    FIELD_TYPE_DURATION	("duration"),
    FIELD_TYPE_GEOPOINT	("geopoint"),
    FIELD_TYPE_GEOJSON	("geojson"),
    FIELD_TYPE_ANY		("any"),
    FIELD_TYPE_NONE		("");

    private static final Map<String, FieldType> dictionary = new HashMap<>();
    private final String label;

    private FieldType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static FieldType byName(String label) {
        return dictionary.get(label);
    }

    public static boolean isEmpty(FieldType type) {
        if ((null == type) || (type.equals(FIELD_TYPE_NONE))) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return getLabel();
    }
    /*
        Populate dictionary at load time
     */

    static {
        for(FieldType env : FieldType.values()) {
            dictionary.put(env.getLabel(), env);
        }
    }
}
