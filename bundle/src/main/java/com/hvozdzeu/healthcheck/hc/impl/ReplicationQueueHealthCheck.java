package com.hvozdzeu.healthcheck.hc.impl;

import com.day.cq.replication.Agent;
import com.day.cq.replication.AgentManager;
import com.day.cq.replication.ReplicationQueue;
import com.hvozdzeu.healthcheck.constants.HealthCheckConstants;
import org.apache.felix.scr.annotations.Reference;
import org.apache.sling.hc.annotations.SlingHealthCheck;
import org.apache.sling.hc.api.HealthCheck;
import org.apache.sling.hc.api.Result;
import org.apache.sling.hc.util.FormattingResultLog;

import java.util.Map;

@SlingHealthCheck(
        name = "Replication Queue Health Check",
        mbeanName = "replicationQueueHC",
        description = "This health check checks the replication queue of agents.",
        tags = {"deep"}
)
public class ReplicationQueueHealthCheck implements HealthCheck {

    @Reference
    private AgentManager agentManager;



    @Override
    public Result execute() {
        FormattingResultLog resultLog = new FormattingResultLog();

        if(agentManager.getAgents().isEmpty()) {
            resultLog.info("No agents configured");
        }

        for (Map.Entry<String, Agent> entry : agentManager.getAgents().entrySet()) {
            Agent agent = entry.getValue();

            // only perform check against valid/enabled agent
            if(agent.isValid() && agent.isEnabled()) {
                ReplicationQueue replicationQueue = agent.getQueue();
                if(!replicationQueue.entries().isEmpty()) {
                    ReplicationQueue.Entry firstEntry = replicationQueue.entries().get(0);
                    if(firstEntry.getNumProcessed() > HealthCheckConstants.MAX_REPLICATION_TRIES) {
                        resultLog.warn("Agent [{}] number of retries: {}, expected number of retries <= {}", agent.getId(), firstEntry.getNumProcessed(), HealthCheckConstants.MAX_REPLICATION_TRIES);
                    }
                } else {
                    resultLog.debug("Agent [{}] replication queue {} is empty.", replicationQueue.getName());
                }
            } else {
                resultLog.debug("Agent [{}] is not valid and/or not enabled.");
            }
        }
        return new Result(resultLog);
    }
}
