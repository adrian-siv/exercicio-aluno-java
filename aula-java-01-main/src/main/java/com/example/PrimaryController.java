package com.example;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class PrimaryController {
    // textField e TextArea são objetos/classes
    @FXML
    ListView<Aluno> listAlunos;
    @FXML
    TextField txtNome;
    @FXML
    TextField txtTurma;
    @FXML
    TextField txtRM;
    @FXML
    RadioButton rdbCrescente;
    @FXML
    RadioButton rdbDecrescente;

    // collections em java - ArrayList
    // <String> - generics
    // inner class x class
    // (classe com metodos de uso interno usaria ele aqui dentro)
    private ArrayList<Aluno> alunos = new ArrayList<>();

    public void adicionarAluno() {
        var aluno = new Aluno(txtNome.getText(), txtTurma.getText(), Integer.valueOf(txtRM.getText()));

        // Conectar com o banco
        final String HOST = "auth-db719.hstgr.io";
        final String PORT = "3306";
        final String DATABASE = "u553405907_fiap";
        final String USER = "u553405907_fiap";
        final String PASS = "u553405907_FIAP";
        final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE; // JDBC URL

        try {
            var con = DriverManager.getConnection(URL, USER, PASS);

            var sql = "INSERT INTO alunos (nome, turma, rm) VALUES (?, ?, ?)";
            var instrucao = con.prepareStatement(sql);
            instrucao.setString(1, aluno.nome());
            instrucao.setString(2, aluno.turma());
            instrucao.setInt(3, aluno.rm());
            instrucao.executeUpdate();

            Alert alert = new Alert(AlertType.INFORMATION, "Cadastrado com sucesso");
            alert.show();

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //

        alunos.add(aluno);
        // txtAlunos.appendText(txtNome.getText() + "\n");
        txtNome.clear();

        atualizarAluno();
    }

    public void atualizarAluno() {
        final String HOST = "auth-db719.hstgr.io";
        final String PORT = "3306";
        final String DATABASE = "u553405907_fiap";
        final String USER = "u553405907_fiap";
        final String PASS = "u553405907_FIAP";
        final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE; // JDBC URL

        try {
            var con = DriverManager.getConnection(URL, USER, PASS);

            var sql = "SELECT * FROM alunos ORDER BY nome";
            var instrucao = con.prepareStatement(sql);
            var dados = instrucao.executeQuery();

            while(dados.next()) {
                var aluno = new Aluno(
                    dados.getString("nome"),
                    dados.getString("turma"),
                    dados.getInt("rm")
                );

                listAlunos.getItems().add(aluno);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void ordenar() {
        if (rdbCrescente.isSelected()) {
            alunos.sort((o1, o2) -> o1.nome().compareToIgnoreCase(o2.nome()));
        } else {
            alunos.sort((o1, o2) -> o2.nome().compareToIgnoreCase(o1.nome()));
        }
    }

    public void apagarAluno() {
        var aluno = listAlunos.getSelectionModel().getSelectedItem();
        alunos.remove(aluno);
        atualizarAluno();

        Alert alert = new Alert(AlertType.INFORMATION, "Aluno(a) " + aluno.nome() + " apagado(a) com sucesso");
        alert.show();
    }
}
