package cuenta_movimientos.com.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReporteResponseDTO {
    private String fecha;
    private String cliente;
    private String numeroCuenta;
    private String tipo;
    private Double saldoInicial;
    private Boolean estado;
    private Double movimiento;
    private Double saldoDisponible;
}
