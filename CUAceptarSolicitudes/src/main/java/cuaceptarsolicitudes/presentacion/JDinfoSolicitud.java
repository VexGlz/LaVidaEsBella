package cuaceptarsolicitudes.presentacion;

import cuaceptarsolicitudes.dtos.SolicitudDTO;
import java.time.format.DateTimeFormatter;

/**
 * Diálogo para mostrar detalles de una solicitud de adopción
 * 
 * @author Josel
 */
public class JDinfoSolicitud extends javax.swing.JDialog {

    private SolicitudDTO solicitud;

    public JDinfoSolicitud(java.awt.Frame parent, boolean modal, SolicitudDTO solicitud) {
        super(parent, modal);
        this.solicitud = solicitud;
        initComponents();
        cargarDatos();
    }

    private void cargarDatos() {
        if (solicitud != null) {
            lblNomUsuario.setText(solicitud.getNombreUsuario() != null ? solicitud.getNombreUsuario() : "N/A");
            lblNombreMascotallenar.setText(
                    solicitud.getNombreMascota() != null ? solicitud.getNombreMascota() : "N/A");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            if (solicitud.getFechaSolicitud() != null) {
                lblHoraSolicitudllenar.setText(solicitud.getFechaSolicitud().format(formatter));
            } else {
                lblHoraSolicitudllenar.setText("N/A");
            }

            if (solicitud.getFechaCita() != null) {
                lblHoraCitaLLenar.setText(solicitud.getFechaCita().format(formatter));
            } else {
                lblHoraCitaLLenar.setText("Sin cita asignada");
            }

            lblEstadoLlenar.setText(solicitud.getEstado() != null ? solicitud.getEstado() : "PENDIENTE");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lblCliente = new javax.swing.JLabel();
        lblHoraSolicitud = new javax.swing.JLabel();
        lblHoraCita = new javax.swing.JLabel();
        lblMascota = new javax.swing.JLabel();
        lblNomUsuario = new javax.swing.JLabel();
        lblHoraSolicitudllenar = new javax.swing.JLabel();
        lblHoraCitaLLenar = new javax.swing.JLabel();
        lblNombreMascotallenar = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        lblEstadoLlenar = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnRegresar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Detalles de Solicitud");

        jPanel1.setBackground(new java.awt.Color(219, 213, 195));

        jPanel2.setBackground(new java.awt.Color(219, 213, 195));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        lblCliente.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblCliente.setText("Nombre del usuario");

        lblHoraSolicitud.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblHoraSolicitud.setText("Hora de la solicitud");

        lblHoraCita.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblHoraCita.setText("Hora de la cita");

        lblMascota.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblMascota.setText("Nombre mascota");

        lblNomUsuario.setFont(new java.awt.Font("Segoe UI", 1, 14));
        lblNomUsuario.setText("...");

        lblHoraSolicitudllenar.setFont(new java.awt.Font("Segoe UI", 1, 14));
        lblHoraSolicitudllenar.setText("...");

        lblHoraCitaLLenar.setFont(new java.awt.Font("Segoe UI", 1, 14));
        lblHoraCitaLLenar.setText("...");

        lblNombreMascotallenar.setFont(new java.awt.Font("Segoe UI", 1, 14));
        lblNombreMascotallenar.setText("...");

        lblEstado.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblEstado.setText("Estado");

        lblEstadoLlenar.setFont(new java.awt.Font("Segoe UI", 1, 14));
        lblEstadoLlenar.setText("...");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                        false)
                                        .addComponent(lblCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 150,
                                                Short.MAX_VALUE)
                                        .addComponent(lblHoraSolicitud, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblHoraCita, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblMascota, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblEstado, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                        false)
                                        .addComponent(lblHoraSolicitudllenar, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                200, Short.MAX_VALUE)
                                        .addComponent(lblNomUsuario, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblHoraCitaLLenar, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblNombreMascotallenar, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblEstadoLlenar, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(127, Short.MAX_VALUE)));
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblCliente)
                                        .addComponent(lblNomUsuario))
                                .addGap(25, 25, 25)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblHoraSolicitud)
                                        .addComponent(lblHoraSolicitudllenar))
                                .addGap(25, 25, 25)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblHoraCita)
                                        .addComponent(lblHoraCitaLLenar))
                                .addGap(25, 25, 25)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblMascota)
                                        .addComponent(lblNombreMascotallenar))
                                .addGap(25, 25, 25)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblEstado)
                                        .addComponent(lblEstadoLlenar))
                                .addContainerGap(50, Short.MAX_VALUE)));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36));
        jLabel1.setText("Detalles de Solicitud");

        btnRegresar.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnRegresar.setText("Cerrar");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1)
                                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 100,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(100, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jLabel1)
                                .addGap(30, 30, 30)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(btnRegresar)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRegresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblEstadoLlenar;
    private javax.swing.JLabel lblHoraCita;
    private javax.swing.JLabel lblHoraCitaLLenar;
    private javax.swing.JLabel lblHoraSolicitud;
    private javax.swing.JLabel lblHoraSolicitudllenar;
    private javax.swing.JLabel lblMascota;
    private javax.swing.JLabel lblNomUsuario;
    private javax.swing.JLabel lblNombreMascotallenar;
    // End of variables declaration//GEN-END:variables
}
