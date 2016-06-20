package ar.uba.fi.hemobilling.commons.dozer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;

@Component("dozerMapper")
public class DozerMapper {

	@Resource(name = "org.dozer.Mapper")
	private DozerBeanMapper mapper;

	public <T> T map(Object map, Class<T> clazz) {

		if (null == map) {
			return null;
		}

		return (T) mapper.map(map, clazz);
	}

	public <T, E> Collection<T> map(Collection<E> map, Class<T> clazz) {

		List<T> result = new ArrayList<T>();

		for (Iterator<E> iterator = map.iterator(); iterator.hasNext();) {
			E e = (E) iterator.next();

			result.add(map(e, clazz));
		}

		return result;
	}
}