package br.edu.fib.bibliotecajavamvc.model;

import java.util.Date;

import javax.persistence.*;

@Entity
public class Emprestimo {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataEmprestimo;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataDevolucao;
	
	@ManyToOne
	private Livro livro;

	@ManyToOne
	private Usuario usuario;
	
	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataEmprestimo() {
		return dataEmprestimo;
	}

	public void setDataEmprestimo(Date dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
	}

	public Date getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
