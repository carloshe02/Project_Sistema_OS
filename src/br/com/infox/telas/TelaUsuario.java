/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infox.telas;

/**
 *
 * @author carlos
 */
import java.sql.*;
import br.com.infox.dal.ModuloConexao;
import javax.swing.JOptionPane;

public class TelaUsuario extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaUsuario
     */
    public TelaUsuario() {
        initComponents();

        conexao = ModuloConexao.conector();

    }

    private void consultar() {

        String sql = "select * from tbusuarios where iduser=?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuId.getText());

            rs = pst.executeQuery();
            //
            if (rs.next()) {

                txtUsuNome.setText(rs.getString(2));
                txtUsuFone.setText(rs.getString(3));
                txtUsuLogin.setText(rs.getString(4));
                txtUsuSenha.setText(rs.getString(5));

                // a linha abaixo se refere ao combo box
                cboUsuPerfil.setSelectedItem(rs.getString(6));
                ////////

                txtUsuRg.setText(rs.getString(7)); // rg
                txtUsuEnd.setText(rs.getString(8)); // end

            } else {

                JOptionPane.showMessageDialog(null, " ID do Usuário não consta no banco de dados, Por favor, certifique-se de que deseja adiciona-lo");
                // as linhas a baixo limpam os campos
                txtUsuNome.setText(null);
                txtUsuFone.setText(null);
                txtUsuLogin.setText(null);
                txtUsuSenha.setText(null);

                txtUsuEnd.setText(null);
                txtUsuRg.setText(null);

            }

            //
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);

        }
    }

    // create / adicionar usuarios a tabela usuarios metodo adicionar
    private void adicionar() {

        String sql = "insert into tbusuarios(iduser,usuario,fone,login,senha,perfil,rg,endereco) values(?,?,?,?,?,?,?,?)";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuId.getText());
            pst.setString(2, txtUsuNome.getText());
            pst.setString(3, txtUsuFone.getText());
            pst.setString(4, txtUsuLogin.getText());
            pst.setString(5, txtUsuSenha.getText());
            pst.setString(6, cboUsuPerfil.getSelectedItem().toString());
            pst.setString(7, txtUsuRg.getText()); // rg
            pst.setString(8, txtUsuEnd.getText()); // end
            // a linha abaixo faz a validação dos campos obrigatorios
            if ((txtUsuId.getText().isEmpty()) || (txtUsuNome.getText().isEmpty()) || (txtUsuFone.getText().isEmpty()) || (txtUsuLogin.getText().isEmpty()) || (txtUsuSenha.getText().isEmpty()) || (txtUsuRg.getText().isEmpty()) || (txtUsuEnd.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Todos os campos são obrigatórios");

            } else {

                // a linha abaixo atualiza o banco de dados
                //pst.executeUpdate();
                // a linha a baixo atualiza e informa que o usuario foi adicionado com sucesso
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário adicionado com sucesso");
                    //////////////////////////////////////////////////////
                    // limpando campos após adicionar usuario
                    txtUsuId.setText(null);
                    txtUsuNome.setText(null);
                    txtUsuFone.setText(null);
                    txtUsuLogin.setText(null);
                    txtUsuSenha.setText(null);

                    txtUsuEnd.setText(null);
                    txtUsuRg.setText(null);

                    /////////////////////////////////////////////////////
                }
            } // else

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }

    }

    // chamando metodo alterar
    private void alterar() {

        String sql = "update tbusuarios set usuario=?, fone=?, login=?, senha=?, perfil=?, rg=?, endereco=? where iduser=?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuNome.getText());
            pst.setString(2, txtUsuFone.getText());
            pst.setString(3, txtUsuLogin.getText());
            pst.setString(4, txtUsuSenha.getText());
            pst.setString(5, cboUsuPerfil.getSelectedItem().toString());
            pst.setString(6, txtUsuRg.getText());
            pst.setString(7, txtUsuEnd.getText());
            pst.setString(8, txtUsuId.getText());
            ///

            if ((txtUsuId.getText().isEmpty()) || (txtUsuNome.getText().isEmpty()) || (txtUsuFone.getText().isEmpty()) || (txtUsuLogin.getText().isEmpty()) || (txtUsuSenha.getText().isEmpty()) || (txtUsuRg.getText().isEmpty()) || (txtUsuEnd.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Todos os campos são obrigatórios");

            } else {

                // a linha abaixo atualiza o banco de dados
                //pst.executeUpdate();
                // a linha a baixo atualiza e informa que o usuario foi adicionado com sucesso
                int alterando = pst.executeUpdate();
                if (alterando > 0) {
                    JOptionPane.showMessageDialog(null, "Dados alterados com sucesso");
                    //////////////////////////////////////////////////////
                    // limpando campos após adicionar usuario
                    txtUsuId.setText(null);
                    txtUsuNome.setText(null);
                    txtUsuFone.setText(null);
                    txtUsuLogin.setText(null);
                    txtUsuSenha.setText(null);

                    txtUsuEnd.setText(null);
                    txtUsuRg.setText(null);

                    /////////////////////////////////////////////////////
                }
            } // else

            ///
        } catch (Exception e) {
        }

    } // alterar

    // metodo usado para a remoção de usuários no banco de dados
    private void remover() {
        // alinha a baixo faz uma confirmação sobre a remoção
        int confirma = JOptionPane.showConfirmDialog(null, "Realmente deseja remover este usuário?", "Atentenção", JOptionPane.YES_NO_OPTION);

        if (confirma == JOptionPane.YES_OPTION) {

            try {
                String sql = "delete from tbusuarios where iduser=?";
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtUsuId.getText());
                int apagado = pst.executeUpdate();

                if (apagado > 0) {

                    JOptionPane.showMessageDialog(null, "Usuário foi removido com sucesso!");

                    // limpando campo
                    txtUsuId.setText(null);
                    txtUsuNome.setText(null);
                    txtUsuFone.setText(null);
                    txtUsuLogin.setText(null);
                    txtUsuSenha.setText(null);

                    txtUsuEnd.setText(null);
                    txtUsuRg.setText(null);

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

    // end
    // botão limpar metodo
    private void limparCampos() {

        txtUsuId.setText(null);
        txtUsuNome.setText(null);
        txtUsuFone.setText(null);
        txtUsuLogin.setText(null);
        txtUsuSenha.setText(null);

        txtUsuEnd.setText(null);
        txtUsuRg.setText(null);
    }
    // end

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtUsuId = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtUsuNome = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtUsuFone = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtUsuLogin = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtUsuSenha = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cboUsuPerfil = new javax.swing.JComboBox<>();
        btnUsuCreate = new javax.swing.JButton();
        btnUsuRead = new javax.swing.JButton();
        btnUsuUpdate = new javax.swing.JButton();
        btnUsuDelete = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtUsuRg = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtUsuEnd = new javax.swing.JTextField();
        btnLimparCampos = new javax.swing.JButton();

        jTextField1.setText("jTextField1");

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Usuários");

        jLabel2.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel2.setText("* ID");

        txtUsuId.setFont(new java.awt.Font("Ubuntu", 2, 15)); // NOI18N
        txtUsuId.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel4.setText("* Nome");

        txtUsuNome.setFont(new java.awt.Font("Ubuntu", 2, 15)); // NOI18N
        txtUsuNome.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel1.setText("* Fone");

        txtUsuFone.setFont(new java.awt.Font("Ubuntu", 2, 15)); // NOI18N
        txtUsuFone.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel5.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel5.setText("* Login");

        txtUsuLogin.setFont(new java.awt.Font("Ubuntu", 2, 15)); // NOI18N
        txtUsuLogin.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel6.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel6.setText("* Senha");

        txtUsuSenha.setFont(new java.awt.Font("Ubuntu", 2, 15)); // NOI18N
        txtUsuSenha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel7.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel7.setText("* Perfil");

        jLabel8.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(240, 147, 49));
        jLabel8.setText("[ Autenticações, consultas e alterações em usuários do sistema ]");

        cboUsuPerfil.setFont(new java.awt.Font("Ubuntu", 0, 15)); // NOI18N
        cboUsuPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "admin", "user" }));
        cboUsuPerfil.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(250, 133, 42)));

        btnUsuCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/create.png"))); // NOI18N
        btnUsuCreate.setToolTipText("Adicionar novo");
        btnUsuCreate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnUsuCreate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuCreate.setPreferredSize(new java.awt.Dimension(85, 80));
        btnUsuCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuCreateActionPerformed(evt);
            }
        });

        btnUsuRead.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/read.png"))); // NOI18N
        btnUsuRead.setToolTipText("Consultar");
        btnUsuRead.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnUsuRead.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuRead.setPreferredSize(new java.awt.Dimension(85, 80));
        btnUsuRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuReadActionPerformed(evt);
            }
        });

        btnUsuUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/update.png"))); // NOI18N
        btnUsuUpdate.setToolTipText("Alterar dados");
        btnUsuUpdate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnUsuUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuUpdate.setPreferredSize(new java.awt.Dimension(85, 80));
        btnUsuUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuUpdateActionPerformed(evt);
            }
        });

        btnUsuDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/delete.png"))); // NOI18N
        btnUsuDelete.setToolTipText("Excluir Usuário");
        btnUsuDelete.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnUsuDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuDelete.setPreferredSize(new java.awt.Dimension(85, 80));
        btnUsuDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuDeleteActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel9.setText("Adicionar");

        jLabel10.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel10.setText("Consultar");

        jLabel11.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel11.setText("Alterar");

        jLabel12.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel12.setText("Excluir");

        jLabel13.setFont(new java.awt.Font("Ubuntu", 2, 10)); // NOI18N
        jLabel13.setText("Ex: pedro, roberto, julia, ana etc..");

        jLabel14.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel14.setText("* Rg");

        txtUsuRg.setFont(new java.awt.Font("Ubuntu", 2, 15)); // NOI18N
        txtUsuRg.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel15.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel15.setText("* Endereço");

        txtUsuEnd.setFont(new java.awt.Font("Ubuntu", 2, 15)); // NOI18N
        txtUsuEnd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnLimparCampos.setFont(new java.awt.Font("Ubuntu", 2, 12)); // NOI18N
        btnLimparCampos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/limpar.png"))); // NOI18N
        btnLimparCampos.setText("Limpar os Campos");
        btnLimparCampos.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnLimparCampos.setPreferredSize(new java.awt.Dimension(90, 50));
        btnLimparCampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparCamposActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(598, 598, 598)
                        .addComponent(jLabel3)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtUsuId, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUsuEnd)
                        .addGap(98, 98, 98))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnUsuCreate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(jLabel9)))
                                .addGap(80, 80, 80)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(btnUsuRead, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel13)
                            .addComponent(txtUsuSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtUsuFone, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtUsuLogin, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(83, 83, 83)
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel12)
                                        .addGap(88, 88, 88))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(72, 72, 72)
                                        .addComponent(btnUsuUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(85, 85, 85)
                                        .addComponent(btnUsuDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(71, 71, 71))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel14)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtUsuRg, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(btnLimparCampos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                                            .addComponent(cboUsuPerfil, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addGap(98, 98, 98))))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addComponent(jLabel8))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(23, 23, 23)
                                .addComponent(txtUsuNome, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnUsuCreate, btnUsuDelete, btnUsuRead, btnUsuUpdate});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(77, 77, 77)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUsuId, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel15)
                    .addComponent(txtUsuEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUsuNome, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUsuRg, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel4))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUsuFone, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(cboUsuPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtUsuLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13)
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtUsuSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(btnLimparCampos, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnUsuRead, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUsuUpdate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUsuDelete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUsuCreate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addContainerGap())
        );

        setBounds(0, 0, 750, 600);
    }// </editor-fold>//GEN-END:initComponents

    private void btnUsuReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuReadActionPerformed
        // chamando o metodo consultar
        consultar();

    }//GEN-LAST:event_btnUsuReadActionPerformed

    private void btnUsuCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuCreateActionPerformed
        // chamando metodo adicionar
        adicionar();
    }//GEN-LAST:event_btnUsuCreateActionPerformed

    private void btnLimparCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparCamposActionPerformed
        // chamando metodo limpar campos
        limparCampos();
    }//GEN-LAST:event_btnLimparCamposActionPerformed

    private void btnUsuUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuUpdateActionPerformed
        // chamando o metodo alterar
        alterar();
    }//GEN-LAST:event_btnUsuUpdateActionPerformed

    private void btnUsuDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuDeleteActionPerformed
        // chamando metodo remover
        remover();
    }//GEN-LAST:event_btnUsuDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLimparCampos;
    private javax.swing.JButton btnUsuCreate;
    private javax.swing.JButton btnUsuDelete;
    private javax.swing.JButton btnUsuRead;
    private javax.swing.JButton btnUsuUpdate;
    private javax.swing.JComboBox<String> cboUsuPerfil;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField txtUsuEnd;
    private javax.swing.JTextField txtUsuFone;
    private javax.swing.JTextField txtUsuId;
    private javax.swing.JTextField txtUsuLogin;
    private javax.swing.JTextField txtUsuNome;
    private javax.swing.JTextField txtUsuRg;
    private javax.swing.JTextField txtUsuSenha;
    // End of variables declaration//GEN-END:variables
}
