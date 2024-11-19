package com.example.parcialweb.controladores;

import com.example.parcialweb.dto.ContratoDTO;
import com.example.parcialweb.modelos.Contrato;
import com.example.parcialweb.repositorios.ContratoRepositorio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/contratos")
public class ContratoControlador {

    private final ContratoRepositorio contratoRepositorio;

    public ContratoControlador(ContratoRepositorio contratoRepositorio) {
        this.contratoRepositorio = contratoRepositorio;
    }


    @GetMapping
    public ResponseEntity<List<ContratoDTO>> obtenerContratos() {
        List<Contrato> contratos = contratoRepositorio.findAll();
        List<ContratoDTO> contratosDTO = contratos.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(contratosDTO);
    }

    
    @PostMapping
    public ResponseEntity<ContratoDTO> crearContrato(@RequestBody ContratoDTO contratoDTO) {
        Contrato contrato = convertirAEntidad(contratoDTO);
        Contrato nuevoContrato = contratoRepositorio.save(contrato);
        return ResponseEntity.ok(convertirADTO(nuevoContrato));
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<ContratoDTO> obtenerContratoPorId(@PathVariable Long id) {
        return contratoRepositorio.findById(id)
                .map(contrato -> ResponseEntity.ok(convertirADTO(contrato)))
                .orElse(ResponseEntity.notFound().build());
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<ContratoDTO> actualizarContrato(@PathVariable Long id, @RequestBody ContratoDTO contratoDTO) {
        return contratoRepositorio.findById(id)
                .map(contratoExistente -> {
                    contratoExistente.setIdentificador(contratoDTO.getIdentificador());
                    contratoExistente.setValor(contratoDTO.getValor());
                    contratoExistente.setNombreContratante(contratoDTO.getNombreContratante());
                    contratoExistente.setDocumentoContratante(contratoDTO.getDocumentoContratante());
                    contratoExistente.setNombreContratantista(contratoDTO.getNombreContratantista());
                    contratoExistente.setDocumentoContratantista(contratoDTO.getDocumentoContratantista());
                    contratoExistente.setFechaInicial(contratoDTO.getFechaInicial());
                    contratoExistente.setFechaFinal(contratoDTO.getFechaFinal());
                    Contrato contratoActualizado = contratoRepositorio.save(contratoExistente);
                    return ResponseEntity.ok(convertirADTO(contratoActualizado));
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarContrato(@PathVariable Long id) {
        if (contratoRepositorio.existsById(id)) {
            contratoRepositorio.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

  
    private ContratoDTO convertirADTO(Contrato contrato) {
        ContratoDTO dto = new ContratoDTO();
        dto.setId(contrato.getId());
        dto.setIdentificador(contrato.getIdentificador());
        dto.setValor(contrato.getValor());
        dto.setNombreContratante(contrato.getNombreContratante());
        dto.setDocumentoContratante(contrato.getDocumentoContratante());
        dto.setNombreContratantista(contrato.getNombreContratantista());
        dto.setDocumentoContratantista(contrato.getDocumentoContratantista());
        dto.setFechaInicial(contrato.getFechaInicial());
        dto.setFechaFinal(contrato.getFechaFinal());
        return dto;
    }

    private Contrato convertirAEntidad(ContratoDTO dto) {
        Contrato contrato = new Contrato();
        contrato.setIdentificador(dto.getIdentificador());
        contrato.setValor(dto.getValor());
        contrato.setNombreContratante(dto.getNombreContratante());
        contrato.setDocumentoContratante(dto.getDocumentoContratante());
        contrato.setNombreContratantista(dto.getNombreContratantista());
        contrato.setDocumentoContratantista(dto.getDocumentoContratantista());
        contrato.setFechaInicial(dto.getFechaInicial());
        contrato.setFechaFinal(dto.getFechaFinal());
        return contrato;
    }
}