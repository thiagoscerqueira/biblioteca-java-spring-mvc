package br.edu.fib.bibliotecajavamvc.model;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@DynamicUpdate
public class Livro {
	
	@Id
	@GeneratedValue
	private Long id;

	@NotBlank
	@Size(min=2, max = 100)
	private String titulo;
	
	private String foto;

	@NotNull
	@Min(value = 10)
	@Column(name="QUANTIDADE_PAGINAS")
	private Integer quantidade;
	
	private String isbn;

	@NotNull
	@ManyToOne
	@Cascade(CascadeType.SAVE_UPDATE)
	private Autor autor;
	
	@OneToMany(mappedBy="livro")
	private List<Review> reviews = new ArrayList<>();
	
	@OneToMany(mappedBy="livro")
	private List<Emprestimo> emprestimos = new ArrayList<>();

	@Transient
	private MultipartFile fotoUpload;

	@Transient
	private Double mediaAvaliacoes = 0d;

	public Livro() {
	}

	public Livro(String titulo, String foto, Integer quantidade, String isbn, Autor autor, Double mediaAvaliacoes) {
		this.titulo = titulo;
		this.foto = foto;
		this.quantidade = quantidade;
		this.isbn = isbn;
		this.autor = autor;
		this.mediaAvaliacoes = mediaAvaliacoes;
	}

	public Livro(Long id, Double mediaAvaliacoes) {
		this.id = id;
		this.mediaAvaliacoes = mediaAvaliacoes;
	}

	public Boolean temFotoCadastrada() {
	    return !StringUtils.isEmpty(this.foto);
    }

    public Boolean fotoVazia() {
        return getFotoUpload() != null && StringUtils.isEmpty(getFotoUpload().getOriginalFilename());
    }

    public Boolean formatoFotoValido() {
        return getFotoUpload() != null && !StringUtils.isEmpty(getFotoUpload().getContentType()) && getFotoUpload().getContentType().equals("image/jpeg");
    }

	public Double getMediaAvaliacoes() {
		return mediaAvaliacoes;
	}

	public void setMediaAvaliacoes(Double mediaAvaliacoes) {
		this.mediaAvaliacoes = mediaAvaliacoes;
	}

	public MultipartFile getFotoUpload() {
        return fotoUpload;
    }

    public void setFotoUpload(MultipartFile fotoUpload) {
        this.fotoUpload = fotoUpload;
    }

    public List<Emprestimo> getEmprestimos() {
		return emprestimos;
	}

	public void setEmprestimos(List<Emprestimo> emprestimos) {
		this.emprestimos = emprestimos;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}


	

}
