package entities;

import jakarta.persistence.*;
import java.util.Set;
import java.util.List;

@Entity
@Table(name = "ESTUDIANTE")
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", length = 255, nullable = false)
    private String nombre;

    @Column(name = "correoInstitucional", length = 255, nullable = false, unique = true)
    private String correoInstitucional;

    @Column(name = "contrasena", length = 255, nullable = false)
    private String contrasena;
    
    @Column(name = "edad", nullable = false)
    private Integer edad;
    
    @Column(name = "sexo", length = 20, nullable = false)
    private String sexo;
    
    @Column(name = "descripcion", length = 500) // Nullable por defecto, está bien
    private String descripcion;

    @Column(name = "carrera", length = 100)
    private String carrera;

    @Column(name = "fotoPerfilURL", length = 255)
    private String fotoPerfilURL; 

    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "ESTUDIANTE_HOBBY",
        joinColumns = @JoinColumn(name = "estudianteId"),
        inverseJoinColumns = @JoinColumn(name = "hobbyId")
    )
    private Set<Hobby> hobbies;

    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "ESTUDIANTE_INTERES",
        joinColumns = @JoinColumn(name = "estudianteId"),
        inverseJoinColumns = @JoinColumn(name = "interesId")
    )
    private Set<Interes> intereses;

    
    @OneToMany(mappedBy = "emisor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Interaccion> interaccionesEnviadas;

    @OneToMany(mappedBy = "receptor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Interaccion> interaccionesRecibidas;

    @OneToMany(mappedBy = "estudiante1", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MatchConexion> matchesComoEstudiante1;

    @OneToMany(mappedBy = "estudiante2", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MatchConexion> matchesComoEstudiante2;

    
    @OneToMany(mappedBy = "emisor", cascade = CascadeType.ALL, orphanRemoval = true) 
    private List<Mensaje> mensajesEnviados;

    

    public Estudiante() {
    }

    public Estudiante(Integer id, String nombre, String correoInstitucional, String contrasena, 
                      Integer edad, String sexo, String descripcion, // <-- NUEVO
                      String carrera, String fotoPerfilURL, Set<Hobby> hobbies, Set<Interes> intereses, 
                      Set<Interaccion> interaccionesEnviadas, Set<Interaccion> interaccionesRecibidas, 
                      Set<MatchConexion> matchesComoEstudiante1, Set<MatchConexion> matchesComoEstudiante2, 
                      List<Mensaje> mensajesEnviados) {
        
        this.id = id;
        this.nombre = nombre;
        this.correoInstitucional = correoInstitucional;
        this.contrasena = contrasena;
        this.edad = edad; // <-- NUEVO
        this.sexo = sexo; // <-- NUEVO
        this.descripcion = descripcion; // <-- NUEVO
        this.carrera = carrera;
        this.fotoPerfilURL = fotoPerfilURL;
        this.hobbies = hobbies;
        this.intereses = intereses;
        this.interaccionesEnviadas = interaccionesEnviadas;
        this.interaccionesRecibidas = interaccionesRecibidas;
        this.matchesComoEstudiante1 = matchesComoEstudiante1;
        this.matchesComoEstudiante2 = matchesComoEstudiante2;
        this.mensajesEnviados = mensajesEnviados;
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

    public String getCorreoInstitucional() {
        return correoInstitucional;
    }

    public void setCorreoInstitucional(String correoInstitucional) {
        this.correoInstitucional = correoInstitucional;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    
    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getFotoPerfilURL() {
        return fotoPerfilURL;
    }

    public void setFotoPerfilURL(String fotoPerfilURL) {
        this.fotoPerfilURL = fotoPerfilURL;
    }

    public Set<Hobby> getHobbies() {
        return hobbies;
    }

    public void setHobbies(Set<Hobby> hobbies) {
        this.hobbies = hobbies;
    }

    public Set<Interes> getIntereses() {
        return intereses;
    }

    public void setIntereses(Set<Interes> intereses) {
        this.intereses = intereses;
    }

    public Set<Interaccion> getInteraccionesEnviadas() {
        return interaccionesEnviadas;
    }

    public void setInteraccionesEnviadas(Set<Interaccion> interaccionesEnviadas) {
        this.interaccionesEnviadas = interaccionesEnviadas;
    }

    public Set<Interaccion> getInteraccionesRecibidas() {
        return interaccionesRecibidas;
    }

    public void setInteraccionesRecibidas(Set<Interaccion> interaccionesRecibidas) {
        this.interaccionesRecibidas = interaccionesRecibidas;
    }

    public Set<MatchConexion> getMatchesComoEstudiante1() {
        return matchesComoEstudiante1;
    }

    public void setMatchesComoEstudiante1(Set<MatchConexion> matchesComoEstudiante1) {
        this.matchesComoEstudiante1 = matchesComoEstudiante1;
    }

    public Set<MatchConexion> getMatchesComoEstudiante2() {
        return matchesComoEstudiante2;
    }

    public void setMatchesComoEstudiante2(Set<MatchConexion> matchesComoEstudiante2) {
        this.matchesComoEstudiante2 = matchesComoEstudiante2;
    }

    public List<Mensaje> getMensajesEnviados() {
        return mensajesEnviados;
    }

    public void setMensajesEnviados(List<Mensaje> mensajesEnviados) {
        this.mensajesEnviados = mensajesEnviados;
    }
    
}
