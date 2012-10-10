package com.epam.urlchopper.service.simple;

/**
 * This enum contains the valid url schemes which are acceptable by the application.
 * @author Marton_Sereg
 *
 */
public enum ValidUrl {
    HTTP("http://"),
    HTTPS("https://"),
    FTP("ftp://"),
    MAILTO("mailto:"),
    GOPHER("gopher://"),
    TELNET("telnet://"),
    MID("mid:"),
    CID("cid:"),
    NEWS("news:"),
    NNTP("nntp://"),
    TN3270("tn3270://"),
    RLOGIN("rlogin://"),
    FILE("file://"),
    WAIS("wais://"),
    NFS("nfs://"),
    PROSPERO("prospero://");

    private String value;

    private ValidUrl(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
