/*
 * The MIT License
 *
 * Copyright 2024 imarc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package gui.formularios;

import aplicacion.FachadaAplicacion;

/**
 *
 * @author imarc
 */
public final class VCreacionActividad extends javax.swing.JFrame {

    FachadaAplicacion fa;
    /**
     * Creates new form VCreacionCategoria
     */
    public VCreacionActividad(FachadaAplicacion fa)
    {
        this.fa = fa;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblNombreNuevaCat = new javax.swing.JLabel();
        lblDescrNuevaCat = new javax.swing.JLabel();
        txtNombreNuevaAct = new javax.swing.JTextField();
        txtTipoNuevaAct = new javax.swing.JTextField();
        lblTipoNuevaAct = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescrNuevaCat = new javax.swing.JTextArea();
        lblCustomNuevaCat = new javax.swing.JLabel();
        btnValidarNombreNuevaCategoria = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnGuardarNuevaCategoria = new javax.swing.JButton();
        checknombreValido = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Asistente de Creación de Categorías");
        setAlwaysOnTop(true);
        setModalExclusionType(java.awt.Dialog.ModalExclusionType.TOOLKIT_EXCLUDE);
        setName("frameCrearCategoria"); // NOI18N
        setType(java.awt.Window.Type.POPUP);

        lblNombreNuevaCat.setFont(new java.awt.Font("IM FELL DW Pica PRO", 0, 18)); // NOI18N
        lblNombreNuevaCat.setText("Nombre");
        lblNombreNuevaCat.setName("lblNombreNuevaCat"); // NOI18N

        lblDescrNuevaCat.setFont(new java.awt.Font("IM FELL DW Pica PRO", 0, 18)); // NOI18N
        lblDescrNuevaCat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDescrNuevaCat.setText("Descripción:");
        lblDescrNuevaCat.setName("lblDescrNuevaCat"); // NOI18N

        txtNombreNuevaAct.setToolTipText("Nombre de la nueva actividad");

        txtTipoNuevaAct.setToolTipText("Tipo de la nueva actividad");

        lblTipoNuevaAct.setFont(new java.awt.Font("IM FELL DW Pica PRO", 0, 18)); // NOI18N
        lblTipoNuevaAct.setText("Tipo");
        lblTipoNuevaAct.setName("lblTipo"); // NOI18N

        txtDescrNuevaCat.setColumns(20);
        txtDescrNuevaCat.setRows(5);
        txtDescrNuevaCat.setToolTipText("Descripcion de la nueva actividad");
        jScrollPane1.setViewportView(txtDescrNuevaCat);
        txtDescrNuevaCat.getAccessibleContext().setAccessibleName("Descripcion nueva actividad");

        lblCustomNuevaCat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCustomNuevaCat.setEnabled(false);

        btnValidarNombreNuevaCategoria.setText("Validar");
        btnValidarNombreNuevaCategoria.setToolTipText("Comprueba que no exista el nombre");
        btnValidarNombreNuevaCategoria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnValidarNombreNuevaCategoriaMouseClicked(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.setToolTipText("Salir del asistente de creación");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnGuardarNuevaCategoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/yunque3050.png"))); // NOI18N
        btnGuardarNuevaCategoria.setToolTipText("Guardar");
        btnGuardarNuevaCategoria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarNuevaCategoriaMouseClicked(evt);
            }
        });
        btnGuardarNuevaCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarNuevaCategoriaActionPerformed(evt);
            }
        });

        checknombreValido.setBackground(new java.awt.Color(255, 255, 255));
        checknombreValido.setForeground(new java.awt.Color(153, 0, 0));
        checknombreValido.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(btnGuardarNuevaCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnCancelar))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(lblTipoNuevaAct, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtTipoNuevaAct))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(lblNombreNuevaCat, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtNombreNuevaAct, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(checknombreValido))
                                    .addComponent(lblCustomNuevaCat, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(50, 50, 50)
                                .addComponent(btnValidarNombreNuevaCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDescrNuevaCat, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblNombreNuevaCat)
                                    .addComponent(txtNombreNuevaAct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(checknombreValido)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCustomNuevaCat, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTipoNuevaAct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTipoNuevaAct)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnValidarNombreNuevaCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(lblDescrNuevaCat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardarNuevaCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblNombreNuevaCat.getAccessibleContext().setAccessibleDescription("");
        lblDescrNuevaCat.getAccessibleContext().setAccessibleName("Descripción");
        txtNombreNuevaAct.getAccessibleContext().setAccessibleName("Nombre de actividad");
        txtTipoNuevaAct.getAccessibleContext().setAccessibleName("Tipo actividad");
        btnGuardarNuevaCategoria.getAccessibleContext().setAccessibleName("Guardar Nueva Categoria");
        btnGuardarNuevaCategoria.getAccessibleContext().setAccessibleDescription("Guarda una nueva categoría");
        checknombreValido.getAccessibleContext().setAccessibleName("Es valido el nombre");
        checknombreValido.getAccessibleContext().setAccessibleDescription("tick en caso de nombre valido. destickeado por defecto");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnValidarNombreNuevaCategoriaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnValidarNombreNuevaCategoriaMouseClicked
        String nombre = txtNombreNuevaAct.getText();
        if (nombre.isEmpty()) {
            lblCustomNuevaCat.setText("El nombre no puede estar vacío");
            // pongo el color de la letra en rojo
            lblCustomNuevaCat.setBackground(new java.awt.Color(153, 0, 0));
            checknombreValido.setSelected(false);
            return;
        }
        if(fa.existeActividad(nombre)){
            lblCustomNuevaCat.setText("El nombre ya existe");
            // pongo el color de la letra en rojo
            lblCustomNuevaCat.setForeground(new java.awt.Color(153, 0, 0));
            checknombreValido.setSelected(false);
            return;
        }
        lblCustomNuevaCat.setText("Nombre válido");
        // pongo el color de la letra en verde
        lblCustomNuevaCat.setForeground(new java.awt.Color(0, 153, 0));
        checknombreValido.setSelected(true);
    }//GEN-LAST:event_btnValidarNombreNuevaCategoriaMouseClicked

    private void btnGuardarNuevaCategoriaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarNuevaCategoriaMouseClicked
        String nombre = txtNombreNuevaAct.getText();
        String tipo = txtTipoNuevaAct.getText();
        String descripcion = txtDescrNuevaCat.getText();
        if (nombre.isEmpty()) {
            lblCustomNuevaCat.setText("El nombre no puede estar vacío");
            // pongo el color de la letra en rojo
            lblCustomNuevaCat.setBackground(new java.awt.Color(153, 0, 0));
            checknombreValido.setSelected(false);
            return;
        }
        if(fa.existeActividad(nombre)){
            lblCustomNuevaCat.setText("El nombre ya existe");
            // pongo el color de la letra en rojo
            lblCustomNuevaCat.setForeground(new java.awt.Color(153, 0, 0));
            checknombreValido.setSelected(false);
            return;
        }

        lblCustomNuevaCat.setVisible(false);
        fa.crearActividad(nombre, descripcion, tipo);
        this.dispose();
    }//GEN-LAST:event_btnGuardarNuevaCategoriaMouseClicked

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarNuevaCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarNuevaCategoriaActionPerformed
        btnGuardarNuevaCategoriaMouseClicked(null);
    }//GEN-LAST:event_btnGuardarNuevaCategoriaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardarNuevaCategoria;
    private javax.swing.JButton btnValidarNombreNuevaCategoria;
    private javax.swing.JCheckBox checknombreValido;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCustomNuevaCat;
    private javax.swing.JLabel lblDescrNuevaCat;
    private javax.swing.JLabel lblNombreNuevaCat;
    private javax.swing.JLabel lblTipoNuevaAct;
    private javax.swing.JTextArea txtDescrNuevaCat;
    private javax.swing.JTextField txtNombreNuevaAct;
    private javax.swing.JTextField txtTipoNuevaAct;
    // End of variables declaration//GEN-END:variables
}
