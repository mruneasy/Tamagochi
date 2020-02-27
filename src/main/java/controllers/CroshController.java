package controllers;


import models.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class CroshController extends HeroesController {


    private Model modelSonic = new Model("croshSave.txt");


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.setModel(modelSonic);
        loadFile();

    }
}
