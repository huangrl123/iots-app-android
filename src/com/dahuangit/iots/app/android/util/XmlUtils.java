package com.dahuangit.iots.app.android.util;

import java.io.StringReader;
import java.io.StringWriter;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

public class XmlUtils {

	public static String obj2xml(Mapping mapping, Object obj) throws Exception {

		Marshaller marshaller = new Marshaller();
		marshaller.setMapping(mapping);
		StringWriter sw = new StringWriter();
		marshaller.marshal(obj, sw);

		return sw.toString();
	}

	public static <T> T xml2obj(Mapping mapping, String xml, Class<T> clazz) throws Exception {
		Unmarshaller unmarshaller = new Unmarshaller(clazz);
		unmarshaller.setMapping(mapping);

		Object obj = unmarshaller.unmarshal(new StringReader(xml));

		return (T) obj;
	}
}
