package com.oolong.twrpc.client;

import com.oolong.twrpc.Peer;
import com.oolong.twrpc.codec.Decoder;
import com.oolong.twrpc.codec.Encoder;
import com.oolong.twrpc.codec.JSONDecoder;
import com.oolong.twrpc.codec.JSONEncoder;
import com.oolong.twrpc.transport.HTTPTransportClient;
import com.oolong.twrpc.transport.TransportClient;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * @author oolong
 */
@Data
public class RpcClientConfig {
    private Class<? extends TransportClient> transportClass =
            HTTPTransportClient.class;
    private Class<? extends Encoder> encoderClass = JSONEncoder.class;
    private Class<? extends Decoder> decoderCLass = JSONDecoder.class;
    private Class<? extends TransportSelector> selectorClass = RandomTransportSelector.class;

    private int connectCount = 1;


    private List<Peer> servers = Arrays.asList(
            new Peer("127.0.0.1",3000)
    );
}
