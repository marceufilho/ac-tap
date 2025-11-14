package br.edu.ibmec.dao;

import br.edu.ibmec.entity.Inscricao;
import br.edu.ibmec.entity.InscricaoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao, InscricaoId> {

    // Chave composta
    @Query("SELECT i FROM Inscricao i WHERE i.id.aluno.matricula = :matricula AND i.id.turma.codigo = :codigo")
    Optional<Inscricao> findByAlunoMatriculaAndTurmaCodigo(@Param("matricula") int matricula, @Param("codigo") int codigo);
}
