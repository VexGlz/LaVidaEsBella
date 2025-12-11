/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package presentacion;

import cubuscarmascotaideal.dtos.EncuestaDTO;
import cubuscarmascotaideal.control.ControlPresentacion;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;

/**
 *
 * @author Josel
 */
public class JPEncuesta extends javax.swing.JPanel {

    // ButtonGroups para manejar exclusividad de RadioButtons
    private ButtonGroup grupoAnimalesPeludos;
    private ButtonGroup grupoAlergias;
    private ButtonGroup grupoTamanoCasa;
    private ButtonGroup grupoCasaODepa;
    private ButtonGroup grupoSueldo;
    private ButtonGroup grupoTiempoFuera;

    // Listener para cuando se completa la encuesta
    private EncuestaListener listener;

    // Control de presentación
    private ControlPresentacion controlPresentacion;

    /**
     * Creates new form JPEncuesta
     */
    public JPEncuesta() {
        initComponents();
        inicializarButtonGroups();
        this.controlPresentacion = new ControlPresentacion();
    }

    /**
     * Establece el control de presentación
     */
    public void setControlPresentacion(ControlPresentacion controlPresentacion) {
        this.controlPresentacion = controlPresentacion;
    }

    /**
     * Inicializa los ButtonGroups para los RadioButtons
     */
    private void inicializarButtonGroups() {
        // Grupo: ¿Te gustan los animales peludos?
        grupoAnimalesPeludos = new ButtonGroup();
        grupoAnimalesPeludos.add(rbtnSIAnimalesPeludos);
        grupoAnimalesPeludos.add(rbtnNoAnimalesPeludos);

        // Grupo: ¿Tienes alergias?
        grupoAlergias = new ButtonGroup();
        grupoAlergias.add(rbtnSiAlergias);
        grupoAlergias.add(rbtnNoAlergias);

        // Grupo: ¿Tu casa/depa es grande?
        grupoTamanoCasa = new ButtonGroup();
        grupoTamanoCasa.add(rbtnSICasaGrande);
        grupoTamanoCasa.add(rbtnMasomenosCasaGrande);
        grupoTamanoCasa.add(rbtnNoCasaGrande);

        // Grupo: ¿Casa o Departamento?
        grupoCasaODepa = new ButtonGroup();
        grupoCasaODepa.add(rbtnCasa);
        grupoCasaODepa.add(rbtnDepa);

        // Grupo: ¿Dispuest@ a gastar bastante?
        grupoSueldo = new ButtonGroup();
        grupoSueldo.add(rbtnGastoBastante);
        grupoSueldo.add(rbtnNogastobastante);

        // Grupo: ¿Tiempo fuera de casa?
        grupoTiempoFuera = new ButtonGroup();
        grupoTiempoFuera.add(rbtnSITiempoFuera);
        grupoTiempoFuera.add(rbtnNotiempoFuera);
    }

    /**
     * Establece el listener para eventos de la encuesta
     */
    public void setEncuestaListener(EncuestaListener listener) {
        this.listener = listener;
    }

