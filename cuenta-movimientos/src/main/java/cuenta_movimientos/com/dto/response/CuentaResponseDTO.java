package cuenta_movimientos.com.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CuentaResponseDTO {
    private Long id;
    private String numeroCuenta;
    private String tipoCuenta;
    private Double saldoInicial;
    private Boolean estado;
}
