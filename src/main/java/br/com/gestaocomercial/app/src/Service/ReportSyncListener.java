package br.com.gestaocomercial.app.src.Service;

import br.com.gestaocomercial.app.src.Model.DTO.SyncEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.client.RestClient;

@Component
public class ReportSyncListener {

    private static final Logger log = LoggerFactory.getLogger(ReportSyncListener.class);
    private final RestClient restClient;

    public ReportSyncListener(RestClient reportRestClient) {
        this.restClient = reportRestClient;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onSyncEvent(SyncEvent event) {
        try {
            restClient.post()
                    .uri(event.endpoint())
                    .body(event.payload())
                    .retrieve()
                    .toBodilessEntity();
        } catch (Exception e) {
            // Loga a falha para posterior reenvio via carga inicial/reconciliação
            log.error("Falha ao sincronizar com api-report no endpoint {}: {}", event.endpoint(), e.getMessage());
        }
    }
}
