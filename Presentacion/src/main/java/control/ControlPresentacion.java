/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;


import control.ControlSubsistemas;
import DTOS.CitaDTO;
import DTOS.InfoPersonalDTO;
import DTOS.InfoViviendaDTO;
import DTOS.RazonesAntecedentesDTO;
import DTOS.SolicitudAdopcionDTO;
import DTOS.UsuarioDTO;
import gui.FrmCorreoConfirmacion;
import gui.FrmGenerarCita;
import gui.FrmInfoPersonal;
import gui.FrmInfoVivienda;
import gui.FrmInformacionMascota;
import gui.FrmMenuPrincipal;
import gui.FrmRazonesAntecedentes;
import gui.MenuMostrarEspecies;

import javax.swing.*;

public class ControlPresentacion {

    private ControlSubsistemas controlSubsistemas;

    // Referencias a las pantallas
    private MenuMostrarEspecies menuMostrarEspecies;
    private FrmInformacionMascota frmInformacionMascota;
    private FrmGenerarCita frmGenerarCita;
    private FrmInfoVivienda frmInfoVivienda;
    private FrmRazonesAntecedentes frmRazonesAntecedentes;
    private FrmInfoPersonal frmInfoPersonal;
    private FrmCorreoConfirmacion frmCorreoConfirmacion;
    private FrmMenuPrincipal frmMenuPrincipal;

    // Variables de contexto
    private String idMascotaSeleccionada;
    private String idUsuarioActual;
    private SolicitudAdopcionDTO solicitudActual;

    public ControlPresentacion() {
        this.controlSubsistemas = new ControlSubsistemas();
        this.idUsuarioActual = "USER001"; // Usuario de prueba
    }

    public void iniciarSistema() {
        mostrarMenuPrincipal();
    }

    public void mostrarMenuPrincipal() {
        if (frmMenuPrincipal == null) {
            frmMenuPrincipal = new FrmMenuPrincipal();
        }
        frmMenuPrincipal.setVisible(true);
    }

    public void mostrarMenuMascotas() {
        if (menuMostrarEspecies == null) {
            menuMostrarEspecies = new MenuMostrarEspecies();
        }
        menuMostrarEspecies.setVisible(true);
    }

    public void procesarSeleccionMascota(String idMascota) {
        this.idMascotaSeleccionada = idMascota;
        mostrarInformacionMascota(idMascota);
    }

    public void mostrarInformacionMascota(String idMascota) {
        if (menuMostrarEspecies != null) menuMostrarEspecies.setVisible(false);
        frmInformacionMascota = new FrmInformacionMascota();
        frmInformacionMascota.setVisible(true);
    }

    public void continuarConSolicitud() {
        cerrarVentana(frmInformacionMascota);
        mostrarInfoPersonal();
    }

    public void mostrarInfoPersonal() {
        frmInfoPersonal = new FrmInfoPersonal();
        frmInfoPersonal.setVisible(true);
    }

    public void guardarInfoPersonal(InfoPersonalDTO infoPersonal) {
        // Guardar en la solicitud actual
        if (solicitudActual == null) {
            solicitudActual = new SolicitudAdopcionDTO();
        }
        // TODO: Set mascota and usuario properly

        if (frmInfoPersonal != null) frmInfoPersonal.setVisible(false);
        mostrarInfoVivienda();
    }

    public void mostrarInfoVivienda() {
        frmInfoVivienda = new FrmInfoVivienda();
        frmInfoVivienda.setVisible(true);
    }

    public void guardarInfoVivienda(InfoViviendaDTO infoVivienda) {
        // TODO: Save info vivienda

        if (frmInfoVivienda != null) frmInfoVivienda.setVisible(false);
        mostrarRazonesAntecedentes();
    }

    public void mostrarRazonesAntecedentes() {
        frmRazonesAntecedentes = new FrmRazonesAntecedentes();
        frmRazonesAntecedentes.setVisible(true);
    }

    public void guardarRazonesAntecedentes(RazonesAntecedentesDTO razones) {
        // TODO: Save razones

        if (frmRazonesAntecedentes != null) frmRazonesAntecedentes.setVisible(false);
        mostrarGenerarCita();
    }

    public void mostrarGenerarCita() {
        frmGenerarCita = new FrmGenerarCita();
        frmGenerarCita.setVisible(true);
    }

    public void procesarCita(CitaDTO cita) {
        solicitudActual.setIdCita(cita.getId());

        // Procesar solicitud completa
        try {
            controlSubsistemas.procesarSolicitudCompleta(solicitudActual, cita);
            cerrarVentana(frmGenerarCita);
            mostrarConfirmacion();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error al procesar la solicitud: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void mostrarConfirmacion() {
        frmCorreoConfirmacion = new FrmCorreoConfirmacion();
        frmCorreoConfirmacion.setVisible(true);
    }

    public void finalizarProceso() {
        cerrarVentana(frmCorreoConfirmacion);
        solicitudActual = null;
        idMascotaSeleccionada = null;
        mostrarMenuPrincipal();
    }

    public void cancelarProceso() {
        int opcion = JOptionPane.showConfirmDialog(null,
                "¿Está seguro que desea cancelar el proceso de adopción?",
                "Confirmar Cancelación",
                JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            limpiarContexto();
            mostrarMenuPrincipal();
        }
    }

    private void limpiarContexto() {
        solicitudActual = null;
        idMascotaSeleccionada = null;
        cerrarTodasLasVentanas();
    }

    private void cerrarVentana(JFrame ventana) {
        if (ventana != null) {
            ventana.dispose();
        }
    }

    private void cerrarTodasLasVentanas() {
        if (menuMostrarEspecies != null) menuMostrarEspecies.setVisible(false);
        cerrarVentana(frmInformacionMascota);
        cerrarVentana(frmGenerarCita);
        if (frmInfoVivienda != null) frmInfoVivienda.setVisible(false);
        if (frmRazonesAntecedentes != null) frmRazonesAntecedentes.setVisible(false);
        if (frmInfoPersonal != null) frmInfoPersonal.setVisible(false);
        cerrarVentana(frmCorreoConfirmacion);
    }

    public ControlSubsistemas getControlSubsistemas() {
        return controlSubsistemas;
    }

    public String getIdUsuarioActual() {
        return idUsuarioActual;
    }
    
    /**
     * Valida las credenciales de login del usuario
     * @param correo Correo del usuario
     * @param password Contraseña del usuario
     * @return UsuarioDTO si las credenciales son válidas
     * @throws Exception si las credenciales son inválidas
     */
    public UsuarioDTO validarLogin(String correo, String password) throws Exception {
        return controlSubsistemas.validarLogin(correo, password);
    }
    
    /**
     * Registra un nuevo usuario en el sistema
     * @param usuario UsuarioDTO con los datos del nuevo usuario
     * @throws Exception si hay algún error en el registro
     */
    public void registrarUsuario(UsuarioDTO usuario) throws Exception {
        controlSubsistemas.registrarUsuario(usuario);
    }
}
