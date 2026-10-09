package upc.edu.pe.banca_upcina.DTO;

import java.util.Set;

public class LoginResponseDTO {
    private int userId;
    private String username;
    private Set<String> roles;
    private Integer estudianteId;
    private Integer tutorId;
    private String mensaje;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(int userId, String username, Set<String> roles, Integer estudianteId, Integer tutorId, String mensaje) {
        this.userId = userId;
        this.username = username;
        this.roles = roles;
        this.estudianteId = estudianteId;
        this.tutorId = tutorId;
        this.mensaje = mensaje;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Integer getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(Integer estudianteId) {
        this.estudianteId = estudianteId;
    }

    public Integer getTutorId() {
        return tutorId;
    }

    public void setTutorId(Integer tutorId) {
        this.tutorId = tutorId;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
