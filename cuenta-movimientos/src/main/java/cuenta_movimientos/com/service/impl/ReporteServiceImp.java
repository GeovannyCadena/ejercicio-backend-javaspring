package cuenta_movimientos.com.service.impl;

import cuenta_movimientos.com.dto.response.ReporteResponseDTO;
import cuenta_movimientos.com.model.Cuenta;
import cuenta_movimientos.com.model.Movimiento;
import cuenta_movimientos.com.repository.interfaces.CuentaRepository;
import cuenta_movimientos.com.repository.interfaces.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReporteServiceImp {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private MovimientoRepository movimientoRepository;

    // Método para obtener el reporte con los datos requeridos
    @Transactional
    public List<ReporteResponseDTO> obtenerReporte(String fechaInicio, String fechaFin) {
        // Convertir las fechas de entrada en objetos Date (formato "yyyy-MM-dd")
        Date inicio = parseFecha(fechaInicio);
        Date fin = parseFecha(fechaFin);

        // Obtener todas las cuentas
        List<Cuenta> cuentas = cuentaRepository.findAll();

        List<ReporteResponseDTO> reporteList = new ArrayList<>();

        for (Cuenta cuenta : cuentas) {
            // Obtener todos los movimientos de la cuenta en el rango de fechas
            List<Movimiento> movimientos = movimientoRepository.findByCuentaIdAndFechaBetween(
                    cuenta.getId(), inicio, fin);

            // Calcular el saldo inicial (que no cambia)
            Double saldoInicial = cuenta.getSaldoInicial();
            Double saldoDisponible = saldoInicial;

            for (Movimiento movimiento : movimientos) {
                // Calcular el saldo después de cada movimiento
                if ("DEPOSITO".equalsIgnoreCase(movimiento.getTipoMovimiento())) {
                    saldoDisponible += movimiento.getValor();
                } else if ("RETIRO".equalsIgnoreCase(movimiento.getTipoMovimiento())) {
                    saldoDisponible -= movimiento.getValor();
                }

                // Crear el registro del reporte
                ReporteResponseDTO reporte = new ReporteResponseDTO();
                reporte.setFecha(formatFecha(movimiento.getFecha()));
                reporte.setCliente("Jose Lema"); // Nombre del cliente
                reporte.setNumeroCuenta(cuenta.getNumeroCuenta());
                reporte.setTipo(cuenta.getTipoCuenta());
                reporte.setSaldoInicial(saldoInicial);
                reporte.setEstado(cuenta.getEstado());
                reporte.setMovimiento("RETIRO".equalsIgnoreCase(movimiento.getTipoMovimiento()) ?
                        -movimiento.getValor() : movimiento.getValor());
                reporte.setSaldoDisponible(saldoDisponible);

                // Agregar el registro a la lista del reporte
                reporteList.add(reporte);
            }
        }
        return reporteList;
    }

    // Método para convertir String a Date
    private Date parseFecha(String fecha) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
        } catch (ParseException e) {
            throw new RuntimeException("Fecha inválida.");
        }
    }

    // Método para formatear la fecha
    private String formatFecha(Date fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(fecha);
    }
}