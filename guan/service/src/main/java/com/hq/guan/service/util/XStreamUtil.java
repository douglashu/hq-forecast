package com.hq.guan.service.util;

import com.alibaba.fastjson.JSON;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.apache.commons.lang.StringUtils;
import java.io.Writer;

/**
 * Created by zhaoyang on 26/01/2017.
 */
public class XStreamUtil {

    public static <T> T fromXml(String xmlStr, Class<T> clazz) {
        XStream xStream = new XStream(new XppDriver(new NoNameCoder()));
        // 此处为springboot的一个缺陷, 不过不指定class loader
        // 在springboot容器中使用此方法是会出错的
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
