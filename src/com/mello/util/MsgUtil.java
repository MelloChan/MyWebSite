package com.mello.util;


import com.mello.dispenser.EventDispatcher;
import com.mello.dispenser.MsgDispatcher;
import com.mello.entity.message.req.ImageMessage;
import com.mello.entity.message.resp.Article;
import com.mello.entity.message.resp.MusicMessage;
import com.mello.entity.message.resp.NewsMessage;
import com.mello.entity.message.resp.TextMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/4.
 * 微信消息 接收、响应、事件的封装类
 * 包括解析XML方法、返回XML文件
 */
public class MsgUtil {
    private MsgUtil(){}
    //消息响应
    public static final String RESP_TYPE_TEXT="text";

    public static final String RESP_TYPE_IMG="image";

    public static final String RESP_TYPE_VOICE="voice";

    public static final String RESP_TYPE_VIDEO="video";

    public static final String RESP_TYPE_MUSIC="music";

    public static final String RESP_TYPE_NEWS="news";

    //消息接收
    public static final String REQ_TYPE_TEXT="text";

    public static final String REQ_TYPE_IMG="image";

    public static final String REQ_TYPE_VOICE="voice";

    public static final String REQ_TYPE_VIDEO="VIDEO";

    public static final String REQ_TYPE_SHORTVIDEO="shortvideo";

    public static final String REQ_TYPE_LOCATION="location";

    public static final String REQ_TYPE_LINK="link";

    public static final String REQ_TYPE_EVENT="event";

    //事件推送
    public static final String EVENT_TYPE_SUBSCRIBE="subscribe";

    public static final String EVENT_TYPE_UNSUBCRIBE="unSubscribe";

    public static final String EVENT_TYPE_CLICK="CLICK";

    public static final String EVENT_TYPE_VIEW="VIEW";

    public static final String EVENT_TYPE_SCAN="SCAN";

    public static final String EVENT_TYPE_LOCATION="LOCATION";

    /**
     * 接收微信XML文档内容
     * @param req 请求类
     * @return 放回解析结果集【哈希表】
     */
    public static Map<String,String> parseXML(HttpServletRequest req){
        //存放解析XML结果集
        Map<String,String>map=new HashMap<>();

        try(BufferedInputStream bis=new BufferedInputStream(req.getInputStream())) {
            //SAX XML输入流
            SAXReader reader =new SAXReader();
            //XML文档
            Document document=reader.read(bis);
            //XML根元素
            Element root=document.getRootElement();
            //子节点集
            List<Element>elementList=root.elements();
            for (Element element:elementList){
                map.put(element.getName(),element.getText());
            }
        } catch (IOException | DocumentException e) {
            System.out.println("parse XML IO异常:"+e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 封装成XML格式时 按照微信官方所需要的格式
     */
    @SuppressWarnings("unused")
    private static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // 对所有xml节点的转换都增加CDATA标记
                boolean cdata = true;
                @SuppressWarnings("rawtypes")
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });

    /**
     * 文本->XML格式
     * @param text 需要响应的文本
     * @return 封装好的XML文件
     */
    public static String textToXML(TextMessage text){
        xstream.alias("xml",text.getClass());
        return xstream.toXML(text);
    }

    /**
     * music->XML格式
     * @param music 需要响应的music
     * @return 封装好的XML文件
     */
    public static String musicToXML(MusicMessage music){
        xstream.alias("xml",music.getClass());
        return xstream.toXML(music);
    }

    /**
     * news->XML
     * @param news 需要响应的news
     * @return 封装好的XML文件
     */
    public static String newsToXML(NewsMessage news){
        xstream.alias("xml",news.getClass());
        xstream.alias("item", Article.class);
        return xstream.toXML(news);
    }

    public static String imgToXML(ImageMessage image){
        xstream.alias("xml",ImageMessage.class);
        return xstream.toXML(image);
    }

    /**
     * 通用的微信服务消息处理器 根据消息类型自动转发到各自处理方法
     * @param req 请求头
     * @return 响应给微信的XML数据
     * @throws IOException IO流异常
     */
    public static String returnRespXML(HttpServletRequest req) throws IOException {
        Map<String, String> map = MsgUtil.parseXML(req);
        String msgType = map.get("MsgType");
        String respXML;
        if (MsgUtil.REQ_TYPE_EVENT.equals(msgType)) {
            respXML = EventDispatcher.processEvent(map); //进入事件处理
        } else {
            respXML = MsgDispatcher.processMessage(map); //进入消息处理
        }
        return respXML;
    }

}
