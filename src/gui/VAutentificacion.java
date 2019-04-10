/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * VAutentificacion.java
 *
 * Created on 16-feb-2011, 17:52:08
 */
package gui;

import static java.lang.System.exit;

/**
 *
 * @author basesdatos
 */
public class VAutentificacion extends javax.swing.JDialog {

    private aplicacion.FachadaAplicacion fa;
    private VPrincipal vp;

    /**
     * Creates new form VAutentificacion
     * @param parent
     * @param modal
     * @param fa
     */
    public VAutentificacion(java.awt.Frame parent, boolean modal, aplicacion.FachadaAplicacion fa, VPrincipal vp) {
        super(parent, modal);
        this.vp = vp;
        this.fa = fa;
        initComponents();
        this.setLocationRelativeTo(null);//para situar la ventana en el centro
        etiquetaFallo.setVisible(false);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            }
        });
        
    }
    
    private void initStyle(){
        btnAceptar.setBorderPainted(false); 
        btnAceptar.setContentAreaFilled(false); 
        btnAceptar.setFocusPainted(false); 
        btnAceptar.setOpaque(false);
        
        btnCancelar.setBorderPainted(false); 
        btnCancelar.setContentAreaFilled(false); 
        btnCancelar.setFocusPainted(false); 
        btnCancelar.setOpaque(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jLabel1 = new javax.swing.JLabel();
        usuarioTxt = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        etiquetaFallo = new javax.swing.JLabel();
        contrasenaTxt = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Autentificación de usuarios");
        setBackground(new java.awt.Color(204, 204, 204));
        setModal(true);
        setName("VAutentificacion"); // NOI18N
        setResizable(false);

        jLabel1.setText("Usuario:");

        usuarioTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usuarioTxtActionPerformed(evt);
            }
        });

        jLabel2.setText("Contraseña:");

        btnAceptar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/corazon_peq.png"))); // NOI18N
        btnAceptar.setText("Aceptar");
        btnAceptar.setContentAreaFilled(false);
        btnAceptar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAceptar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/corazon_peq.png"))); // NOI18N
        btnAceptar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cruz_peq.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setContentAreaFilled(false);
        btnCancelar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCancelar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cruz_peq.png"))); // NOI18N
        btnCancelar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        etiquetaFallo.setForeground(new java.awt.Color(255, 51, 51));
        etiquetaFallo.setText("Autentificación incorrecta!");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logo.png"))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Liberation Serif", 3, 18)); // NOI18N
        jLabel4.setText("WELCOME TO INFO-TINDER");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(139, 139, 139)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(104, 104, 104))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(contrasenaTxt, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(88, 88, 88)
                                .addComponent(etiquetaFallo))
                            .addComponent(usuarioTxt, javax.swing.GroupLayout.Alignment.LEADING))
                        .addContainerGap(61, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usuarioTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(contrasenaTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(etiquetaFallo)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAceptar)
                    .addComponent(btnCancelar))
                .addGap(41, 41, 41))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void usuarioTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usuarioTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usuarioTxtActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        if (fa.comprobarAutentificacion(usuarioTxt.getText(), contrasenaTxt.getText())){
            System.out.println("Logeado correctamente");
            vp.setVisible(true);
            this.dispose();
            
        }
        System.out.println(contrasenaTxt.getText());
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        exit(0);
    }//GEN-LAST:event_btnCancelarActionPerformed

    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JPasswordField contrasenaTxt;
    private javax.swing.JLabel etiquetaFallo;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField usuarioTxt;
    // End of variables declaration//GEN-END:variables

}
