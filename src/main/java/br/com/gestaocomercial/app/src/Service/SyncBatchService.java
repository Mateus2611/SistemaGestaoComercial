package br.com.gestaocomercial.app.src.Service;

import br.com.gestaocomercial.app.src.Model.DTO.ClienteSyncDTO;
import br.com.gestaocomercial.app.src.Model.DTO.OrcamentoSyncDTO;
import br.com.gestaocomercial.app.src.Model.DTO.ProdutoSyncDTO;
import br.com.gestaocomercial.app.src.Model.DTO.VendaSyncDTO;
import br.com.gestaocomercial.app.src.Model.Orcamento;
import br.com.gestaocomercial.app.src.Model.OrcamentoProduto;
import br.com.gestaocomercial.app.src.Repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SyncBatchService {
    private static final Logger log = LoggerFactory.getLogger(SyncBatchService.class);

    @Autowired
    private RestClient reportRestClient;
    @Autowired private IClienteRepository clienteRepo;
    @Autowired private IProdutoRepository produtoRepo;
    @Autowired private IOrcamentoRepository orcamentoRepo;
    @Autowired private IVendaRepository vendaRepo;
    @Autowired private IAvaliacaoRepository avaliacaoRepo;

    @Scheduled(fixedDelayString = "${sync.report.intervalo-ms:1800000}", initialDelay = 60000)
    @Transactional(readOnly = true)
    public String executarSincronizacaoCompleta() {
        try {
            List<ClienteSyncDTO> clientes = clienteRepo.findAll().stream().map(c -> {
                String email = (c.getEmails() != null && !c.getEmails().isEmpty()) ? c.getEmails().get(0).getEndereco() : null;
                String cidade = c.getEndereco() != null ? c.getEndereco().getCidade() : null;
                String estado = c.getEndereco() != null ? c.getEndereco().getEstado() : null;
                return new ClienteSyncDTO(
                        c.getId(), c.getNome(),
                        c.getTipo() != null ? c.getTipo().name() : null,
                        c.getDataCadastro() != null ? c.getDataCadastro().toLocalDate() : null,
                        c.getStatus() != null ? c.getStatus().name() : null,
                        cidade, estado, email
                );
            }).toList();
            enviarLote("/sync/clientes/batch", clientes);

            List<ProdutoSyncDTO> produtos = produtoRepo.findAll().stream().map(p ->
                    new ProdutoSyncDTO(p.getId(), p.getNome(), p.getValor() != null ? p.getValor() : BigDecimal.ZERO, true)
            ).toList();
            enviarLote("/sync/produtos/batch", produtos);

            List<OrcamentoSyncDTO> orcamentos = orcamentoRepo.findAll().stream().map(o -> {
                List<OrcamentoSyncDTO.ItemSyncDTO> itens = new ArrayList<>();
                if (o.getOrcamentoProdutos() != null) {
                    for (OrcamentoProduto op : o.getOrcamentoProdutos()) {
                        if (op.getProduto() != null) {
                            itens.add(new OrcamentoSyncDTO.ItemSyncDTO(op.getProduto().getId(), op.getQuantidade()));
                        }
                    }
                }
                return new OrcamentoSyncDTO(
                        o.getId(),
                        o.getCliente() != null ? o.getCliente().getId() : null,
                        o.getCliente() != null ? o.getCliente().getNome() : null,
                        o.getDataCriacao() != null ? o.getDataCriacao().toLocalDate() : null,
                        o.getDataValidade() != null ? o.getDataValidade().toLocalDate() : null,
                        o.getValor(), o.getDesconto(),
                        o.getStatus() != null ? o.getStatus().name() : null,
                        itens
                );
            }).toList();
            enviarLote("/sync/orcamentos/batch", orcamentos);

            Map<Integer, Float> notasPorVenda = avaliacaoRepo.findAll().stream()
                    .filter(a -> a.getVenda() != null && a.getNota() != null)
                    .collect(Collectors.toMap(
                            a -> a.getVenda().getId(),
                            a -> a.getNota().floatValue(),
                            (existente, novo) -> novo
                    ));

            List<VendaSyncDTO> vendas = vendaRepo.findAll().stream().map(v -> {
                Orcamento o = v.getOrcamento();
                Integer clienteId = (o != null && o.getCliente() != null) ? o.getCliente().getId() : null;
                BigDecimal valorFinal = (o != null && o.getValor() != null) ? o.getValor() : BigDecimal.ZERO;
                return new VendaSyncDTO(
                        v.getId(),
                        o != null ? o.getId() : null,
                        clienteId,
                        v.getDataCriacao() != null ? v.getDataCriacao().toLocalDate() : null,
                        v.getStatusPagamento() != null ? v.getStatusPagamento().name() : null,
                        valorFinal,
                        notasPorVenda.get(v.getId())
                );
            }).toList();
            enviarLote("/sync/vendas/batch", vendas);

            String resumo = String.format(
                    "Sincronização concluída: %d clientes, %d produtos, %d orçamentos, %d vendas (%d avaliadas).",
                    clientes.size(), produtos.size(), orcamentos.size(), vendas.size(), notasPorVenda.size()
            );
            log.info(resumo);
            return resumo;

        } catch (Exception e) {
            log.warn("Falha na sincronização agendada com api-report (será tentada novamente no próximo ciclo): {}", e.getMessage());
            return "Erro na sincronização: " + e.getMessage();
        }
    }

    private void enviarLote(String uri, List<?> dados) {
        if (!dados.isEmpty()) {
            reportRestClient.post()
                    .uri(uri)
                    .body(dados)
                    .retrieve()
                    .toBodilessEntity();
        }
    }
}
