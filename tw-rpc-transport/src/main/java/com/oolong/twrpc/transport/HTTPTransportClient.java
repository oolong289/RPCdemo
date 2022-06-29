package com.oolong.twrpc.transport;

import com.oolong.twrpc.Peer;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author oolong
 */
public class HTTPTransportClient implements TransportClient {
    String url;

    /**
     * 设置端口节点的url
     *
     * @param peer
     */
    @Override
    public void connect(Peer peer) {
        this.url = "http://" + peer.getHost()
                + ":" + peer.getPort();
    }

    /**
     * 使用HttpURLConnection设置网络连接，获取输入与输出
     *
     * @param data
     * @return
     */
    @Override
    public InputStream write(InputStream data) {
        try {
            HttpURLConnection httpConn = (HttpURLConnection) new URL(url).openConnection();
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            httpConn.setUseCaches(false);
            httpConn.setRequestMethod("POST");

            httpConn.connect();
            IOUtils.copy(data, httpConn.getOutputStream());
            int responseCode = httpConn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return httpConn.getInputStream();
            } else {
                return httpConn.getErrorStream();
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void close() {

    }
}
