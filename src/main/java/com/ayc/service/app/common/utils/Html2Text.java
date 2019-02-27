package com.ayc.service.app.common.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

/**
 * 将html转化成文本
 *
 * @author wwq
 * @version 1.0
 * @createDate 2019/2/18 20:46
 */
public class Html2Text extends HTMLEditorKit.ParserCallback {

    private static Html2Text html2Text = new Html2Text();
    StringBuffer s;

    public Html2Text() { }
    public void parse(String str) throws IOException {
        InputStream iin = new ByteArrayInputStream(str.getBytes());
        Reader in = new InputStreamReader(iin);
        s = new StringBuffer();
        ParserDelegator delegator = new ParserDelegator();
        // the third parameter is TRUE to ignore charset directive
        delegator.parse(in, this, Boolean.TRUE);
        iin.close();
        in.close();
    }

    public void handleText(char[] text, int pos) {
        s.append(text);
    }

    public String getText() {
        return s.toString();
    }

    public static String getContent(String str) {
        try { html2Text.parse(str); } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return html2Text.getText();
    }
}
