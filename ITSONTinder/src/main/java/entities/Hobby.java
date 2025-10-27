package entities;

import jakarta.persistence.*;
import java.util.Set;


@Entity
@Table(name = "HOBBY")
public class Hobby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", length = 100, nullable = false, unique = true)
    private String nombre;

    @ManyToMany(mappedBy = "hobbies")
    private Set<Estudiante> estudiantes;

    // Getters y Setters

    public Hobby() {
    }

    public Hobby(Integer id, String nombre, Set<Estudiante> estudiantes) {
        this.id = id;
        this.nombre = nombre;
        this.estudiantes = estudiantes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(Set<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }
    
}
