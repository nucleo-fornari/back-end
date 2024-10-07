//package fornari.nucleo.domain.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@AllArgsConstructor
//@Getter
//@Setter
//public class Aluno {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @Column(name = "ra")
//    private String ra;
//
//    @Column(name = "nome")
//    private String nome;
//
//    @Column(name = "laudado")
//    private boolean laudado;
//
//    @Column(name = "dtNasc")
//    private LocalDate dtNasc;
//
//    @ManyToMany(targetEntity = Usuario.class, mappedBy = "afiliados")
//    private List<Usuario> responsaveis;
//
//    public Aluno() {
//        this.responsaveis = new ArrayList<>();
//    }
//
//}
