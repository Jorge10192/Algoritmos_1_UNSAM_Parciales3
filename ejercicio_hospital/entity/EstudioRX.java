package ejercicio_hospital.entity;

public class EstudioRX extends Estudio {
    private ZonaCuerpo zonaCuerpo;

    public EstudioRX(ZonaCuerpo zonaCuerpo) {
        this.zonaCuerpo = zonaCuerpo;
    }

    public EstudioRX(String zonaCuerpaComoString) {
        this.zonaCuerpo = ZonaCuerpo.valueOf(zonaCuerpaComoString.toUpperCase());
    }

    public ZonaCuerpo getZonaCuerpo() {
        return zonaCuerpo;
    }

    @Override
    public String getNombre() {
        return "Rayos Equis";
    }

    @Override
    public String getDescripcion() {
        return String.format("Radiografía de alguna zona %s del cuerpo."
                , zonaCuerpo);
    }
}
