package com.mw.distribution.wxpay.support;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.annotation.DomHandler;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMResult;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 处理接收微信动态字段以及 api 变更产生的未知字段.
 *
 * @see javax.xml.bind.annotation.W3CDomHandler
 */
public class AnyElementsDomHandler implements DomHandler<SortedMap<String, String>, DOMResult> {

    private final SortedMap<String, String> otherElements = new TreeMap<>();

    @Override
    public DOMResult createUnmarshaller(final ValidationEventHandler errorHandler) {
        return new DOMResult();
    }

    @Override
    public SortedMap<String, String> getElement(final DOMResult rt) {

        final Node n = rt.getNode();
        Element element = null;
        if (n instanceof Document) {
            element = ((Document) n).getDocumentElement();
        }
        else if (n instanceof Element) {
            element = (Element) n;
        }
        else if (n instanceof DocumentFragment) {
            element = (Element) n.getChildNodes().item(0);
        }
        if (null != element) {
            this.otherElements.put(element.getNodeName(), element.getTextContent());
        }
        return this.otherElements;
    }

    @Override
    public Source marshal(final SortedMap<String, String> n, final ValidationEventHandler errorHandler) {
        throw new UnsupportedOperationException();
    }
}
