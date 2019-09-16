package me.chanjar.weixin.cp.util.xml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import me.chanjar.weixin.common.util.xml.XStreamInitializer;
import me.chanjar.weixin.cp.bean.WxCpTpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpTpXmlPackage;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutImageMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutNewsMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutTextMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutVideoMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutVoiceMessage;

public class XStreamTransformer {

  protected static final Map<Class, XStream> CLASS_2_XSTREAM_INSTANCE = configXStreamInstance();

  /**
   * xml -> pojo
   */
  @SuppressWarnings("unchecked")
  public static <T> T fromXml(Class<T> clazz, String xml) {
    T object = (T) CLASS_2_XSTREAM_INSTANCE.get(clazz).fromXML(xml);
    return object;
  }

  @SuppressWarnings("unchecked")
  public static <T> T fromXml(Class<T> clazz, InputStream is) {
    T object = (T) CLASS_2_XSTREAM_INSTANCE.get(clazz).fromXML(is);
    return object;
  }

  /**
   * 注册扩展消息的解析器.
   *
   * @param clz     类型
   * @param xStream xml解析器
   */
  public static void register(Class clz, XStream xStream) {
    CLASS_2_XSTREAM_INSTANCE.put(clz, xStream);
  }

  /**
   * pojo -> xml.
   */
  public static <T> String toXml(Class<T> clazz, T object) {
    return CLASS_2_XSTREAM_INSTANCE.get(clazz).toXML(object);
  }

  private static Map<Class, XStream> configXStreamInstance() {
    Map<Class, XStream> map = new HashMap<>();
    map.put(WxCpXmlMessage.class, configWxCpXmlMessage());
    map.put(WxCpXmlOutNewsMessage.class, configWxCpXmlOutNewsMessage());
    map.put(WxCpXmlOutTextMessage.class, configWxCpXmlOutTextMessage());
    map.put(WxCpXmlOutImageMessage.class, configWxCpXmlOutImageMessage());
    map.put(WxCpXmlOutVideoMessage.class, configWxCpXmlOutVideoMessage());
    map.put(WxCpXmlOutVoiceMessage.class, configWxCpXmlOutVoiceMessage());
    map.put(WxCpTpXmlPackage.class, configWxCpTpXmlPackage());
    map.put(WxCpTpXmlMessage.class, configWxCpTpXmlMessage());
    return map;
  }

  private static XStream configWxCpXmlMessage() {
    return XStreamInitializer.getInstance(new Class[]{
      WxCpXmlMessage.class,
      WxCpXmlMessage.ScanCodeInfo.class,
      WxCpXmlMessage.SendPicsInfo.class,
      WxCpXmlMessage.SendPicsInfo.Item.class,
      WxCpXmlMessage.SendLocationInfo.class});
  }

  private static XStream configWxCpXmlOutImageMessage() {
    return XStreamInitializer.getInstance(new Class[]{
      WxCpXmlOutMessage.class,
      WxCpXmlOutImageMessage.class
    });
  }

  private static XStream configWxCpXmlOutNewsMessage() {
    return XStreamInitializer.getInstance(new Class[]{
      WxCpXmlOutMessage.class,
      WxCpXmlOutNewsMessage.class,
      WxCpXmlOutNewsMessage.Item.class
    });
  }

  private static XStream configWxCpXmlOutTextMessage() {
    return XStreamInitializer.getInstance(new Class[]{
      WxCpXmlOutMessage.class,
      WxCpXmlOutTextMessage.class
    });
  }

  private static XStream configWxCpXmlOutVideoMessage() {
    return XStreamInitializer.getInstance(new Class[]{
      WxCpXmlOutMessage.class,
      WxCpXmlOutVideoMessage.class,
      WxCpXmlOutVideoMessage.Video.class
    });
  }

  private static XStream configWxCpXmlOutVoiceMessage() {
    return XStreamInitializer.getInstance(new Class[]{
      WxCpXmlOutMessage.class,
      WxCpXmlOutVoiceMessage.class
    });
  }
  
  private static XStream configWxCpTpXmlPackage() {
    return XStreamInitializer.getInstance(WxCpTpXmlPackage.class);
  }
  
  private static XStream configWxCpTpXmlMessage() {
    return XStreamInitializer.getInstance(WxCpTpXmlMessage.class);
  }

}
