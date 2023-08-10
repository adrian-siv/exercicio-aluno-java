package com.example;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class PrimaryController {
    // textField e TextArea são objetos/classes
    @FXML
    ListView<Aluno> listAlunos;
    @FXML
    TextField txtNome;
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
        var aluno = new Aluno(txtNome.getText(), "1TDSPG", 200399);
        alunos.add(aluno);
        // txtAlunos.appendText(txtNome.getText() + "\n");
        txtNome.clear();

        atualizarAluno();
    }

    public void atualizarAluno() {
        ordenar();

        listAlunos.getItems().clear();
        for (Aluno aluno : alunos) { // forEach do java
            listAlunos.getItems().add(aluno);
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
