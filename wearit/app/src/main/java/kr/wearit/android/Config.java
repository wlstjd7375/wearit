package kr.wearit.android;

/**
 * Created by KimJS on 2016-09-03.
 */
public class Config {

    public enum Server {
        DEV, //
        @Deprecated
        STG, //
        PRD, //
    }

    //

    public static final boolean LOG = true;

    public static final String YOUTUBE = "AIzaSyChYYXGJGd001Is5sR-mBnnyfwma8wefmo";//"AIzaSyCiMYN9NfPJnSoC1xQP-ldP-xxniymiDuk";
    //

    private static final String SITE_PRD = "http://192.168.123.101:8080";//"http://192.168.25.28:8080";//"http://api.stylemap.kr:8080";//"http://api.stylemap.kr:8080";//"http://121.130.206.3:8080";//"http://121.130.206.3:8080";/"http://api.stylemap.kr:8080";
    @Deprecated
    private static final String SITE_STG = "http://stylemap.preduct.biz:9000";//"http://192.168.0.15:8080";"http://stylemap.preduct.biz:9000";
    private static final String SITE_DEV = "http://192.168.0.15:8080";
    private static final String BASE = "/api";

    //

    private static Data data = new Data();

    //

    public static void load(Server server) {
        data.server = server;

        switch (data.server) {
            case DEV:
                data.site = SITE_DEV;
                break;
            case STG:
                data.site = SITE_STG;
                break;
            case PRD:
                data.site = SITE_PRD;
                break;
        }

        data.base = data.site + BASE;
    }

    //

    public static Server getServer() {
        return data.server;
    }

    public static String getSite() {
        return data.site;
    }

    public static String getBase() {
        return data.base;
    }

    //

    public static class Data {

        private Server server = Server.PRD;
        private String site = SITE_PRD;
        private String base = SITE_PRD + BASE;
    }
}