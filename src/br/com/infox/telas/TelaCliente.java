/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infox.telas;

/**
 *
 * @author carlos
 *
 *
 */
import java.sql.*;
import br.com.infox.dal.ModuloConexao;
import javax.swing.JOptionPane;
// abaixo importanto recursos da biblioteca rs2xml.jar
import net.proteanit.sql.DbUtils;

public class TelaCliente extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaCliente
     */
    public TelaCliente() {
        initComponents();

        conexao = ModuloConexao.conector();

    }

    //////////////////
    // create / adicionar clientes a tabela clientes metodo adicionar
    private void adicionar() {

        String sql = "insert into tbclientes(nomecli,endcli,fonecli,emailcli) values(?,?,?,?)";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCliNome.getText());
            pst.setString(2, txtCliEndereco.getText());
            pst.setString(3, txtCliFone.getText());
            pst.setString(4, txtCliEmail.getText());

            // a linha abaixo faz a validação dos campos obrigatorios
            if ((txtCliNome.getText().isEmpty()) || (txtCliEndereco.getText().isEmpty()) || (txtCliFone.getText().isEmpty()) || (txtCliEmail.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Todos os campos são obrigatórios");

            } else {

                // a linha abaixo atualiza o banco de dados
                //pst.executeUpdate();
                // a linha a baixo atualiza e informa que o usuario foi adicionado com sucesso
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente adicionado com sucesso");
                    //////////////////////////////////////////////////////
                    // limpando campos após adicionar Cliente
                    txtCliId.setText(null);
                    txtCliNome.setText(null);
                    txtCliEndereco.setText(null);
                    txtCliFone.setText(null);
                    txtCliEmail.setText(null);

                    /////////////////////////////////////////////////////
                }
            } // else

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }

    }

    //////////////////
    // metodo para pesquisar clientes pelo nome com filtro
    private void pesquisar_cliente() {

        String sql = "select * from tbclientes where nomecli like ?";

        try {

            pst = conexao.prepareStatement(sql);
            // passando conteudo da caixa de pesquisa para o "?"
            // atenção ao "%" continuação da String sql
            pst.setString(1, txtCliPesquisar.getText() + "%");
            rs = pst.executeQuery();
            // a linha abaixo usa a bibioteca rsxml.jar para preencher a tabela
            tblClientes.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    // metodo para setar os campos do formulario com o conteúdo da tabela
    private void setar_campos() {

        int setar = tblClientes.getSelectedRow();
        txtCliId.setText(tblClientes.getModel().getValueAt(setar, 0).toString());
        txtCliNome.setText(tblClientes.getModel().getValueAt(setar, 1).toString());
        txtCliEndereco.setText(tblClientes.getModel().getValueAt(setar, 2).toString());
        txtCliFone.setText(tblClientes.getModel().getValueAt(setar, 3).toString());
        txtCliEmail.setText(tblClientes.getModel().getValueAt(setar, 4).toString());

        // desabilitando o botão adicionar cliente
        btnAdicionar.setEnabled(false);

    }
    //////////

    // chamando metodo alterar
    private void alterar() {

        String sql = "update tbclientes set nomecli=?, endcli=?, fonecli=?, emailcli=?  where idcli=?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCliNome.getText());
            pst.setString(2, txtCliEndereco.getText());
            pst.setString(3, txtCliFone.getText());
            pst.setString(4, txtCliEmail.getText());
            pst.setString(5, txtCliId.getText());

            ///
            if ((txtCliNome.getText().isEmpty()) || (txtCliEndereco.getText().isEmpty()) || (txtCliFone.getText().isEmpty()) || (txtCliEmail.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Todos os campos são obrigatórios");

            } else {

                // a linha abaixo atualiza o banco de dados
                // pst.executeUpdate();
                // a linha a baixo atualiza e informa que o usuario foi adicionado com sucesso
                int alterando = pst.executeUpdate();
                if (alterando > 0) {
                    JOptionPane.showMessageDialog(null, "Dados alterados com sucesso");

                    //////////////////////////////////////////////////////
                    // limpando campos após adicionar usuario
                    txtCliId.setText(null);
                    txtCliNome.setText(null);
                    txtCliEndereco.setText(null);
                    txtCliFone.setText(null);
                    txtCliEmail.setText(null);
                    // abilitando o botão adicionar cliente
                    btnAdicionar.setEnabled(true);

                    /////////////////////////////////////////////////////
                }
            } // else

            ///
        } catch (Exception e) {
        }

    } // alterar

    // metodo usado para a remoção de clientes no banco de dados
    private void remover() {
        // alinha a baixo faz uma confirmação sobre a remoção
        int confirma = JOptionPane.showConfirmDialog(null, "Realmente deseja remover este cliente?", "Atentenção", JOptionPane.YES_NO_OPTION);

        if (confirma == JOptionPane.YES_OPTION) {

            try {
                String sql = "delete from tbclientes where idcli=?";
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtCliId.getText());
                int apagado = pst.executeUpdate();

                if (apagado > 0) {

                    JOptionPane.showMessageDialog(null, "Cliente foi removido com sucesso!");

                    // limpando campo
                    txtCliId.setText(null);
                    txtCliNome.setText(null);
                    txtCliEndereco.setText(null);
                    txtCliFone.setText(null);
                    txtCliEmail.setText(null);
                    // abilitando o botão adicionar cliente
                    btnAdicionar.setEnabled(true);

                    //////////////////////////////////// teste
                } else {
                    JOptionPane.showMessageDialog(null, "Os campos estão Vazios!");

                    // limpando campo
                }

                /////////////////////// teste
                // } // if
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);

            }

        }

    }

    // metodo limpar campos clientes
    private void limpar_campos_cliente() {
        txtCliId.setText(null);
        txtCliNome.setText(null);
        txtCliEndereco.setText(null);
        txtCliFone.setText(null);
        txtCliEmail.setText(null);

        // abilitando o botão adicionar cliente
        btnAdicionar.setEnabled(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtCliPesquisar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCliNome = new javax.swing.JTextField();
        txtCliEndereco = new javax.swing.JTextField();
        txtCliFone = new javax.swing.JTextField();
        txtCliEmail = new javax.swing.JTextField();
        btnAdicionar = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnRemover = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblLimparCamposCliente = new javax.swing.JButton();
        txtCliId = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Clientes");
        setPreferredSize(new java.awt.Dimension(750, 600));

        txtCliPesquisar.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.orange));
        txtCliPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCliPesquisarKeyReleased(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/search.png"))); // NOI18N

        tblClientes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClientes);

        jLabel2.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel2.setText("* Nome");

        jLabel3.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel3.setText("* Endereço");

        jLabel4.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel4.setText("* Telefone");

        jLabel5.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel5.setText("* Email");

        txtCliNome.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtCliEndereco.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtCliFone.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtCliEmail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/create.png"))); // NOI18N
        btnAdicionar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAdicionar.setPreferredSize(new java.awt.Dimension(85, 80));
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        btnAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/update.png"))); // NOI18N
        btnAlterar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAlterar.setPreferredSize(new java.awt.Dimension(85, 80));
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });

        btnRemover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/delete.png"))); // NOI18N
        btnRemover.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnRemover.setPreferredSize(new java.awt.Dimension(85, 80));
        btnRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel6.setText("Adicionar");

        jLabel7.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel7.setText("Alterar");

        jLabel8.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel8.setText("Excluir");

        lblLimparCamposCliente.setFont(new java.awt.Font("Ubuntu", 2, 12)); // NOI18N
        lblLimparCamposCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/limpar.png"))); // NOI18N
        lblLimparCamposCliente.setText("Limpar Campos");
        lblLimparCamposCliente.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lblLimparCamposCliente.setPreferredSize(new java.awt.Dimension(90, 50));
        lblLimparCamposCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblLimparCamposClienteActionPerformed(evt);
            }
        });

        txtCliId.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        txtCliId.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCliId.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel6)
                        .addGap(73, 73, 73)
                        .addComponent(jLabel7)
                        .addGap(86, 86, 86)
                        .addComponent(jLabel8)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(btnRemover, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                        .addComponent(lblLimparCamposCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52))))
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jLabel2)
                                .addGap(45, 45, 45))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4))
                                .addGap(40, 40, 40)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCliEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCliNome, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCliFone, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCliEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(2, 2, 2)
                        .addComponent(txtCliPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCliPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(29, 29, 29)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCliNome, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCliEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCliFone, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCliEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lblLimparCamposCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnRemover, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel8)
                            .addGap(40, 40, 40))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel7)
                            .addContainerGap()))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addGap(41, 41, 41))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        // Chamando metodo apara adicionar clientes
        adicionar();

    }//GEN-LAST:event_btnAdicionarActionPerformed

    //  abaixo é o keyRealeased ou seja em quanto estiver digitanto faça.
    // referente ao metodo pesquisar_cliente
    private void txtCliPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliPesquisarKeyReleased
        // chamando o metodo pesquisar_cliente

        pesquisar_cliente();
    }//GEN-LAST:event_txtCliPesquisarKeyReleased

    // evento que sera usado para setar os campos da tabela (clicando com o mouse)
    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        // chamando o metodo para setar os campos
        setar_campos();
    }//GEN-LAST:event_tblClientesMouseClicked

    private void lblLimparCamposClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblLimparCamposClienteActionPerformed
        // chamando o metodo limpar_campos_cliente()

        limpar_campos_cliente();
    }//GEN-LAST:event_lblLimparCamposClienteActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        // chamando o metodo alterar
        alterar();
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        // Chamando o metodo remover
        remover();
    }//GEN-LAST:event_btnRemoverActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnRemover;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton lblLimparCamposCliente;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtCliEmail;
    private javax.swing.JTextField txtCliEndereco;
    private javax.swing.JTextField txtCliFone;
    private javax.swing.JTextField txtCliId;
    private javax.swing.JTextField txtCliNome;
    private javax.swing.JTextField txtCliPesquisar;
    // End of variables declaration//GEN-END:variables
}