    /**
     * Valida que todas las preguntas hayan sido respondidas
     */
    private boolean validarRespuestas() {
        if (grupoAnimalesPeludos.getSelection() == null) {
            JOptionPane.showMessageDialog(this,
                    "Por favor responde: ¿Te gustan los animales peludos?",
                    "Pregunta sin responder", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (grupoAlergias.getSelection() == null) {
            JOptionPane.showMessageDialog(this,
                    "Por favor responde: ¿Tienes alergias?",
                    "Pregunta sin responder", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (grupoCasaODepa.getSelection() == null) {
            JOptionPane.showMessageDialog(this,
                    "Por favor responde: ¿Vives en casa o departamento?",
                    "Pregunta sin responder", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (grupoTamanoCasa.getSelection() == null) {
            JOptionPane.showMessageDialog(this,
                    "Por favor responde: ¿Tu casa/depa es grande?",
                    "Pregunta sin responder", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (grupoSueldo.getSelection() == null) {
            JOptionPane.showMessageDialog(this,
                    "Por favor responde: ¿Estás dispuest@ a gastar bastante?",
                    "Pregunta sin responder", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (grupoTiempoFuera.getSelection() == null) {
            JOptionPane.showMessageDialog(this,
                    "Por favor responde: ¿Pasarás mucho tiempo fuera de tu hogar?",
                    "Pregunta sin responder", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Obtiene los criterios de búsqueda basados en las respuestas
     */
    public EncuestaDTO obtenerCriterios() {
        if (!validarRespuestas()) {
            return null;
        }

        EncuestaDTO encuesta = new EncuestaDTO();

        // ¿Te gustan los animales peludos?
        encuesta.setPeludo(rbtnSIAnimalesPeludos.isSelected());

        // Tamaño de casa -> tamaño de mascota
        if (rbtnSICasaGrande.isSelected()) {
            encuesta.setTamano("grande");
        } else if (rbtnMasomenosCasaGrande.isSelected()) {
            encuesta.setTamano("mediano");
        } else {
            encuesta.setTamano("pequeño");
        }

        // Nivel de actividad (basado en tiempo fuera)
        if (rbtnSITiempoFuera.isSelected()) {
            encuesta.setNivelActividad("bajo"); // Poco tiempo en casa = mascota tranquila
        } else {
            encuesta.setNivelActividad("alto"); // Mucho tiempo en casa = mascota activa
        }

        // Costo de mantenimiento
        if (rbtnGastoBastante.isSelected()) {
            encuesta.setCostoMantenimiento("alto");
        } else {
            encuesta.setCostoMantenimiento("bajo");
        }

        return encuesta;
    }

    /**
     * Interfaz para escuchar eventos de la encuesta
     */
    public interface EncuestaListener {
        void onBusquedaCompletada(EncuestaDTO encuesta);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        lblAnimalesPeludos = new javax.swing.JLabel();
        rbtnNoAlergias = new javax.swing.JRadioButton();
        rbtnNoAnimalesPeludos = new javax.swing.JRadioButton();
        lblTamanoCasa = new javax.swing.JLabel();
        rbtnSIAnimalesPeludos = new javax.swing.JRadioButton();
        rbtnSiAlergias = new javax.swing.JRadioButton();
        lblAlergias = new javax.swing.JLabel();
        rbtnSICasaGrande = new javax.swing.JRadioButton();
        rbtnMasomenosCasaGrande = new javax.swing.JRadioButton();
        rbtnNoCasaGrande = new javax.swing.JRadioButton();
        lblCasaODepa = new javax.swing.JLabel();
        rbtnCasa = new javax.swing.JRadioButton();
        rbtnDepa = new javax.swing.JRadioButton();
        lblSueldo = new javax.swing.JLabel();
        rbtnGastoBastante = new javax.swing.JRadioButton();
        rbtnNogastobastante = new javax.swing.JRadioButton();
        btnBuscarMascotas = new javax.swing.JButton();
        lblTiempoFuera = new javax.swing.JLabel();
        rbtnSITiempoFuera = new javax.swing.JRadioButton();
        rbtnNotiempoFuera = new javax.swing.JRadioButton();

        jPanel3.setBackground(new java.awt.Color(219, 213, 195));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblAnimalesPeludos.setText("¿Te gustan los animales peludos?");
        jPanel3.add(lblAnimalesPeludos, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 120, 210, -1));

        rbtnNoAlergias.setText("No");
        rbtnNoAlergias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnNoAlergiasActionPerformed(evt);
            }
        });
        jPanel3.add(rbtnNoAlergias, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 180, -1, -1));

        rbtnNoAnimalesPeludos.setText("No");
        rbtnNoAnimalesPeludos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnNoAnimalesPeludosActionPerformed(evt);
            }
        });
        jPanel3.add(rbtnNoAnimalesPeludos, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 120, -1, -1));

        lblTamanoCasa.setText("¿Tu casa/depa es grande?");
        jPanel3.add(lblTamanoCasa, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 290, 210, -1));

        rbtnSIAnimalesPeludos.setText("SI");
        rbtnSIAnimalesPeludos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnSIAnimalesPeludosActionPerformed(evt);
            }
        });
        jPanel3.add(rbtnSIAnimalesPeludos, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 120, -1, -1));

