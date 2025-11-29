/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;


import presentacion.boundaries.*;
import Negocio.control.ControlSubsistemas;
import negocio.adopcionesdto.*;

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
            frmMenuPrincipal = new FrmMenuPrincipal(this);
        }
        frmMenuPrincipal.setVisible(true);
    }

    public void mostrarMenuMascotas() {
        if (menuMostrarEspecies == null) {
            menuMostrarEspecies = new MenuMostrarEspecies(this);
        }
        menuMostrarEspecies.setVisible(true);
    }

    public void procesarSeleccionMascota(String idMascota) {
        this.idMascotaSeleccionada = idMascota;
        mostrarInformacionMascota(idMascota);
    }

    public void mostrarInformacionMascota(String idMascota) {
        cerrarVentana(menuMostrarEspecies);
        frmInformacionMascota = new FrmInformacionMascota(this, idMascota);
        frmInformacionMascota.setVisible(true);
    }

    public void continuarConSolicitud() {
        cerrarVentana(frmInformacionMascota);
        mostrarInfoPersonal();
    }

    public void mostrarInfoPersonal() {
        frmInfoPersonal = new FrmInfoPersonal(this);
        frmInfoPersonal.setVisible(true);
    }

    public void guardarInfoPersonal(InfoPersonalDTO infoPersonal) {
        // Guardar en la solicitud actual
        if (solicitudActual == null) {
            solicitudActual = new SolicitudAdopcionDTO();
            solicitudActual.setIdMascota(idMascotaSeleccionada);
            solicitudActual.setIdUsuario(idUsuarioActual);
        }
        solicitudActual.setInfoPersonal(infoPersonal.toString());

        cerrarVentana(frmInfoPersonal);
        mostrarInfoVivienda();
    }

    public void mostrarInfoVivienda() {
        frmInfoVivienda = new FrmInfoVivienda(this);
        frmInfoVivienda.setVisible(true);
    }

    public void guardarInfoVivienda(InfoViviendaDTO infoVivienda) {
        solicitudActual.setInfoVivienda(infoVivienda.toString());

        cerrarVentana(frmInfoVivienda);
        mostrarRazonesAntecedentes();
    }

    public void mostrarRazonesAntecedentes() {
        frmRazonesAntecedentes = new FrmRazonesAntecedentes(this);
        frmRazonesAntecedentes.setVisible(true);
    }

    public void guardarRazonesAntecedentes(RazonesAntecedentesDTO razones) {
        solicitudActual.setRazonesAntecedentes(razones.toString());

        cerrarVentana(frmRazonesAntecedentes);
        mostrarGenerarCita();
    }

    public void mostrarGenerarCita() {
        frmGenerarCita = new FrmGenerarCita(this);
        frmGenerarCita.setVisible(true);
    }

    public void procesarCita(CitaDTO cita) {
        solicitudActual.setCita(cita.getId());

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
        frmCorreoConfirmacion = new FrmCorreoConfirmacion(this, solicitudActual);
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
        cerrarVentana(menuMostrarEspecies);
        cerrarVentana(frmInformacionMascota);
        cerrarVentana(frmGenerarCita);
        cerrarVentana(frmInfoVivienda);
        cerrarVentana(frmRazonesAntecedentes);
        cerrarVentana(frmInfoPersonal);
        cerrarVentana(frmCorreoConfirmacion);
    }

    public ControlSubsistemas getControlSubsistemas() {
        return controlSubsistemas;
    }

    public String getIdUsuarioActual() {
        return idUsuarioActual;
    }
}
