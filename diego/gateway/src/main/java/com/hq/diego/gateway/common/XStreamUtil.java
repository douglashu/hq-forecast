package com.hq.diego.gateway.common;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.apache.commons.lang.StringUtils;
import java.io.Writer;

/**
 * Created by zhaoyang on 09/10/2016.
 */
public class XStreamUtil {

    public static <T> T fromXml(String xmlStr, Class<T> clazz) {
        XStream xStream = new XStream(new XppDriver(new NoNameCoder()));
        xStream.setClassLoader(clazz.getClassLoader());
        xStream.processAnnotations(clazz);
        xStream.ignoreUnknownElements();
        return (T) xStream.fromXML(xmlStr);
    }

    public static String toXml(Object object) {
        XStream xStream = new XStream(new XppDriver() {

            @Override
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new PrettyPrintWriter(out) {
                    @Override
                    public String encodeNode(String name) {
                        return (new NoNameCoder()).encodeNode(name);
                    }

                    @Override
                    protected void writeText(QuickWriter writer, String text) {
                        if(StringUtils.isNotEmpty(text)) {
                            writer.write("<![CDATA[");
                            writer.write(text);
                            writer.write("]]>");
                        }
                    }
                };
            }

        });
        xStream.processAnnotations(object.getClass());
        return xStream.toXML(object);
    }

}
