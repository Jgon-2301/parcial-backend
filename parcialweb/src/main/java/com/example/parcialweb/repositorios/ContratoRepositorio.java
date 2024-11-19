package com.example.parcialweb.repositorios;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.parcialweb.modelos.Contrato;

public interface ContratoRepositorio extends JpaRepository<Contrato, Long> {
}
