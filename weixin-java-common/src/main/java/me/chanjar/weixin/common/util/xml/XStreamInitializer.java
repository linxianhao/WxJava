package me.chanjar.weixin.common.util.xml;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class XStreamInitializer {

  private static Map<Class[],XStream> xStreamMap = new HashMap<>();

  private static final XppDriver XPP_DRIVER = new XppDriver() {
    @Override
    public HierarchicalStreamWriter createWriter(Writer out) {
      return new PrettyPrintWriter(out, getNameCoder()) {
        private static final String PREFIX_CDATA = "<![CDATA[";
        private static final String SUFFIX_CDATA = "]]>";
        private static final String PREFIX_MEDIA_ID = "<MediaId>";
        private static final String SUFFIX_MEDIA_ID = "</MediaId>";

        @Override
        protected void writeText(QuickWriter writer, String text) {
          if (text.startsWith(PREFIX_CDATA) && text.endsWith(SUFFIX_CDATA)) {
            writer.write(text);
          } else if (text.startsWith(PREFIX_MEDIA_ID) && text.endsWith(SUFFIX_MEDIA_ID)) {
            writer.write(text);
          } else {
            super.writeText(writer, text);
          }

        }

        @Override
        public String encodeNode(String name) {
          //防止将_转换成__
          return name;
        }
      };
    }
  };

  public static XStream getInstance(Class clazz){
    return getInstance(new Class[]{clazz});
  }

  public static XStream getInstance(Class[] clazz) {
    XStream xStream = xStreamMap.get(clazz);
    if (xStream != null){
      return xStream;
    }
    XStream xstream = new XStream(new PureJavaReflectionProvider(), XPP_DRIVER);
    xstream.ignoreUnknownElements();
    xstream.setMode(XStream.NO_REFERENCES);
    XStream.setupDefaultSecurity(xstream);
    xstream.allowTypesByWildcard(new String[]{
      "me.chanjar.weixin.**", "cn.binarywang.wx.**", "com.github.binarywang.**"
    });
    xstream.processAnnotations(clazz);
    xStreamMap.put(clazz,xStream);
    //xstream.setClassLoader(Thread.currentThread().getContextClassLoader());
    return xstream;
  }

}
