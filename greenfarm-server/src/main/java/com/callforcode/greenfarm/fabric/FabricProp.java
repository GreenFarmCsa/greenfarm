package com.callforcode.greenfarm.fabric;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "fabric")
public class FabricProp {

    private String name;

    private String mspId;

    private String keyPath;

    private String certPath;

    private String channelName;

    private List<Node> peers;

    private List<Node> orderers;

}
