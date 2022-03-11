package com.replication.policy;

import org.apache.kafka.connect.mirror.DefaultReplicationPolicy;
import org.apache.kafka.connect.mirror.MirrorConnectorConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class CustomMMReplicationPolicy extends DefaultReplicationPolicy {
    private static final Logger logger = LoggerFactory.getLogger(CustomMMReplicationPolicy.class);
    private String sourceClusterAlias;

    @Override
    public void configure(Map<String, ?> props) {
        super.configure(props);
        sourceClusterAlias = (String) props.get(MirrorConnectorConfig.SOURCE_CLUSTER_ALIAS);
        if (sourceClusterAlias == null) {
            String message = String.format("Property %s not found", MirrorConnectorConfig.SOURCE_CLUSTER_ALIAS);
            logger.error(message);
            throw new RuntimeException(message);
        }

    }

    @Override
    public String formatRemoteTopic(String sourceClusterAlias, String topic) {
        return topic;
    }

    @Override
    public String topicSource(String topic) {
        return topic == null? null: sourceClusterAlias;
    }

    @Override
    public String upstreamTopic(String topic) {
        return null;
    }

}
