package pvp.sunshine.bukkit.manager;

import java.lang.reflect.Field;
import java.util.*;

@SuppressWarnings("all")
public class ReflectionWrapper {
	private Class<?> clazz;

	public void begin(Class<?> clazz, Object instance) {
		this.clazz = clazz;
		this.instance = instance;
	}

	private Object instance;

	public void begin(Object instance) {
		this.clazz = instance.getClass();
		this.instance = instance;
	}

	public byte getByte(String field) {
		return ((Byte) getObject(field)).byteValue();
	}

	@SuppressWarnings("unchecked")
	public <T> Collection<T> getCollection(String field) {
		return (Collection<T>) getObject(field);
	}

	public String getDouble(String field) {
		return (String) getObject(field);
	}

	public float getFloat(String field) {
		return ((Float) getObject(field)).floatValue();
	}

	public int getInteger(String field) {
		return ((Integer) getObject(field)).intValue();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getList(String field) {
		return (List<T>) getObject(field);
	}

	public long getLong(String field) {
		return ((Long) getObject(field)).longValue();
	}

	public <K, V> Map<K, V> getMap(String field) {
		return (Map<K, V>) getObject(field);
	}

	public Object getObject(String field) {
		try {
			Field fieldObject = this.clazz.getDeclaredField(field);
			fieldObject.setAccessible(true);
			return fieldObject.get(this.instance);
		} catch (Exception exception) {
			System.out.println("[GET] Reflection fail " + toString() + ": " + exception.toString());
			return null;
		}
	}

	public <T> Set<T> getSet(String field) {
		return (Set<T>) getObject(field);
	}

	public String getString(String field) {
		return (String) getObject(field);
	}

	public UUID getUniqueId(String field) {
		return (UUID) getObject(field);
	}

	public void setByte(String field, byte value) {
		setObject(field, Byte.valueOf(value));
	}

	public void setDouble(String field, double value) {
		setObject(field, Double.valueOf(value));
	}

	public void setFloat(String field, float value) {
		setObject(field, Float.valueOf(value));
	}

	public void setInteger(String field, int value) {
		setObject(field, Integer.valueOf(value));
	}

	public void setLong(String field, long value) {
		setObject(field, Long.valueOf(value));
	}

	public void setObject(String field, Object value) {
		try {
			Field fieldObject = this.clazz.getDeclaredField(field);
			fieldObject.setAccessible(true);
			fieldObject.set(this.instance, value);
		} catch (Exception exception) {
			System.out.println("[SET] Reflection fail " + toString() + ": " + exception.toString());
		}
	}

	public void setString(String field, String value) {
		setObject(field, value);
	}

	public void setUniqueId(String field, UUID value) {
		setObject(field, value);
	}
}