package cuenta_movimientos.com.service.impl;

import cuenta_movimientos.com.dto.request.MovimientoDTO;
import cuenta_movimientos.com.dto.response.MovimientoResponseDTO;
import cuenta_movimientos.com.exception.SaldoNoDisponibleException;
import cuenta_movimientos.com.model.Cuenta;
import cuenta_movimientos.com.model.Movimiento;
import cuenta_movimientos.com.repository.interfaces.CuentaRepository;
import cuenta_movimientos.com.repository.interfaces.MovimientoRepository;
import cuenta_movimientos.com.service.interfaces.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MovimientoServiceImp implements MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    // **Crear nuevo movimiento**
    @Override
    public MovimientoResponseDTO createMovimiento(MovimientoDTO movimientoDTO) {
        // Verificar si la cuenta existe
        Cuenta cuenta = cuentaRepository.findById(movimientoDTO.getCuentaId())
                .orElseThrow(() -> new RuntimeException("Cuenta con ID " + movimientoDTO.getCuentaId() + " no encontrada."));

        // Calcular el saldo actual considerando los movimientos previos, sin alterar el saldo inicial
        Double saldoActual = calcularSaldoActual(cuenta);

        // Verificar si hay saldo disponible para el retiro y evitar saldo negativo
        if ("RETIRO".equalsIgnoreCase(movimientoDTO.getTipoMovimiento()) && saldoActual < movimientoDTO.getValor()) {
            throw new SaldoNoDisponibleException("Saldo no disponible.");
        }

        // Se crea una nueva instancia de Movimiento
        Movimiento movimiento = new Movimiento();
        movimiento.setFecha(new Date());
        movimiento.setTipoMovimiento(movimientoDTO.getTipoMovimiento());
        movimiento.setValor(movimientoDTO.getValor());

        // Calcular el nuevo saldo basado en el tipo de movimiento
        Double nuevoSaldo;

        if ("DEPOSITO".equalsIgnoreCase(movimientoDTO.getTipoMovimiento())) {
            nuevoSaldo = saldoActual + movimientoDTO.getValor();
        } else if ("RETIRO".equalsIgnoreCase(movimientoDTO.getTipoMovimiento())) {
            nuevoSaldo =  saldoActual - movimientoDTO.getValor();
        } else {
            throw new SaldoNoDisponibleException("Error tipo retiro.");
        }
        movimiento.setSaldo(nuevoSaldo);
        movimiento.setCuenta(cuenta);

        // Guardar el movimiento
        Movimiento movimientoGuardado = movimientoRepository.save(movimiento);

        return mapToResponseDTO(movimientoGuardado);
    }

    private Double calcularSaldoActual(Cuenta cuenta) {
        // Obtener todos los movimientos de la cuenta
        List<Movimiento> movimientos = movimientoRepository.findByCuentaId(cuenta.getId());

        // Calcular el saldo sumando depósitos y restando retiros
        Double saldoActual = cuenta.getSaldoInicial(); // Empezamos con el saldo inicial
        for (Movimiento mov : movimientos) {
            if ("DEPOSITO".equalsIgnoreCase(mov.getTipoMovimiento())) {
                saldoActual += mov.getValor();
            } else if ("RETIRO".equalsIgnoreCase(mov.getTipoMovimiento())) {
                saldoActual -= mov.getValor();
            }
        }
        return saldoActual;
    }

    // **Actualizar movimiento existente**
    @Override
    public MovimientoResponseDTO updateMovimiento(Long id, MovimientoDTO movimientoDTO) {
        // Verificar si el movimiento existe
        Movimiento movimientoExistente = movimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimiento con ID " + id + " no encontrado."));

        // Verificar si la cuenta existe
        Cuenta cuenta = cuentaRepository.findById(movimientoDTO.getCuentaId())
                .orElseThrow(() -> new RuntimeException("Cuenta con ID " + movimientoDTO.getCuentaId() + " no encontrada."));

        // Verificar saldo disponible al actualizar un retiro
        if ("RETIRO".equalsIgnoreCase(movimientoDTO.getTipoMovimiento()) && cuenta.getSaldoInicial() < movimientoDTO.getValor()) {
            throw new RuntimeException("Saldo no disponible.");
        }

        // Mapeo manual de DTO a entidad
        movimientoExistente.setTipoMovimiento(movimientoDTO.getTipoMovimiento());
        movimientoExistente.setValor(movimientoDTO.getValor());
        movimientoExistente.setCuenta(cuenta);

        // Ajustar saldo de la cuenta
        if ("DEPOSITO".equalsIgnoreCase(movimientoDTO.getTipoMovimiento())) {
            cuenta.setSaldoInicial(cuenta.getSaldoInicial() + movimientoDTO.getValor());
        } else if ("RETIRO".equalsIgnoreCase(movimientoDTO.getTipoMovimiento())) {
            cuenta.setSaldoInicial(cuenta.getSaldoInicial() - movimientoDTO.getValor());
        }
        cuentaRepository.save(cuenta);

        // Guardar movimiento actualizado
        Movimiento movimientoActualizado = movimientoRepository.save(movimientoExistente);

        return mapToResponseDTO(movimientoActualizado);
    }

    // **Obtener todos los movimientos**
    @Override
    public List<MovimientoResponseDTO> getAllMovimientos() {
        List<Movimiento> movimientos = movimientoRepository.findAll();
        List<MovimientoResponseDTO> responseDTOs = new ArrayList<>();

        // Mapeo manual de Entity a ResponseDTO
        for (Movimiento movimiento : movimientos) {
            responseDTOs.add(mapToResponseDTO(movimiento));
        }

        return responseDTOs;
    }

    // **Obtener movimiento por ID**
    @Override
    public MovimientoResponseDTO getMovimientoById(Long id) {
        Optional<Movimiento> movimientoOpt = movimientoRepository.findById(id);

        Movimiento movimiento = movimientoOpt.orElseThrow(() ->
                new RuntimeException("Movimiento con ID " + id + " no encontrado."));

        return mapToResponseDTO(movimiento);
    }

    // **Eliminar movimiento por ID**
    @Override
    public void deleteMovimiento(Long id) {
        // Verificar si el movimiento existe antes de eliminar
        if (!movimientoRepository.existsById(id)) {
            throw new RuntimeException("Movimiento con ID " + id + " no encontrado para eliminar.");
        }

        movimientoRepository.deleteById(id);
    }

    @Override
    public List<MovimientoDTO> getMovimientosByCuentaId(Long cuentaId) {
        return List.of();
    }

    private MovimientoResponseDTO mapToResponseDTO(Movimiento movimiento) {
        // Convertir a ResponseDTO
        MovimientoResponseDTO responseDTO = new MovimientoResponseDTO();
        responseDTO.setId(movimiento.getId());
        responseDTO.setFecha(convertToLocalDate(movimiento.getFecha()));
        responseDTO.setTipoMovimiento(movimiento.getTipoMovimiento());
        responseDTO.setValor(movimiento.getValor());
        responseDTO.setSaldo(movimiento.getSaldo());
        responseDTO.setCuentaId(movimiento.getCuenta().getId());
        return responseDTO;
    }

    private LocalDate convertToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
