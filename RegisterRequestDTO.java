package upc.edu.pe.banca_upcina.DTO;

public class RegisterRequestDTO {
    private String username;
    private String password;
    private String universidad;
    private String carrera;
    private int ciclo;

    public RegisterRequestDTO() {
    }

    public RegisterRequestDTO(String username, String password, String universidad, String carrera, int ciclo) {
        this.username = username;
        this.password = password;
        this.universidad = universidad;
        this.carrera = carrera;
        this.ciclo = ciclo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUniversidad() {
        return universidad;
    }

    public void setUniversidad(String universidad) {
        this.universidad = universidad;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public int getCiclo() {
        return ciclo;
    }

    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }
}
