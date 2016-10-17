package fi.jyu.imdb.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class PasswordXmlAdapter extends XmlAdapter<String, byte[]> {
    @Override
    public byte[] unmarshal(String value) throws Exception {
        return Util.encodeMD5(value);
    }

    @Override
    public String marshal(byte[] value) throws Exception {
        return null;
    }
}
