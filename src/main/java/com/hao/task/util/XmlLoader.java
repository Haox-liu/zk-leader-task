package com.hao.task.util;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlLoader extends DefaultHandler {

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        System.out.println("开始解析xml文件");
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        System.out.println("结束解析xml文件");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

    }


    public static void main(String[] args) {


    }

}
