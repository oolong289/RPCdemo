package com.oolong.twrpc.client;

import com.oolong.twrpc.Peer;
import com.oolong.twrpc.common.utils.ReflectionUtils;
import com.oolong.twrpc.transport.TransportClient;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author oolong
 */
@Slf4j
public class RandomTransportSelector implements TransportSelector {

    /**
     * 已经连接好的Client
     */
    private List<TransportClient> clients;


    @Override
    public void inti(List<Peer> peers, int count, Class<? extends TransportClient> clazz) {
        count = Math.max(count, 1);

        /**
         * 创建TransportClient类型实例,与每一个peer建立连接
         */
        for (Peer peer : peers) {
            for (int i = 0; i < count; i++) {
                TransportClient client = ReflectionUtils.newInstance(clazz);
                client.connect(peer);
                clients.add(client);
            }
            log.info("connect server: {}", peer);
        }
    }

    public RandomTransportSelector() {
        clients = new ArrayList<>();
    }

    @Override
    public synchronized TransportClient select() {
        int i = new Random().nextInt(clients.size());
        // 在建立好的连接中取出返回
        return clients.remove(i);
    }


    @Override
    public synchronized void release(TransportClient client) {
        // 将取回的连接重写加入到clients中
        clients.add(client);
    }

    @Override
    public synchronized void close() {
        for (TransportClient client : clients) {
            client.close();
        }
        clients.clear();
    }
}
