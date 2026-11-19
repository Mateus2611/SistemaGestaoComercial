package br.com.gestaocomercial.app.src.Controller;

import br.com.gestaocomercial.app.src.Service.SyncBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/sync")
public class AdminSyncController {

    @Autowired
    private SyncBatchService syncBatchService;

    @PostMapping("/full")
    public ResponseEntity<String> sincronizarTudo() {
        return ResponseEntity.ok(syncBatchService.executarSincronizacaoCompleta());
    }
}