package com.hq.scrati.common.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ObjectUtils {
	
	public static String DEFAULT_ENCODING = "GBK";
	
	public static byte[] getBytes(Object o) throws UnsupportedEncodingException {
		byte[] byData = new byte[0];
		if (o instanceof byte[])
			byData = (byte[]) o;
		else if (o instanceof String) {
			byData = ((String) o).getBytes(DEFAULT_ENCODING);
		} else if (o instanceof Integer) {
			byData = ((Integer) o).toString().getBytes();
		} else if (o instanceof Long) {
			byData = ((Long) o).toString().getBytes();
		} else if (o instanceof Character) {
			byData = ((Character) o).toString().getBytes();
		} else if (o instanceof Boolean) {
			byData = ((Boolean) o).toString().getBytes();
		} else {
			byData = o.toString().getBytes();
		}
		return byData;
	}

	public static byte[] getBytes(Object o, String encoding) throws UnsupportedEncodingException {
		String charset = encoding;
		if(charset == null) {
			charset = DEFAULT_ENCODING;
		}
		byte[] byData = new byte[0];
		if (o instanceof byte[])
			byData = (byte[]) o;
		else if (o instanceof String) {
			byData = ((String) o).getBytes(charset);
		} else if (o instanceof Integer) {
			byData = ((Integer) o).toString().getBytes();
		} else if (o instanceof Long) {
			byData = ((Long) o).toString().getBytes();
		} else if (o instanceof Character) {
			byData = ((Character) o).toString().getBytes();
		} else if (o instanceof Boolean) {
			byData = ((Boolean) o).toString().getBytes();
		} else {
			byData = o.toString().getBytes();
		}
		return byData;
	}
	
	public static String getString(Object o) throws UnsupportedEncodingException {
		if (o == null)
			return null;
		if (o instanceof byte[]) {
			return new String((byte[]) o, DEFAULT_ENCODING);
		} else if (o instanceof String) {
			return (String) o;
		} else {
			return o.toString();
		}
	}

	public static String getString(Object o, String encoding) throws UnsupportedEncodingException {
		String charset = encoding;
		if(charset == null) {
			charset = DEFAULT_ENCODING;
		}				
		if (o == null)
			return null;
		if (o instanceof byte[]) {
			return new String((byte[]) o, charset);
		} else if (o instanceof String) {
			return (String) o;
		} else {
			return o.toString();
		}
	}
	
	public static int getInt(String s) {
		try {
			int n = Integer.parseInt(s);
			return n;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public static int getInt(String s, int defaultValue) {
		try {
			int n = Integer.parseInt(s);
			return n;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return defaultValue;
		}
	}

	public static long getLong(String s) {
		try {
			long n = Long.parseLong(s);
			return n;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public static long getLong(String s, int defaultValue) {
		try {
			long n = Long.parseLong(s);
			return n;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return defaultValue;
		}
	}

	public static Map<String, Class<?>> typeMap = new ConcurrentHashMap<String, Class<?>>();
	
	static {
		typeMap.put("byte", byte.class);
		typeMap.put("short", short.class);
		typeMap.put("int", int.class);
		typeMap.put("long", long.class);
		typeMap.put("char", char.class);
		typeMap.put("float", float.class);
		typeMap.put("double", double.class);
		typeMap.put("boolean", boolean.class);
		typeMap.put("string", String.class);
		
		typeMap.put("Byte", byte.class);
		typeMap.put("Short", short.class);
		typeMap.put("Int", int.class);
		typeMap.put("Long", long.class);
		typeMap.put("Char", char.class);
		typeMap.put("Float", float.class);
		typeMap.put("Double", double.class);
		typeMap.put("Boolean", boolean.class);
		typeMap.put("String", String.class);

		typeMap.put("java.lang.Byte", byte.class);
		typeMap.put("java.lang.Short", short.class);
		typeMap.put("java.lang.Integer", int.class);
		typeMap.put("java.lang.Long", long.class);
		typeMap.put("java.lang.Float", float.class);
		typeMap.put("java.lang.Double", double.class);
		typeMap.put("java.lang.Boolean", boolean.class);
		typeMap.put("java.lang.Character", char.class);
	}

	public static Class<?> getType(String type) {
		Class<?> clazz = typeMap.get(type);
		if(clazz == null) {
			try {
				return Class.forName(type);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return clazz;
	}
	
	public static String getType(Class<?> clazz) {
		if(clazz.equals(Byte.class)) return "byte";
		if(clazz.equals(Short.class)) return "short";
		if(clazz.equals(Integer.class)) return "int";
		if(clazz.equals(Long.class)) return "long";
		if(clazz.equals(Character.class)) return "char";
		if(clazz.equals(Float.class)) return "float";
		if(clazz.equals(Double.class)) return "double";
		if(clazz.equals(Boolean.class)) return "boolean";
		if(clazz.equals(String.class)) return "string";
		
		return clazz.getSimpleName().toLowerCase();
	}
	
	public static Object getValue(String type, String data) {
		
		if (type.equals("byte")) return Byte.parseByte(data);
		if (type.equals("short")) return Short.parseShort(data);
		if (type.equals("int")) return Integer.parseInt(data);
		if (type.equals("long")) return Long.parseLong(data);
		if (type.equals("char")) return data.charAt(0);
		if (type.equals("float")) return Float.parseFloat(data);
		if (type.equals("double")) return Double.parseDouble(data);
		if (type.equals("boolean")) return Boolean.parseBoolean(data);
		if (type.equals("string")) return data;
		
		if (type.equals("Byte")) return Byte.parseByte(data);
		if (type.equals("Short")) return Short.parseShort(data);
		if (type.equals("Int")) return Integer.parseInt(data);
		if (type.equals("Long")) return Long.parseLong(data);
		if (type.equals("char")) return data.charAt(0);
		if (type.equals("Float")) return Float.parseFloat(data);
		if (type.equals("Double")) return Double.parseDouble(data);
		if (type.equals("Boolean")) return Boolean.parseBoolean(data);
		if (type.equals("String")) return data;

		if (type.equals("java.lang.Byte")) return Byte.parseByte(data);
		if (type.equals("java.lang.Short")) return Short.parseShort(data);
		if (type.equals("java.lang.Integer")) return Integer.parseInt(data);
		if (type.equals("java.lang.Long")) return Long.parseLong(data);
		if (type.equals("java.lang.Character")) return data.charAt(0);
		if (type.equals("java.lang.Float")) return Float.parseFloat(data);
		if (type.equals("java.lang.Double")) return Double.parseDouble(data);
		if (type.equals("java.lang.Boolean")) return Boolean.parseBoolean(data);
		if (type.equals("java.lang.String")) return data;
		
		return null;		
	}

	public static Object getValue(Object obj) {
		if(obj instanceof Byte) return ((Byte)obj).byteValue();
		if(obj instanceof Short) return ((Short)obj).shortValue();
		if(obj instanceof Integer) return ((Integer)obj).intValue();
		if(obj instanceof Long) return ((Long)obj).longValue();
		if(obj instanceof Float) return ((Float)obj).floatValue();
		if(obj instanceof Double) return ((Double)obj).doubleValue();
		if(obj instanceof Boolean) return ((Boolean)obj).booleanValue();
		if(obj instanceof Character) return ((Character)obj).charValue();
		
		return obj;
	}
	
	public static Object getValue(Object obj, Class<?> type) {
		if(type.equals(obj.getClass())) return obj;
		if(type.equals(byte.class)) return Byte.parseByte(obj.toString());
		if(type.equals(short.class)) return Short.parseShort(obj.toString());
		if(type.equals(int.class)) return Integer.parseInt(obj.toString());
		if(type.equals(long.class)) return Long.parseLong(obj.toString());
		if(type.equals(char.class)) return obj.toString().charAt(0);
		if(type.equals(float.class)) return Float.parseFloat(obj.toString());
		if(type.equals(double.class)) return Double.parseDouble(obj.toString());
		if(type.equals(boolean.class)) return Boolean.parseBoolean(obj.toString());
		
		if(type.equals(Byte.class)) return Byte.parseByte(obj.toString());
		if(type.equals(Short.class)) return Short.parseShort(obj.toString());
		if(type.equals(Integer.class)) return Integer.parseInt(obj.toString());
		if(type.equals(Long.class)) return Long.parseLong(obj.toString());
		if(type.equals(Character.class)) return obj.toString().charAt(0);
		if(type.equals(Float.class)) return Float.parseFloat(obj.toString());
		if(type.equals(Double.class)) return Double.parseDouble(obj.toString());
		if(type.equals(Boolean.class)) return Boolean.parseBoolean(obj.toString());
		if(type.equals(String.class)){
			if(obj.getClass().equals(byte[].class)) {
				try {
					return new String((byte[])obj, DEFAULT_ENCODING);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			} else {
				return obj.toString();
			}
		}
		
		return obj;
	}

	public static Object getValue(byte[] obj, Class<?> type) {
		if(type.equals(obj.getClass())) return obj;
		String sObj = new String(obj);
		if(type.equals(byte.class)) return Byte.parseByte(sObj);
		if(type.equals(short.class)) return Short.parseShort(sObj);
		if(type.equals(int.class)) return Integer.parseInt(sObj);
		if(type.equals(long.class)) return Long.parseLong(sObj);
		if(type.equals(char.class)) return sObj.charAt(0);
		if(type.equals(float.class)) return Float.parseFloat(sObj);
		if(type.equals(double.class)) return Double.parseDouble(sObj);
		if(type.equals(boolean.class)) return Boolean.parseBoolean(sObj);
		
		if(type.equals(Byte.class)) return Byte.parseByte(sObj);
		if(type.equals(Short.class)) return Short.parseShort(sObj);
		if(type.equals(Integer.class)) return Integer.parseInt(sObj);
		if(type.equals(Long.class)) return Long.parseLong(sObj);
		if(type.equals(Character.class)) return sObj.charAt(0);
		if(type.equals(Float.class)) return Float.parseFloat(sObj);
		if(type.equals(Double.class)) return Double.parseDouble(sObj);
		if(type.equals(Boolean.class)) return Boolean.parseBoolean(sObj);
		if(type.equals(String.class)){
			if(obj.getClass().equals(byte[].class)) {
				try {
					return new String(obj, DEFAULT_ENCODING);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			} else {
				return obj.toString();
			}
		}
		
		return obj;
	}
	
	public static void main(String[] args) {
		byte[] buff = new byte[] {49};
		String s = new String(buff);
		System.out.println(s);
	}
}