        rbtnSiAlergias.setText("SI");
        rbtnSiAlergias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnSiAlergiasActionPerformed(evt);
            }
        });
        jPanel3.add(rbtnSiAlergias, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 180, -1, -1));

        lblAlergias.setText("¿Tienes alergias?");
        jPanel3.add(lblAlergias, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 180, 210, -1));

        rbtnSICasaGrande.setText("SI");
        jPanel3.add(rbtnSICasaGrande, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 290, -1, -1));

        rbtnMasomenosCasaGrande.setText("Maso menos");
        jPanel3.add(rbtnMasomenosCasaGrande, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 290, -1, -1));

        rbtnNoCasaGrande.setText("No");
        jPanel3.add(rbtnNoCasaGrande, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 290, -1, -1));

        lblCasaODepa.setText("¿Vives en una casa o en un departamento?");
        jPanel3.add(lblCasaODepa, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 240, -1, -1));

        rbtnCasa.setText("Casa");
        jPanel3.add(rbtnCasa, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 240, -1, -1));

        rbtnDepa.setText("Departamento");
        jPanel3.add(rbtnDepa, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 240, -1, -1));

        lblSueldo.setText("¿Estas dispuest@ a gastar bastante?");
        jPanel3.add(lblSueldo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, -1, -1));

        rbtnGastoBastante.setText("SI");
        jPanel3.add(rbtnGastoBastante, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 350, -1, -1));

        rbtnNogastobastante.setText("No");
        jPanel3.add(rbtnNogastobastante, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 350, -1, -1));

        btnBuscarMascotas.setText("Buscar");
        btnBuscarMascotas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarMascotasActionPerformed(evt);
            }
        });
        jPanel3.add(btnBuscarMascotas, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 450, -1, -1));

        lblTiempoFuera.setText("¿Pasaras mucho tiempo fuera de tu hogar?");
        jPanel3.add(lblTiempoFuera, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 400, -1, -1));

        rbtnSITiempoFuera.setText("Si");
        rbtnSITiempoFuera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnSITiempoFueraActionPerformed(evt);
            }
        });
        jPanel3.add(rbtnSITiempoFuera, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 400, -1, -1));

        rbtnNotiempoFuera.setText("No");
        jPanel3.add(rbtnNotiempoFuera, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 400, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1291, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 601, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarMascotasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarMascotasActionPerformed
        EncuestaDTO encuesta = obtenerCriterios();
        if (encuesta != null && listener != null) {
            listener.onBusquedaCompletada(encuesta);
        }
    }//GEN-LAST:event_btnBuscarMascotasActionPerformed

    private void rbtnNoAlergiasActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_rbtnNoAlergiasActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_rbtnNoAlergiasActionPerformed

    private void rbtnNoAnimalesPeludosActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_rbtnNoAnimalesPeludosActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_rbtnNoAnimalesPeludosActionPerformed

    private void rbtnSIAnimalesPeludosActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_rbtnSIAnimalesPeludosActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_rbtnSIAnimalesPeludosActionPerformed

    private void rbtnSiAlergiasActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_rbtnSiAlergiasActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_rbtnSiAlergiasActionPerformed

    private void rbtnSITiempoFueraActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_rbtnSITiempoFueraActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_rbtnSITiempoFueraActionPerformed

 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscarMascotas;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblAlergias;
    private javax.swing.JLabel lblAnimalesPeludos;
    private javax.swing.JLabel lblCasaODepa;
    private javax.swing.JLabel lblSueldo;
    private javax.swing.JLabel lblTamanoCasa;
    private javax.swing.JLabel lblTiempoFuera;
    private javax.swing.JRadioButton rbtnCasa;
    private javax.swing.JRadioButton rbtnDepa;
    private javax.swing.JRadioButton rbtnGastoBastante;
    private javax.swing.JRadioButton rbtnMasomenosCasaGrande;
    private javax.swing.JRadioButton rbtnNoAlergias;
    private javax.swing.JRadioButton rbtnNoAnimalesPeludos;
    private javax.swing.JRadioButton rbtnNoCasaGrande;
    private javax.swing.JRadioButton rbtnNogastobastante;
    private javax.swing.JRadioButton rbtnNotiempoFuera;
    private javax.swing.JRadioButton rbtnSIAnimalesPeludos;
    private javax.swing.JRadioButton rbtnSICasaGrande;
    private javax.swing.JRadioButton rbtnSITiempoFuera;
    private javax.swing.JRadioButton rbtnSiAlergias;
    // End of variables declaration//GEN-END:variables
}
